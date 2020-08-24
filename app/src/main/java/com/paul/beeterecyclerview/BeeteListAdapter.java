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
    private LayoutInflater inflater;    //handler/uninitialisiertes objekt für den Inflater

    public BeeteListAdapter(Context context, LinkedList<String> beeteWorte) { //Konstruktor
        this.beeteArray = beeteWorte;
        inflater = LayoutInflater.from(context); //nutzt Context
    }

    @NonNull
    @Override
    public BeeteListAdapter.BeeteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = inflater.inflate(R.layout.beetliste_element, parent, false); //ein inflater macht mit einem XML ein View-Objekt
        return new BeeteViewHolder(inflatedView); //ab hier existiert unser viewholder
    }

    @Override
    public void onBindViewHolder(@NonNull BeeteListAdapter.BeeteViewHolder holder, int position) //onBind beschreibt das aktuelle view-element
    {
        String currentPos = beeteArray.get(position);
        holder.beeteElementView.setText(currentPos);
    }

    @Override
    public int getItemCount() { //interna.. kA warum diese funktion extra aufgeführt
        return beeteArray.size();
    }

    public class BeeteViewHolder extends RecyclerView.ViewHolder {

        public final TextView beeteElementView; //Handler für einen View im Viewholder

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //Super-Konstruktor mit unserem View
            this.beeteElementView = elementView.findViewById(R.id.beetname); //speichert diesen View in der View-Variable des Viewholder
        }


    }
}
