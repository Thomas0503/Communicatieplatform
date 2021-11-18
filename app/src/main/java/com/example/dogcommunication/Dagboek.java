package com.example.dogcommunication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dagboek extends AppCompatActivity {
    private Button button;

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