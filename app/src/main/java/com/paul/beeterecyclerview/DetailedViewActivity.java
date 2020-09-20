package com.paul.beeterecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailedViewActivity extends AppCompatActivity {

    private TextView description;
    private TextView levels;
    private EditText mEditTextLevelsView;
    private EditText mEditTextDescView;
    private Button updateDescButton;
    private Button updateLevelsButton;

    private DetailedViewModel mDetailedViewModel;

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

        mDetailedViewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);


        Intent intent = getIntent();
        String idBeet = intent.getStringExtra(MainActivity.EXTRA_ID); //nameBeet ist damit gleicher Beetname wie aus Adapter gerufen
//folgender Observer könnte überall vorkommen! gilt aber nur für das Beet mit diesem Namen

        //-> DetailedRepository-ein Beet davon--//
        mDetailedViewModel.getBeet(idBeet).observe(this, new Observer<Beet>() { /*UI Felder die DB Daten zeigen haben alle einen observer*/
            @Override //new Observer<BEET> setzt unser Beet variable für die ganze Activity
            public void onChanged(@Nullable final Beet beet) {
                description.setText(beet.getDesc());
                levels.setText(beet.getLevels());
                initWaterEditText(beet);
                initDescEditText(beet);
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


}
