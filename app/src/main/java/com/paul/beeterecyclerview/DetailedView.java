package com.paul.beeterecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedView extends AppCompatActivity {

    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        description = findViewById(R.id.description);
        Intent intent = getIntent();
        description.setText(intent.getStringExtra(MainActivity.EXTRA_NAME));
    }
}
