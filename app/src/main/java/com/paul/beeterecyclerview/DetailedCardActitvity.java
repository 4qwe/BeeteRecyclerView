package com.paul.beeterecyclerview;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

public class DetailedCardActitvity extends AppCompatActivity {


    private DetailedViewModel mDetailedCardViewModel; //variable hier vs variable in onCreate?

    private String currentPhotoPath = "";

    private Uri uriFromUcrop;

    private Beet activityBeet;

    private static final int PERMISSION_REQUEST_CODE = 200;

    private ImageView titleImage;
    private TextView titleText;
    private Button picTakeButton;
    private EditText levelsEditText;
    private Button setLevelsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_card);

        titleImage = findViewById(R.id.title);
        titleText = findViewById(R.id.titleText);
        picTakeButton = findViewById(R.id.button1);
        levelsEditText = findViewById(R.id.waterLevel);
        setLevelsButton = findViewById(R.id.levelsEditButton);

        mDetailedCardViewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);

        Intent intent = getIntent();
        String idBeet = intent.getStringExtra(MainActivity.EXTRA_ID);

        activityBeet = mDetailedCardViewModel.getBeet(idBeet).getValue(); //wird im onChange beschrieben

        mDetailedCardViewModel.getBeet(idBeet).observe(this, new Observer<Beet>() {
            @Override
            public void onChanged(@Nullable final Beet beet) {
                activityBeet = beet;
                titleText.setText(beet.getDesc());
                levelsEditText.setText(String.format("%s%%", beet.getLevels()));
                initImgButton();
                initSetLevelsButton(beet);
                if (beet.uriString != null)
                    showPreviousPic(beet);
            }
        });


    }

    private void initSetLevelsButton(Beet beet) {
        setLevelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = levelsEditText.getText().toString().replaceAll("%", "");
                if (str.equals(beet.getLevels())) {
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("Water levels unchanged", beet.getDesc()), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    beet.setLevels(str);
                    mDetailedCardViewModel.update(beet);
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s updated", beet.getDesc()), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void showPreviousPic(Beet beet) {
        Glide.with(this).load(Uri.parse(beet.uriString)).into(titleImage);
    }


    void initImgButton() {
        picTakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCamPermission()) {
                    //main logic or main code

                    openCamera();

                    // . write your main code to execute, It will execute if the permission is already given.

                } else {
                    requestPermission();
                }
            }
        });
    }


    private void openCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file;
        try {
            file = getImageFile(); // 1
        } catch (Exception e) {
            e.printStackTrace(); //Logcat für Android!!
            return;
        }
        Uri uri;
        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID.concat(".provider"), file);
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 4
        startActivityForResult(pictureIntent, 113);
    }


    private File getImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ), "Camera"
        );
        System.out.println(storageDir.getAbsolutePath());
        if (storageDir.exists())
            System.out.println("File exists"); //todo Log.d !!
        else
            System.out.println("File not exists");
        File file = File.createTempFile(
                imageFileName, ".jpg", storageDir
        );
        currentPhotoPath = "file:" + file.getAbsolutePath();
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 113 && resultCode == RESULT_OK) { //Bild von Kamera fertig (wir kehren zum 1. mal in diese
            //activity zurück, aber gehen direkt in die Crop activity
            Uri uri = Uri.parse(currentPhotoPath); //wir croppen mit der Uri aus dem vorherigen Path String!
            openCropActivity(uri, uri);
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) { //Bild fertig gecroppt
            if (data != null) { //wir kehren zum 2. und letzen mal in die activity zurück, bleiben dann hier
                //und nehmen nun die Uri aus dem Intent extra
                uriFromUcrop = UCrop.getOutput(data);
                Glide.with(this).load(uriFromUcrop).into(titleImage);
                activityBeet.setUriString(uriFromUcrop.toString());
                mDetailedCardViewModel.update(activityBeet);
            }
        }
    }

    private void openCropActivity(Uri sourceUri, Uri destinationUri) {
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);
        options.setCropFrameColor(ContextCompat.getColor(this, R.color.colorAccent));
        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(600, 800)
                .withAspectRatio(5f, 5f)
                .start(this);
    }

    private boolean checkCamPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null);
        alertBuilder.show();
    }

}
