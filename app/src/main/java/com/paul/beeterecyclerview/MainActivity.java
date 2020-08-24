package com.paul.beeterecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    final String LOG_TAG = MainActivity.class.getSimpleName();


    private RecyclerView myRecyclerView;
    private BeeteListAdapter myBeeteListAdapter;

    private final LinkedList<String> beetListe = new LinkedList<>();

    public final static String EXTRA_NAME = "DESCRIPTIONFORDETAILEDVIEW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beetListe.addLast("Bananen");
        beetListe.addLast("Tomaten");
        beetListe.addLast("Radieschen");
        beetListe.addLast("Erbsen (gelb)");

        myRecyclerView = findViewById(R.id.recyclerview); //Handler

        myBeeteListAdapter = new BeeteListAdapter(this, beetListe, this);// ab hier läuft der adapter

        myRecyclerView.setAdapter(myBeeteListAdapter); //set adapter for the recycler view layout element

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //layout manager

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Log.d(LOG_TAG, Integer.toString(position));
        Intent intent = new Intent(this, DetailedView.class);
        String description = ((TextView) myRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.beetname)).getText().toString();
        intent.putExtra(EXTRA_NAME, description);
        startActivity(intent);
    }

}
