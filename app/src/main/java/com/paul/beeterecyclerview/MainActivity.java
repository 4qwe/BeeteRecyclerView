package com.paul.beeterecyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private BeeteListAdapter myBeeteListAdapter;

    private final LinkedList<String> beetListe = new LinkedList<>();
    private final int[] beeteArray = new int[]{
            R.drawable.ic_android_black_24dp, R.drawable.ic_brightness_5_black_24dp, R.drawable.ic_local_car_wash_black_24dp, R.drawable.ic_sentiment_very_dissatisfied_black_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        beetListe.addLast("Bananen");
        beetListe.addLast("Tomaten");
        beetListe.addLast("Radieschen");
        beetListe.addLast("Erbsen (gelb)");

        myRecyclerView = findViewById(R.id.recyclerview); //Handler wird beschrieben

        myBeeteListAdapter = new BeeteListAdapter(this, beetListe, beeteArray); // (Context context, LinkedList<String>)
        //Konstruktor plus unsere Daten
        //ab hier läuft der adapter

        myRecyclerView.setAdapter(myBeeteListAdapter); //set adapter for the recycler view layout element

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); //layout manager

        //Snap-scrolling
        SnapHelper snapper = new LinearSnapHelper();
        snapper.attachToRecyclerView(myRecyclerView);


    }
}
