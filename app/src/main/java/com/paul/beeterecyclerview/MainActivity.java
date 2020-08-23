package com.paul.beeterecyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private BeeteListAdapter myBeeteListAdapter;

    private final LinkedList<String> beetListe = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beetListe.addLast("Bananen");
        beetListe.addLast("Tomaten");
        beetListe.addLast("Radieschen");
        beetListe.addLast("Erbsen (gelb)");

        myRecyclerView = findViewById(R.id.recyclerview); //Handler

        myBeeteListAdapter = new BeeteListAdapter(this, beetListe);// ab hier läuft der adapter

        myRecyclerView.setAdapter(myBeeteListAdapter); //set adapter for the recycler view layout element

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //layout manager


    }
}
