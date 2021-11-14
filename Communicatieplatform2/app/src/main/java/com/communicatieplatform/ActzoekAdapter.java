package com.communicatieplatform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActzoekAdapter extends RecyclerView.Adapter<ActzoekAdapter.ProductViewHolder> {

    private Context mCtx;
    private Activiteit productList;

    public ActzoekAdapter(Context mCtx, List<Activiteit> productList) {
        this.mCtx = mCtx;
        this.productList = (Activiteit) productList;
    }


    @Override
    public ActzoekAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActzoekAdapter.ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_activiteit, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActzoekAdapter.ProductViewHolder holder, int position) {
        Activiteit product = productList.get(position);

        holder.textViewDatum.setText(product.getDatum());
        holder.textViewSignalen.setText(product.getStresssignalenLijst());
        holder.textNiveau.setText(product.getNiveau());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewDatum, textViewSignalen, textNiveau;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewDatum = itemView.findViewById(R.id.textview_datum);
            textViewSignalen = itemView.findViewById(R.id.textview_stresssignalen);
            textNiveau = itemView.findViewById(R.id.textview_stressniveau);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Afspraak product = productList.get(getAdapterPosition());

        }
    }
}
