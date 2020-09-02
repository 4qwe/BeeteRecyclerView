package com.paul.beeterecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedViewActivity extends AppCompatActivity {

    private TextView description;
    private TextView levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        description = findViewById(R.id.description);
        levels = findViewById(R.id.levels);
        Intent intent = getIntent();
        description.setText(intent.getStringExtra(MainActivity.EXTRA_NAME));
        levels.setText(intent.getStringExtra(MainActivity.EXTRA_LEVELS));
    }
}
