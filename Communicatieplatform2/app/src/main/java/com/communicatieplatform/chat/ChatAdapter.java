package com.communicatieplatform.chat;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.MessageActivity;
import com.communicatieplatform.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mCtx;
    private List<Chatbericht> productList;
    String query;

    public ChatAdapter(Context mCtx, List<Chatbericht> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.query = "";
    }

    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product_chat, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chatbericht product = productList.get(position);

        holder.textViewNaam.setText(product.getNaam());
        holder.textViewDatum.setText(product.getDatum());
        holder.textViewUur.setText(product.getUur());
        holder.textViewBericht.setText(product.getBericht());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNaam, textViewDatum, textViewUur, textViewBericht;

        public ChatViewHolder(View itemView) {
            super(itemView);
            String query;
            textViewNaam = itemView.findViewById(R.id.textview_naam);
            textViewDatum = itemView.findViewById(R.id.textview_datum);


            itemView.setOnClickListener(this);

        }

        public View getButton() {
            return textViewNaam;
        }

        public View getName() {
            return textViewNaam;
        }

        @Override
        public void onClick(View v) {
            String query = getQuery();
            Intent intent = new Intent(mCtx, MessageActivity.class);
            if (!query.equals("")) {
                intent.putExtra("zoekQuery", query);
            }
            mCtx.startActivity(intent);
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;

    }
}