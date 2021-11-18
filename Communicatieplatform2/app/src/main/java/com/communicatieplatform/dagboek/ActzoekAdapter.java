package com.communicatieplatform.dagboek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.communicatieplatform.R;

public class ActzoekAdapter extends RecyclerView.Adapter<ActzoekAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Activiteit2> productList;

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


        holder.textViewDatum.setText((CharSequence) product.getDatum());
        holder.textViewSignalen.setText((CharSequence) "product.getStresssignalenLijst()");
        holder.textNiveau.setText((CharSequence) "product.getNiveau()");
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
            Activiteit2 product = productList.get(getAdapterPosition());

        }
    }
}
