package com.example.dogcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {
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
        Intent intent = new Intent(this, Dagboek.class);
        startActivity(intent);
    }

    public void openKalender() {
        Intent intent = new Intent(this, Kalender.class);
        startActivity(intent);
    }

    public void openDocumenten() {
        Intent intent = new Intent(this, Documenten.class);
        startActivity(intent);
    }

    public void openChat() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
}