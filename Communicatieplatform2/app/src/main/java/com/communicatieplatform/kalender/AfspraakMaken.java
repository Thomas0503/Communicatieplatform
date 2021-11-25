package com.communicatieplatform.kalender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.communicatieplatform.dagboek.Activiteit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class AfspraakMaken extends AppCompatActivity {
    private TextView afspraak;
    private TextView datum;
    private TextView locatie;
    private TextView opmerkingen;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afspraak);
        afspraak = findViewById(R.id.afspraak);
        datum = findViewById(R.id.datum);
        locatie = findViewById(R.id.locatie);
        opmerkingen = findViewById(R.id.opmerkingen);
        HashMap<String, Object> data = new HashMap<>();
        data.put("afspraak", afspraak);
        data.put("datum", datum);
        data.put("locatie", locatie);
        data.put("opmerkingen", opmerkingen);
        db = FirebaseFirestore.getInstance();

        db.collection("afspraak").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AfspraakMaken.this, "Afspraak toegevoegd", Toast.LENGTH_SHORT).show();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error adding document", e);
            }
        });
    }
}
