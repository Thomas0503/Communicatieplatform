package com.communicatieplatform;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Dagboek extends AppCompatActivity {
    private Button button;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagboek);

        button = (Button) findViewById(R.id.ActiviteitToev);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDagboek();
            }
        });
    }

    public void openDagboek() {
        Intent intent = new Intent(this, Activiteit.class);
        startActivity(intent);
    }
}