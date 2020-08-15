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

    private final LinkedList<String> beetName = new LinkedList<>();
    private final LinkedList<Beet> beeteArray = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beeteArray.addLast(new Beet("Banana"));
        beeteArray.addLast(new Beet("Tomaten"));
        beeteArray.addLast(new Beet("Radieschen"));
        beeteArray.addLast(new Beet("Erbsen (gelb)"));

        myRecyclerView = findViewById(R.id.recyclerview); //Handler wird beschrieben

        myBeeteListAdapter = new BeeteListAdapter(this, beeteArray); // (Context context, LinkedList<Beet>)
        //Konstruktor plus unsere Daten
        //ab hier läuft der adapter

        myRecyclerView.setAdapter(myBeeteListAdapter); //set adapter for the recycler view layout element

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); //layout manager

        //Snap-scrolling
        SnapHelper snapper = new LinearSnapHelper();
        snapper.attachToRecyclerView(myRecyclerView);


    }
}
