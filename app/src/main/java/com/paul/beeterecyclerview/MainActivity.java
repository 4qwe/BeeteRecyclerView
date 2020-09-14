package com.paul.beeterecyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_NAME = "com.paul.beeterecyclerview.DESCRIPTION";

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populate DB in RoomDB onOpen

        RecyclerView myRecyclerView = findViewById(R.id.recyclerview); //Handler
        BeeteListAdapter myBeeteListAdapter = new BeeteListAdapter(this);// ab hier läuft der adapter
        myRecyclerView.setAdapter(myBeeteListAdapter); //set adapter for recycler view layout element
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //layout manager

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class); //Provider erstellt und erinnert ViewModel
        // jeweils bei Activity create
        mMainViewModel.getAllBeete().observe(this, new Observer<List<Beet>>() {
            @Override
            public void onChanged(@Nullable final List<Beet> beete) {
                myBeeteListAdapter.setBeeteArray(beete);
            }
        });
    }

}
