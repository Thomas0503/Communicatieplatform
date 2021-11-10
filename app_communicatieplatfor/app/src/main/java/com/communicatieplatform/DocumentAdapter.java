package com.communicatieplatform;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Document> productList;
    private FirebaseStorage storage;
    public DocumentAdapter(Context mCtx, List<Document> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_document, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Document product = productList.get(position);

        holder.textViewName.setText(product.getName());
        holder.textViewSize.setText(product.getSize());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewSize;
        ImageButton button;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewSize = itemView.findViewById(R.id.textview_size);
            button = itemView.findViewById(R.id.imageButton);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
                Document bestand = productList.get(getAbsoluteAdapterPosition());
                /*CharSequence options[] = new CharSequence[]{
                        "Download",
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we will be downloading the pdf
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                            mCtx.startActivity();
                        }
                        // We will view the pdf
                        if (which == 1) {
                            Intent intent = new Intent(v.getContext(), ViewPdfActivity.class);
                            intent.putExtra("url", message);
                            mCtx.startActivity();
                        }
                    }
                });
                builder.show();
            }

            Document bestand = productList.get(getAbsoluteAdapterPosition());
            Log.d("Error", "DocumentSnapshot successfully written!");
            String bestandsNaam = bestand.getName();
            StorageReference storageReference = storage.getReference();
            StorageReference fileRef = storageReference.child(bestand.getUrl());
            Boolean localFileCreated = Boolean.FALSE;
            File localFile = null;
            if (bestandsNaam.endsWith(".pdf")) {
                try {
                    localFile = File.createTempFile("application", "pdf");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                localFileCreated = Boolean.TRUE;
            } else if (bestandsNaam.endsWith(".docx") || bestandsNaam.endsWith(".doc")) {
                try {
                    localFile = File.createTempFile("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                localFileCreated = Boolean.TRUE;
            }
            if (localFileCreated) {
                fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                    }
                });
            }*/
        }
    }
}

