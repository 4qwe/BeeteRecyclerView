package com.paul.beeterecyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.paul.beeterecyclerview.MainActivity.EXTRA_ID;

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

            holder.beeteElementCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailedCardActitvity.class); //context unserer Main Activity
                    intent.putExtra(EXTRA_ID, current.getId()); //current Beet Objekt ~ Description! //EXTRA string auch aus Main, importiert
                    mContext.startActivity(intent);
                }
            });

            holder.beeteElementTextView.setText(current.getDesc());

            ImageView previewPic = holder.beeteElementImageView;

            if (current.getUriString() != null) showPreviewPic(current, previewPic);


        } else {
            holder.beeteElementTextView.setText("Beet data not ready");
        }


    }

    void setBeeteArray(List<Beet> beete) {
        beeteArray = beete;
        notifyDataSetChanged();
    }

    private void showPreviewPic(Beet beet, ImageView iv) {
        Glide.with(mContext).load(Uri.parse(beet.uriString)).into(iv);
    }

    @Override
    public int getItemCount() { //interna.. kA warum diese funktion extra aufgeführt
        if (beeteArray != null)
            return beeteArray.size();
        else
            return 0;
    }

    public Beet getBeetAtPosition(int position) {
        return beeteArray.get(position); //aus mainActivity später - Viewholder:getAdapterPosition
    }

    public class BeeteViewHolder extends RecyclerView.ViewHolder {

        private final TextView beeteElementTextView; //Handler für einen View im Viewholder
        private final ImageView beeteElementImageView; //Handler für einen View im Viewholder
        private final CardView beeteElementCardView;

        public BeeteViewHolder(View elementView) { //Konstruktor
            super(elementView); //Super-Konstruktor mit unserem View
            beeteElementTextView = elementView.findViewById(R.id.beet_name); //View-Variable des Viewholder bereit für Gebrauch in onBind
            beeteElementImageView = elementView.findViewById(R.id.beet_pic); //View-Variable des Viewholder bereit für Gebrauch in onBind
            beeteElementCardView = elementView.findViewById(R.id.cv);
        }
    }
}
