package com.communicatieplatform.kalender;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;

import java.util.List;

public class AfspraakAdapter extends RecyclerView.Adapter<AfspraakAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Afspraak> productList;

    public AfspraakAdapter(Context mCtx, List<Afspraak> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Afspraak product = productList.get(position);

        holder.textViewName.setText(product.getDescription());
        holder.textViewBrand.setText(product.getStartuur());
        holder.textViewDesc.setText(product.getEinduur());
        holder.textViewPrice.setText(product.getDatum());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Afspraak product = productList.get(getAdapterPosition());

        }
    }
}
