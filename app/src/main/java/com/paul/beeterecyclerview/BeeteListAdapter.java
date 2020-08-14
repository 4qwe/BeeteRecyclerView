package com.paul.beeterecyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class BeeteListAdapter extends RecyclerView.Adapter<BeeteListAdapter.BeeteViewHolder> {

    private final LinkedList<String> beeteArray;
    private final int[] beeteImgArray;
    private LayoutInflater inflater; //handler/initialisiertes objekt für den Inflater der in onCreate verwendet wird

    public BeeteListAdapter(Context context, LinkedList<String> beeteWorte, int[] beeteImgs) { //Konstruktor setzt 3 Klassen-Variablen
        this.beeteArray = beeteWorte;
        this.beeteImgArray = beeteImgs;
        inflater = LayoutInflater.from(context); //LayoutInflater erhält einen context (für dimensionen aus activity?)
    }

    @NonNull
    @Override
    //onCreate-Methode im Adapter erstellt den ViewHolder
    public BeeteListAdapter.BeeteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //woher die viewgroup und ints kommen -> recycler interna
        View inflatedView = inflater.inflate(R.layout.beetliste_element, parent, false); //ein inflater macht
        //mit einem XML ein View-Objekt. der inflated View enthält alle elemente aus beetliste.xml
        //constraint layout funktioniert auch!
        return new BeeteViewHolder(inflatedView); //ab hier existiert unser viewholder
    }

    @Override
    public void onBindViewHolder(@NonNull BeeteListAdapter.BeeteViewHolder holder, int position) // so oder so haben wir jetzt
    // einen viewholder. hier wird die aktuelle position mit dem entsprechenden text versehen
    {
        String currentPos = beeteArray.get(position); //interna..
        holder.beeteElementView.setText(currentPos);
        holder.beeteImageView.setImageResource(beeteImgArray[position]);
    }

    @Override
    public int getItemCount() { //interna.. kA warum diese funktion extra aufgeführt
        return beeteArray.size();
    }

    public class BeeteViewHolder extends RecyclerView.ViewHolder {

        public final TextView beeteElementView; //Handler für 'View' des inneren Textview-Element
        public final ImageView beeteImageView; //inneres Img-Element

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //initialisert einen ViewHolder (Konstruktor der Superklasse) mit BeetwordView
            this.beeteElementView = elementView.findViewById(R.id.beetname); //speichert diesen View in der View-Variable des Viewholder
            this.beeteImageView = elementView.findViewById(R.id.imageView2);//ImgView ist element von elementView
        }


    }
}
