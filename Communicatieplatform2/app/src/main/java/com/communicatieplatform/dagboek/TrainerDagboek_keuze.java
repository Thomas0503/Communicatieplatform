package com.communicatieplatform.dagboek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.communicatieplatform.R;
import com.communicatieplatform.test_chat.KiesContactActivity;

public class TrainerDagboek_keuze extends AppCompatActivity {
    private Button button, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_keuze);

        button = (Button) findViewById(R.id.Algemeen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDagboek();
            }
        });
        button2 = (Button) findViewById(R.id.Specifiek);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDagboek();
            }
        });
    }
    public void openDagboek() {
        Intent intent = new Intent(this, TrainerDagboek.class);
        startActivity(intent);
    }
}
