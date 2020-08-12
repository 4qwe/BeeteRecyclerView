package com.paul.beeterecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class BeeteListAdapter extends RecyclerView.Adapter<BeeteListAdapter.BeeteViewHolder> {

    private final LinkedList<String> beeteArray;
    private LayoutInflater inflater; //handler/initialisiertes objekt für den Inflater der in onCreate verwendet wird

    public BeeteListAdapter(Context context, LinkedList<String> beeteWorte) { //Konstruktor setzt 2 Klassen-Variablen
        this.beeteArray = beeteWorte;
        inflater = LayoutInflater.from(context); //LayoutInflater erhält einen context (für dimensionen aus activity?)
    }

    @NonNull
    @Override
    //onCreate-Methode im Adapter (onCreateViewHolder??) erstellt den ViewHolder
    public BeeteListAdapter.BeeteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //woher die viewgroup und ints kommen -> recycler interna
        View inflatedView = inflater.inflate(R.layout.beetliste_element, parent, false); //ein inflater macht
        //mit einem XML ein View-Objekt
        return new BeeteViewHolder(inflatedView); //ab hier existiert unser viewholder
    }

    @Override
    public void onBindViewHolder(@NonNull BeeteListAdapter.BeeteViewHolder holder, int position) // so oder so haben wir jetzt
    // einen viewholder. hier wird die aktuelle position mit dem entsprechenden text versehen
    {
        String currentPos = beeteArray.get(position); //interna..
        holder.beeteElementView.setText(currentPos);
    }

    @Override
    public int getItemCount() { //interna.. kA warum diese funktion extra aufgeführt
        return beeteArray.size();
    }

    public class BeeteViewHolder extends RecyclerView.ViewHolder {

        public final TextView beeteElementView; //Handler für 'View' des inneren Textview-Element

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //initialisert einen ViewHolder (Konstruktor der Superklasse) mit BeetwordView
            beeteElementView = elementView.findViewById(R.id.beetname); //speichert diesen View in der View-Variable des Viewholder
        }


    }
}
