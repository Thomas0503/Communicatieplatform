package com.communicatieplatform.test_chat;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.MessageActivity;
import com.communicatieplatform.R;
import com.communicatieplatform.chat.Chatbericht;
import com.communicatieplatform.dagboek.ActZoeken;

import java.util.ArrayList;
import java.util.List;

public class test_adapter extends RecyclerView.Adapter<com.communicatieplatform.test_chat.test_adapter.ChatViewHolder> implements Filterable{

    private Context mCtx;
    private List<Model_User> productList, copy;
    String query;

    public test_adapter(Context mCtx, List<Model_User> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        copy = new ArrayList<>(productList);
    }

    @Override
    public com.communicatieplatform.test_chat.test_adapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.communicatieplatform.test_chat.test_adapter.ChatViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product_chat, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull com.communicatieplatform.test_chat.test_adapter.ChatViewHolder holder, int position) {
        Model_User product = productList.get(position);

        holder.textViewNaam.setText(product.getName());
        //holder.textViewDatum.setText(product.getDatum());
        //holder.textViewUur.setText(product.getUur());
        //holder.textViewBericht.setText(product.getBericht());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setCopy(List<Model_User> list) {
        this.copy = list;
    }
    public List<Model_User> getCopy() {
        return this.copy;
    }
    class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewNaam, textViewDatum, textViewUur, textViewBericht;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textViewNaam = itemView.findViewById(R.id.textview_naam);
            textViewDatum = itemView.findViewById(R.id.textview_datum);
            textViewUur = itemView.findViewById(R.id.textview_uur);
            textViewBericht = itemView.findViewById(R.id.textview_bericht);

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
            int position = getBindingAdapterPosition();
            Model_User product = productList.get(position);
            Intent intent = new Intent(v.getContext(), test_MessageActivity.class);
            intent.putExtra("receiveruid", product.getUid());
            intent.putExtra("receivername", product.getName());
            v.getContext().startActivity(intent);
        }
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model_User> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(copy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Model_User item : copy) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    }



