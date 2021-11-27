package com.communicatieplatform.test_chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.communicatieplatform.R;
import com.communicatieplatform.dagboek.Activiteit2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MessageAdapter extends RecyclerView.Adapter<com.communicatieplatform.test_chat.MessageAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Message> productList;

    public MessageAdapter(Context mCtx, List<Message> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public com.communicatieplatform.test_chat.MessageAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.communicatieplatform.test_chat.MessageAdapter.ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.chat_message, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull com.communicatieplatform.test_chat.MessageAdapter.ProductViewHolder holder, int position) {
        Message product = productList.get(position);
        String senderuid = product.getSender();
        String receiveruid = product.getReceiver();
        String message = product.getText();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();
        holder.sendertv.setText((CharSequence) message);

        if(currentUid.equals(senderuid)){
            holder.receivertv.setVisibility(View.GONE);
            holder.sendertv.setText(message);
        }
        else if(currentUid.equals(receiveruid)){
            holder.sendertv.setVisibility(View.GONE);
            holder.receivertv.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView receivertv, sendertv;

        public ProductViewHolder(View itemView) {
            super(itemView);

            sendertv = itemView.findViewById(R.id.sender);
            receivertv = itemView.findViewById(R.id.receiver);

        }

    }
}
