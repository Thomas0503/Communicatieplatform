package com.communicatieplatform.dagboek;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Activiteit extends AppCompatActivity {
    @Exclude
    private String id;
    private Button button;
    private CheckBox s1, s2;
    private RadioButton a1, a2;
    private TextView textView;
    private SeekBar seekBar;
    TextView test;
    FirebaseFirestore db;
    private EditText editTextDate;
    private Integer progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiteit);
        button = findViewById(R.id.opslaanActi);



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
                uploadData();

            }
        });


    }


    public void uploadData() {
        ArrayList<String> signaalLijst = new ArrayList<String>(40);
        String oefening = null;
        String datum = null;
        final RadioButton a1 = (RadioButton) findViewById(R.id.activiteit1);
        if (a1.isChecked()) {
            oefening = a1.getText().toString();
        }
        final RadioButton a2 = (RadioButton) findViewById(R.id.activiteit2);
        if (a2.isChecked()) {
            oefening = a2.getText().toString();
        }
        final RadioButton a3 = (RadioButton) findViewById(R.id.activiteit3);
        if (a3.isChecked()) {
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

        int variableNull = 0;
        editTextDate = findViewById(R.id.editTextDate);
        int stressniveau = seekBar.getProgress();

        if(oefening == null){
            Toast.makeText(this,"Kies een oefening",Toast.LENGTH_SHORT).show();
            variableNull =0;
        }else if (editTextDate.getText() == null){
            Toast.makeText(this,"Kies een datum",Toast.LENGTH_SHORT).show();
            variableNull =0;
        }else if(signaalLijst.isEmpty()){
            Toast.makeText(Activiteit.this,"Kies stresssignalen",Toast.LENGTH_SHORT).show();
            variableNull =0;
        } else {
        datum = editTextDate.getText().toString();
        }

        if(variableNull == 1) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("stressniveau", stressniveau);
            data.put("datum", datum);
            data.put("oefening", oefening);
            data.put("stresssignalen", signaalLijst);
            db = FirebaseFirestore.getInstance();

            db.collection("dagboektest").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Activiteit.this, "Data toegevoegd", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.niveau);
    }
}
