package com.paul.beeterecyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.paul.beeterecyclerview.MainActivity.EXTRA_NAME;

public class BeeteListAdapter extends RecyclerView.Adapter<BeeteListAdapter.BeeteViewHolder> {

    private List<Beet> beeteArray; //cached copy meiner Beete
    private final LayoutInflater inflater;    //handler/uninitialisiertes objekt für den Inflater

    private Context mContext;

    public BeeteListAdapter(Context context) { //Konstruktor

        inflater = LayoutInflater.from(context); //nutzt Context
        this.mContext = context; //context als member variable, weil benutzt hier, aber auch in onBind
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

        if (beeteArray != null) {
            Beet current = beeteArray.get(position);

            holder.beeteElementView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailedViewActivity.class); //context unserer Main Activity
                    intent.putExtra(EXTRA_NAME, current.getDesc()); //current Beet Objekt ~ Description! //EXTRA string auch aus Main, importiert
                    mContext.startActivity(intent);
                }
            });
            holder.beeteElementView.setText(current.getDesc());
        } else {
            holder.beeteElementView.setText("Beet data not ready");
        }


    }

    void setBeeteArray(List<Beet> beete) {
        beeteArray = beete;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { //interna.. kA warum diese funktion extra aufgeführt
        if (beeteArray != null)
            return beeteArray.size();
        else
            return 0;
    }

    public class BeeteViewHolder extends RecyclerView.ViewHolder {

        private final TextView beeteElementView; //Handler für einen View im Viewholder

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //Super-Konstruktor mit unserem View
            beeteElementView = elementView.findViewById(R.id.beetname); //View-Variable des Viewholder bereit für Gebrauch in onBind
        }
    }
}
