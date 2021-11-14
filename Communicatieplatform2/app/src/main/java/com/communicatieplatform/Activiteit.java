package com.communicatieplatform;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activiteit extends AppCompatActivity {
    @Exclude
    private String id;
    private Button button;
    private CheckBox s1,s2;
    private CheckBox a1,a2;
    private ArrayList<ActStresssignalen> signalenLijst;
    ActStresssignalen actStresssignalen;
    private ArrayList<ActActiviteiten> activiteitenLijst;
    ActActiviteiten actActiviteiten;
    private TextView textView;
    private SeekBar seekBar;
    EditText test;
    FirebaseFirestore db;
    private View datum;
    private Integer progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiteit);
        button = findViewById(R.id.opslaanActi);

        a1 = findViewById(R.id.activiteit1);
        a2 = findViewById(R.id.activiteit2);
        String a1 = "Activiteit1";
        String a2 = "Activiteit2";
        HashMap<String, List<ActActiviteiten>> data=new HashMap<>();
        data.put("stressniveau",activiteitenLijst);
        db.collection("dagboek").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Activiteit.this,"Data toegevoegd", Toast.LENGTH_SHORT).show();
            }
        });

        actStresssignalen = new ActStresssignalen();
        s1 = findViewById(R.id.stresssignaal1);
        s2 = findViewById(R.id.stresssignaal2);
        String s1 = "Stresssignaal1";
        String s2 = "Stressignaal2";
        HashMap<String, List<ActStresssignalen>> data2=new HashMap<>();
        data2.put("stressniveau",signalenLijst);
        db.collection("dagboek").document().set(data2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Activiteit.this,"Data toegevoegd", Toast.LENGTH_SHORT).show();
            }
        });

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
        int progress = 0;
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


        HashMap<String, Integer> data3=new HashMap<>();
        data3.put("stressniveau",progress);
        db.collection("dagboek").document().set(data3).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Activiteit.this,"Data toegevoegd", Toast.LENGTH_SHORT).show();
            }
        });

        datum = findViewById(R.id.datum);
        HashMap<String, View> data4=new HashMap<>();
        data4.put("datum",datum);
        db.collection("dagboek").document().set(data4).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Activiteit.this,"Data toegevoegd", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.niveau);
    }

    public Activiteit(View datum, ArrayList<ActStresssignalen> signalenLijst, Integer progress) {
        this.datum = datum;
        this.signalenLijst = signalenLijst;
        this.progress = progress;
    }
    public View getDatum() {
        return datum;
    }

    public ArrayList<ActStresssignalen> getStresssignalenLijst(){
        return signalenLijst;
    }
    public Integer getNiveau() {
        return progress;
    }
    public void setId(String id) {
        this.id = id;
    }
}