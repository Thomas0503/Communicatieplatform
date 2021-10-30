package com.communicatieplatform;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;


public class Activiteit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiteit);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}