package com.communicatieplatform.dagboek;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
public class ActzoekAdapter extends RecyclerView.Adapter<ActzoekAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Activiteit2> productList;
    private FirebaseStorage storage;
    private HashMap<Integer, String> weekDagomzetten, maandOmzetten;
    public ActzoekAdapter(Context mCtx, List<Activiteit2> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        HashMap<Integer, String> weekDagOmzetten = new HashMap<>();
        weekDagOmzetten.put(1, "Maandag");
        weekDagOmzetten.put(2, "Dinsdag");
        weekDagOmzetten.put(3, "Woensdag");
        weekDagOmzetten.put(4, "Donderdag");
        weekDagOmzetten.put(5, "Vrijdag");
        weekDagOmzetten.put(6, "Zaterdag");
        weekDagOmzetten.put(7, "Zondag");
        HashMap<Integer, String> maandOmzetten = new HashMap<>();
        maandOmzetten.put(1, "januari");
        maandOmzetten.put(2, "februari");
        maandOmzetten.put(3, "maart");
        maandOmzetten.put(4, "april");
        maandOmzetten.put(5, "mei");
        maandOmzetten.put(6, "juni");
        maandOmzetten.put(7, "juli");
        maandOmzetten.put(8, "augustus");
        maandOmzetten.put(9, "september");
        maandOmzetten.put(10, "oktober");
        maandOmzetten.put(11, "november");
        maandOmzetten.put(12, "december");
        this.weekDagomzetten = weekDagOmzetten;
        this.maandOmzetten = maandOmzetten;
    }


    @Override
    public ActzoekAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActzoekAdapter.ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_activiteit, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActzoekAdapter.ProductViewHolder holder, int position) {
        Activiteit2 product = productList.get(position);
        LocalDateTime localDateTime = product.getLocalDateTime();
        String textDatum = localDateTime.getDayOfMonth() + " " +
                maandOmzetten.get(Integer.valueOf(localDateTime.getMonthValue())) + " "
                + localDateTime.getYear();
        holder.textOefening.setText(product.getOefening());
        holder.textViewDatum.setText(textDatum);
        holder.textViewSignalen.setText(product.getStresssignalenString());
        holder.textNiveau.setText(product.getNiveau().toString());
        if(product.getDescription().equals("") || product.getDescription().isEmpty()){
            holder.textOmschrijving.setVisibility(View.GONE);
            holder.titleOmschrijving.setVisibility(View.GONE);
        } else {
            holder.textOmschrijving.setVisibility(View.VISIBLE);
            holder.titleOmschrijving.setVisibility(View.VISIBLE);
            holder.textOmschrijving.setText(product.getDescription());
        }
        if(product.getUrl().equals("")){
            holder.imageView.setVisibility(View.GONE);
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
            storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            Uri uri = Uri.parse(product.getUrl());
            Picasso.get().load(product.getUrl()).fit().centerInside().into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewDatum, textViewSignalen, textNiveau, textOefening, textOmschrijving, titleOmschrijving;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewDatum = itemView.findViewById(R.id.textview_datum);
            textViewSignalen = itemView.findViewById(R.id.textview_stresssignalen);
            textNiveau = itemView.findViewById(R.id.textview_stressniveau);
            textOefening = itemView.findViewById(R.id.textview_oefening);
            textOmschrijving = itemView.findViewById(R.id.textview_omschrijving);
            imageView = itemView.findViewById(R.id.imageView);
            titleOmschrijving = itemView.findViewById(R.id.title_omschrijving);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Activiteit2 product = productList.get(getAdapterPosition());

        }
    }
}
