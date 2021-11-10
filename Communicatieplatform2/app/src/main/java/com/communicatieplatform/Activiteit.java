package com.communicatieplatform;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;


public class Activiteit extends AppCompatActivity {
    private Button button;
    private CheckBox s1,s2;
    private CheckBox a1,a2;
    private List<ActStresssignalen> signalenLijst;
    ActStresssignalen actStresssignalen;
    private List<ActActiviteiten> activiteitenLijst;
    ActActiviteiten actActiviteiten;
    private TextView textView;
    private SeekBar seekBar;
    EditText test;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiteit);
        button = findViewById(R.id.opslaanActi);

        actActiviteiten = new ActActiviteiten();
        a1 = findViewById(R.id.activiteit1);
        a2 = findViewById(R.id.activiteit2);
        String a1 = "Activiteit1";
        String a2 = "Activiteit2";

        actStresssignalen = new ActStresssignalen();
        s1 = findViewById(R.id.stresssignaal1);
        s2 = findViewById(R.id.stresssignaal2);
        String s1 = "Stresssignaal1";
        String s2 = "Stressignaal2";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CheckBox a1 = (CheckBox) findViewById(R.id.activiteit1);
                if (a1.isChecked()) {
                    actActiviteiten.setActiviteit(true);
                }
                else {
                    actActiviteiten.setActiviteit(false);
                }
                final CheckBox a2 = (CheckBox) findViewById(R.id.activiteit2);
                if (a2.isChecked()) {
                    actActiviteiten.setActiviteit(true);
                }
                else {
                    actActiviteiten.setActiviteit(false);
                }
                final CheckBox s1 = (CheckBox) findViewById(R.id.stresssignaal1);
                if (s1.isChecked()) {
                    actStresssignalen.setStresssignaal(true);
                }
                else {
                    actStresssignalen.setStresssignaal(false);
                }
                final CheckBox s2 = (CheckBox) findViewById(R.id.stresssignaal2);
                if (s2.isChecked()) {
                    actStresssignalen.setStresssignaal(true);
                }
                else {
                    actStresssignalen.setStresssignaal(false);
                }
            }
        });


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        test = (EditText) findViewById(R.id.niveau);
        initializeVariables();
        textView.setText( seekBar.getProgress() + "/" + seekBar.getMax() );
        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
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

                textView.setText( progress + "/" + seekBar.getMax() );
                String strI = String.valueOf(progress);
            }
        } );

    }
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.niveau);
    }
}