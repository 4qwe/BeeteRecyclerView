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
    private EditText mEditWordView;
    private Button updateGoButton;

    private DetailedViewModel mDetailedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        description = findViewById(R.id.description);
        levels = findViewById(R.id.levels);
        mEditWordView = findViewById(R.id.editText);
        updateGoButton = findViewById(R.id.button2);

        mDetailedViewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);


        Intent intent = getIntent();
        String idBeet = intent.getStringExtra(MainActivity.EXTRA_ID); //nameBeet ist damit gleicher Beetname wie aus Adapter gerufen
//folgender Observer könnte überall vorkommen! gilt aber nur für das Beet mit diesem Namen

        //-> DetailedRepository-ein Beet davon--//
        mDetailedViewModel.getBeet(idBeet).observe(this, new Observer<Beet>() { /*UI Felder die DB Daten zeigen haben alle einen observer*/
            @Override
            public void onChanged(@Nullable final Beet beet) {
                description.setText(beet.getDesc());
                levels.setText(beet.getLevels());
                initEditText(beet);

            }
        });


    }

    void initEditText(Beet beet) {
        mEditWordView.setText(beet.getLevels());
        updateGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "No text", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    beet.setLevels(mEditWordView.getText().toString());
                    mDetailedViewModel.update(beet);
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("%s updated", beet.getDesc()), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });

    }
}
