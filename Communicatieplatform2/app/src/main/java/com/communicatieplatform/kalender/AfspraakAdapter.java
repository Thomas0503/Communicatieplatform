package com.communicatieplatform.kalender;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class AfspraakAdapter extends RecyclerView.Adapter<AfspraakAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Afspraak> productList;
    private HashMap<Integer, String> weekDagomzetten, maandOmzetten;

    public AfspraakAdapter(Context mCtx, List<Afspraak> productList) {
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Afspraak product = productList.get(position);
        LocalDateTime localDateTime = product.getLocalDateTime();
        String textDatum = weekDagomzetten.get(Integer.valueOf(localDateTime.getDayOfWeek().getValue()))
                + " " + localDateTime.getDayOfMonth() + " " +
                maandOmzetten.get(Integer.valueOf(localDateTime.getMonthValue())) + " "
                + localDateTime.getYear();

        holder.textViewName.setText(product.getTitle());
        holder.textViewBrand.setText(product.getStartuur()+ " - " + product.getEinduur());
        holder.textViewPrice.setText(textDatum);
        if(product.getLocatie().equals("")){
            holder.textViewDesc.setVisibility(View.GONE);
            holder.titleViewDesc.setVisibility(View.GONE);
        } else {
            holder.textViewDesc.setVisibility(View.VISIBLE);
            holder.textViewDesc.setText(product.getLocatie());
        }
        if(product.getOpmerkingen().equals("")){
            holder.textViewOpmerking.setVisibility(View.GONE);
            holder.titleViewOpmerking.setVisibility(View.GONE);
        } else {
            holder.textViewOpmerking.setVisibility(View.VISIBLE);
            holder.textViewOpmerking.setText(product.getOpmerkingen());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewOpmerking, titleViewDesc, titleViewOpmerking;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);
            textViewOpmerking = itemView.findViewById(R.id.textview_opmerking);
            titleViewDesc = itemView.findViewById(R.id.titleview_desc);
            titleViewOpmerking = itemView.findViewById(R.id.titleview_opmerking);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Afspraak product = productList.get(getAdapterPosition());

        }
    }
}
