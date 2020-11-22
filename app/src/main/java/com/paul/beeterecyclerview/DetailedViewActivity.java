package com.paul.beeterecyclerview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

public class DetailedViewActivity extends AppCompatActivity {

    private TextView description;
    private TextView levels;
    private EditText mEditTextLevelsView;
    private EditText mEditTextDescView;
    private Button updateDescButton;
    private Button updateLevelsButton;
    private ImageView imgShower;
    private Button imgButton;
    private Button saveButton;


    private DetailedViewModel mDetailedViewModel;

    private Uri uriFromUcrop;

    private String currentPhotoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        description = findViewById(R.id.description);
        levels = findViewById(R.id.levels);
        mEditTextLevelsView = findViewById(R.id.editText);
        mEditTextDescView = findViewById(R.id.editText2);
        updateLevelsButton = findViewById(R.id.button2);
        updateDescButton = findViewById(R.id.button);
        imgShower = findViewById(R.id.imageShower);
        imgButton = findViewById(R.id.buttonImage);
        saveButton = findViewById(R.id.button3);

        mDetailedViewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);


        Intent intent = getIntent(); //Return the intent that started this activity.
        String idBeet = intent.getStringExtra(MainActivity.EXTRA_ID); //nameBeet ist damit gleicher Beetname wie aus Adapter gerufen
        //folgender Observer könnte überall vorkommen! gilt aber nur für das Beet mit diesem Namen

        //-> DetailedRepository-ein Beet davon--//
        mDetailedViewModel.getBeet(idBeet).observe(this, new Observer<Beet>() { /*UI Felder die DB Daten zeigen haben alle einen observer*/
            @Override //new Observer<BEET> setzt unser Beet variable für die ganze Activity
            public void onChanged(@Nullable final Beet beet) { //onChanged wird auch ohne Change 1x ausgeführt //was bedeutet final hier genau?
                description.setText(beet.getDesc());
                initDescEditText(beet);
                levels.setText(beet.getLevels());
                initWaterEditText(beet);
                initImgButton();
                initSaveButton(beet);

                if (beet.uriString != null)
                    showPreviousPic(beet);
            }
        });


    }


    private void showPreviousPic(Beet beet) {
        Glide.with(this).load(Uri.parse(beet.uriString)).into(imgShower);
    }


    void initImgButton() {
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }

    void initSaveButton(Beet beet) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beet.uriString == null) {
                    beet.setUriString(uriFromUcrop.toString());
                    mDetailedViewModel.update(beet);
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s updated", beet.getDesc()), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s already has a pic", beet.getDesc()), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    void initWaterEditText(Beet beet) {

        updateLevelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mEditTextLevelsView.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "No text", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    beet.setLevels(mEditTextLevelsView.getText().toString());
                    mDetailedViewModel.update(beet);
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s updated", beet.getDesc()), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });

    }

    void initDescEditText(Beet beet) {

        updateDescButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mEditTextDescView.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "No text", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    beet.setDesc(mEditTextDescView.getText().toString());
                    mDetailedViewModel.update(beet);
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s updated", beet.getDesc()), Toast.LENGTH_SHORT);
                    toast.show();
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
            e.printStackTrace();
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
            System.out.println("File exists");
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

                Glide.with(this).load(uriFromUcrop).into(imgShower);
                imgButton.setText("dont click");
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

}
