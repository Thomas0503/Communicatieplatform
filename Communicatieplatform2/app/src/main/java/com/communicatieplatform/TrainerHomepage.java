package com.communicatieplatform;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.communicatieplatform.chat.Chat;
import com.communicatieplatform.dagboek.Dagboek;
import com.communicatieplatform.dagboek.TrainerDagboek;
import com.communicatieplatform.dagboek.TrainerDagboek_keuze;
import com.communicatieplatform.documenten.TrainerDocumenten_keuze;
import com.communicatieplatform.documenten.DocumentActivity;
import com.communicatieplatform.kalender.AfspraakActivity;
import com.communicatieplatform.kalender.TrainerKalender_keuze;
import com.communicatieplatform.test_chat.KiesContactActivity;

import java.util.ArrayList;
import java.util.List;

public class TrainerHomepage extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        button = (Button) findViewById(R.id.dagboek);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDagboek();
            }
        });

        button = (Button) findViewById(R.id.kalender);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKalender();
            }
        });

        button = (Button) findViewById(R.id.documenten);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocumenten();
            }
        });

        button = (Button) findViewById(R.id.chat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });
    }

    public void openDagboek() {
        Intent intent = new Intent(this, TrainerDagboek_keuze.class);
        ArrayList<String> pleeggezin = getIntent().getStringArrayListExtra("pleeggezin");
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }

    public void openKalender() {
        Intent intent = new Intent(this, TrainerKalender_keuze.class);
        ArrayList<String> pleeggezin = getIntent().getStringArrayListExtra("pleeggezin");
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }

    public void openDocumenten() {
        Intent intent = new Intent(this, TrainerDocumenten_keuze.class);
        ArrayList<String> pleeggezin = getIntent().getStringArrayListExtra("pleeggezin");
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }

    public void openChat() {
        Intent intent = new Intent(this, KiesContactActivity.class);
        ArrayList<String> pleeggezin = getIntent().getStringArrayListExtra("pleeggezin");
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }
}