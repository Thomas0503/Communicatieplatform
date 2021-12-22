package com.communicatieplatform.documenten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class DocumentenToevoegenActivity extends AppCompatActivity {

    Button selectFile, upload;
    TextView notification;
    Uri pdfUri; //Uri are actually URLs that are meant for local storage

    FirebaseStorage storage; //used for uploading files
    private FirebaseFirestore database;
    ; //used to store URLs of uploaded files
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents);

        storage = FirebaseStorage.getInstance(); //return object of Firebase Storage
        database = FirebaseFirestore.getInstance(); //return object of Firebase Database

        selectFile = findViewById(R.id.selectFile);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.notification);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DocumentenToevoegenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(DocumentenToevoegenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null) {//user has selected a file
                    uploadFile(pdfUri);
                    notification.setText("Bestand geüpload. Geen nieuw bestand geselecteerd");
                    pdfUri = null;
                } else
                    Toast.makeText(DocumentenToevoegenActivity.this, "Selecteer een bestand", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void uploadFile(Uri pdfUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Bestand uploaden...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = System.currentTimeMillis() + " - ";
        final String[] fileInfo = getFileInfo(pdfUri);
        final String name = fileName + "_" + fileInfo[0];
        String directory = "Uploads";
        StorageReference storageReference = storage.getReference(); //return root path
        storageReference.child(directory).child(name).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //store the url in realtime database
                        String[] fileInfo = getFileInfo(pdfUri);
                        String url = directory + "/" + name;
                        CollectionReference reference = database.collection("formulier").document("formulier").collection(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Map<String, Object> image = new HashMap<>();
                        image.put("link", url);
                        image.put("name", fileInfo[0]);
                        image.put("size", Long.parseLong(fileInfo[1]));
                        image.put("createdAt", Timestamp.now());
                        reference.document(name).set(image)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("document_upload", "DocumentSnapshot successfully written!");
                                        Toast.makeText(DocumentenToevoegenActivity.this, "Bestand is succesvol geüpload", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("document_upload", "Error adding document", e);
                                        Toast.makeText(DocumentenToevoegenActivity.this, "Bestand is niet succesvol geüpload", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                //track the progress of =our upload
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else
            Toast.makeText(DocumentenToevoegenActivity.this, "Geef toestemming alstublieft...", Toast.LENGTH_SHORT).show();
    }


    private void selectPdf() {
        //to offer user to select a file using file manager
        //we will be using a Intent
        //Intent intent=new Intent();
        //intent.setType("application/pdf");
        //intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "rtapplication/msword"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    private String[] getFileInfo(Uri pdfUri) {
        Cursor returnCursor =
                getContentResolver().query(pdfUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        returnCursor.moveToFirst();
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        String fileName = returnCursor.getString(nameIndex);
        String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
        return new String[]{fileName, fileSize};
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check whether user has selected a file or no
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData(); // return the uri of selected file
            String[] fileInfo = getFileInfo(pdfUri);

            if (Long.valueOf(fileInfo[1]) > 5000000) {
                Toast.makeText(DocumentenToevoegenActivity.this, "Dit bestand is te groot", Toast.LENGTH_SHORT).show();
            } else {
                notification.setText(fileInfo[0] + " geselecteerd");
            }
        } else {
            Toast.makeText(DocumentenToevoegenActivity.this, "Selecteer een bestand", Toast.LENGTH_SHORT).show();
        }
    }
}