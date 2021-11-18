package com.communicatieplatform;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dagboek extends AppCompatActivity {
    private Button button;
    private Button button2;
    private FirebaseFirestore db;
    private SearchView searchView;
    private ListView listView;
    private ArrayList list;
    private ArrayAdapter adapter;
    private String query;

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
        button2 = (Button) findViewById(R.id.ActiviteitZoeken);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActZoeken();
            }
        });

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        list = new ArrayList<String>();
        list.add("openbare plaatsen");
        list.add("vervoer");
        list.add("shopping");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(getActiviteitLijst().contains(query)){
                    adapter.getFilter().filter(query);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //openActZoeken();
                        }
                    });
                    // geselecteerde activiteit onthouden ==> getActiviteit
                }else{
                    Toast.makeText(Dagboek.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActZoeken();
                    }
                });
                return false;
            }
        });

    }

    public ArrayList<String> getActiviteitLijst(){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("dagboektest").document("oefening2");
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Processing when the value can be obtained
                                ArrayList<String> list = (ArrayList<String>) document.get("oefening");//
                            } else {
                                list = new ArrayList<String>();
                                //What to do when the value does not exist
                            }
                        }
                    }});
        return list;
    }


    public void openDagboek() {
        Intent intent = new Intent(this, Activiteit.class);
        startActivity(intent);
    }
    public String getActiviteit(){
        return query;
    }

    public void openActZoeken() {
        Intent intent = new Intent(this, ActZoeken.class);
        startActivity(intent);
    }
}