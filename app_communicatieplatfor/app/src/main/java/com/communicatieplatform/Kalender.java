package com.communicatieplatform;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Kalender extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender);

        button = (Button) findViewById(R.id.afspraakToevoegen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAfspraak();
            }
        });
    }

    public void openAfspraak() {
        Intent intent = new Intent(this, Afspraak.class);
        startActivity(intent);
    }
}