package com.communicatieplatform.dagboek;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.communicatieplatform.documenten.DocumentenToevoegenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activiteit extends AppCompatActivity {
    @Exclude
    private String id;
    private Button button, button_afbeelding;
    private CheckBox s1, s2;
    private RadioButton a1, a2;
    private TextView textView;
    private SeekBar seekBar;
    TextView test;
    FirebaseFirestore db;
    private EditText editTextDate;
    private Integer progress;
    TextView notification;
    Uri imageUri; //Uri are actually URLs that are meant for local storage
    ImageView imageView;
    FirebaseStorage storage; //used for uploading files
    FirebaseFirestore database;; //used to store URLs of uploaded files
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiteit);
        button = findViewById(R.id.opslaanActi);
        button_afbeelding = findViewById(R.id.afbeeldingToevoegen);
        imageView = findViewById(R.id.toegevoegdeAfbeelding);
        storage = FirebaseStorage.getInstance(); //return object of Firebase Storage
        database = FirebaseFirestore.getInstance(); //return object of Firebase Database

        imageUri = null;
        int progress = 0;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        test = (TextView) findViewById(R.id.niveau);
        initializeVariables();

        textView.setText(progress + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

                textView.setText(progress + "/" + seekBar.getMax());
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageUri!= null){ uploadImage(imageUri);}
                else {uploadData("null");}
                imageUri = null;
              }

        });
        button_afbeelding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Activiteit.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(Activiteit.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

            }

            }

        });


    }


    public void uploadData(String databaseUrl) {
        ArrayList<String> signaalLijst = new ArrayList<String>(40);
        String oefening = null;
        String datum = null;
        final RadioButton a1 = (RadioButton) findViewById(R.id.activiteit1);
        final RadioButton a2 = (RadioButton) findViewById(R.id.activiteit2);
        final RadioButton a3 = (RadioButton) findViewById(R.id.activiteit3);
        if (a1.isChecked()) {
            oefening = a1.getText().toString();
        }else if (a2.isChecked()) {
            oefening = a2.getText().toString();
        }else if (a3.isChecked()) {
            oefening = a3.getText().toString();
        }

        final CheckBox s1 = (CheckBox) findViewById(R.id.stresssignaal1);
        if (s1.isChecked()) {
            signaalLijst.add(s1.getText().toString());
        }
        final CheckBox s2 = (CheckBox) findViewById(R.id.stresssignaal2);
        if (s2.isChecked()) {
            signaalLijst.add(s2.getText().toString());
        }
        final CheckBox s3 = (CheckBox) findViewById(R.id.stresssignaal3);
        if (s3.isChecked()) {
            signaalLijst.add(s3.getText().toString());
        }

        int variableIsNull = 1;
        editTextDate = findViewById(R.id.editTextDate);
        int stressniveau = seekBar.getProgress();

        if(oefening == null){
            Toast.makeText(this,"Kies een oefening",Toast.LENGTH_SHORT).show();
            variableIsNull =0;
        }else if (editTextDate.getText().toString().equals("")){
            Toast.makeText(this,"Kies een datum",Toast.LENGTH_SHORT).show();
            variableIsNull =0;
        }else if(signaalLijst.isEmpty()){
            Toast.makeText(Activiteit.this,"Kies stresssignalen",Toast.LENGTH_SHORT).show();
            variableIsNull =0;
        } else {
        datum = editTextDate.getText().toString();
        }

        if(variableIsNull == 1) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("stressniveau", stressniveau);
            data.put("datum", datum);
            data.put("oefening", oefening);
            data.put("stresssignalen", signaalLijst);
            data.put("imageUrl", databaseUrl);
            db = FirebaseFirestore.getInstance();

            db.collection("dagboektest").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Activiteit.this, "Activiteit toegevoegd", Toast.LENGTH_SHORT).show();
                }
            }) .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("TAG", "Error adding document", e);
                }
            });
        }
    }
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.niveau);
    }
    private void uploadImage(Uri pdfUri) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Bestand uploaden...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName=System.currentTimeMillis()+"";
        String directory = "Images_dagboek";
        StorageReference storageReference=storage.getReference(); //return root path
        storageReference.child(directory).child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //store the url in realtime database
                        String url = directory + "/" + fileName;
                        uploadData(url);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                //track the progress of =our upload
                int currentProgress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
                if(currentProgress == 100) {progressDialog.dismiss();}
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
            Toast.makeText(Activiteit.this,"Geef toestemming alstublieft...",Toast.LENGTH_SHORT).show();
    }


    private void selectPdf() {
        //to offer user to select a file using file manager
        //we will be using a Intent
        //Intent intent=new Intent();
        //intent.setType("application/pdf");
        //intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"image/*"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }
    private Long getFileInfo(Uri pdfUri) {
        Cursor returnCursor =
                getContentResolver().query(pdfUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        returnCursor.moveToFirst();
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        Long fileSize =  returnCursor.getLong(sizeIndex);
        return fileSize;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check whether user has selected a file or no
        if(requestCode==86 && resultCode==RESULT_OK && data!=null)
        {
            imageUri=data.getData(); // return the uri of selected file
            Long fileInfo = getFileInfo(imageUri);
            if(fileInfo > 5000000) {
                Toast.makeText(Activiteit.this,"Dit bestand is te groot",Toast.LENGTH_SHORT).show();
            } else {
                button_afbeelding.setText("Andere afbeelding kiezen");
                Drawable yourDrawable;
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    yourDrawable = Drawable.createFromStream(inputStream, imageUri.toString() );
                } catch (FileNotFoundException e) {
                    yourDrawable = getResources().getDrawable(R.drawable.legeafbeelding);
                }
                imageView.setImageDrawable(yourDrawable);
            }
        }
        else{
            Toast.makeText(Activiteit.this,"Selecteer een afbeelding",Toast.LENGTH_SHORT).show();
        }
    }
}
