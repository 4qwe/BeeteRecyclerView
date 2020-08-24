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

    private static RecyclerViewClickListener itemListener;
    private Context mContext;

    public BeeteListAdapter(Context context, LinkedList<String> beeteWorte, RecyclerViewClickListener itemListener) { //Konstruktor
        this.mContext = context;
        this.beeteArray = beeteWorte;
        this.itemListener = itemListener;
        inflater = LayoutInflater.from(mContext); //nutzt Context
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

    public static class BeeteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView beeteElementView; //Handler für einen View im Viewholder

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //Super-Konstruktor mit unserem View
            this.beeteElementView = elementView.findViewById(R.id.beetname); //speichert diesen View in der View-Variable des Viewholder
            beeteElementView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

    }
}
