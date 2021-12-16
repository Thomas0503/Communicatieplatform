package com.communicatieplatform.test_chat;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        String media = product.getMedia();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();

        if(currentUid.equals(senderuid)){
            holder.receivertv.setVisibility(View.GONE);
            holder.sendertv.setVisibility(View.VISIBLE);
            holder.sendertv.setText(message);
        }
        else {
            holder.sendertv.setVisibility(View.GONE);
            holder.receivertv.setVisibility(View.VISIBLE);
            holder.receivertv.setText(message);
        }
        if(media.equals("")){
            holder.senderImage.setVisibility(View.GONE);
            holder.receiverImage.setVisibility(View.GONE);
        } else {
            if(currentUid.equals(senderuid)){
                holder.receiverImage.setVisibility(View.GONE);
                holder.senderImage.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(media);
                Picasso.get().load(uri).fit().centerCrop(Gravity.RIGHT).into(holder.senderImage);
            }
            else {
                holder.senderImage.setVisibility(View.GONE);
                holder.receiverImage.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(media);
                Picasso.get().load(uri).fit().centerCrop(Gravity.LEFT).into(holder.receiverImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView receivertv, sendertv;
        ImageView senderImage, receiverImage;

        public ProductViewHolder(View itemView) {
            super(itemView);

            sendertv = itemView.findViewById(R.id.sender);
            receivertv = itemView.findViewById(R.id.receiver);
            senderImage = itemView.findViewById(R.id.imageViewIk);
            receiverImage = itemView.findViewById(R.id.imageViewAnder);

        }

    }
}
