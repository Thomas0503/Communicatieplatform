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
    public ActzoekAdapter(Context mCtx, List<Activiteit2> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
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

        holder.textOefening.setText((CharSequence) product.getOefening());
        holder.textViewDatum.setText((CharSequence) product.getDatum());
        holder.textViewSignalen.setText((CharSequence) product.getStresssignalenString());
        holder.textNiveau.setText((CharSequence) product.getNiveau().toString());
        if(product.getDescription().equals("")){
            holder.textOmschrijving.setVisibility(View.GONE);
        } else {
            holder.textOmschrijving.setVisibility(View.VISIBLE);
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

        TextView textViewDatum, textViewSignalen, textNiveau, textOefening, textOmschrijving;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewDatum = itemView.findViewById(R.id.textview_datum);
            textViewSignalen = itemView.findViewById(R.id.textview_stresssignalen);
            textNiveau = itemView.findViewById(R.id.textview_stressniveau);
            textOefening = itemView.findViewById(R.id.textview_oefening);
            textOmschrijving = itemView.findViewById(R.id.textview_omschrijving);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Activiteit2 product = productList.get(getAdapterPosition());

        }
    }
}
