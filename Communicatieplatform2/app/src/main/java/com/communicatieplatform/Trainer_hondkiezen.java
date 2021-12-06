package com.communicatieplatform;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.communicatieplatform.dagboek.ActZoeken;
import com.communicatieplatform.dagboek.Activiteit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;

public class
Trainer_hondkiezen extends AppCompatActivity {
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
        setContentView(R.layout.trainer_keuze);
        button = (Button) findViewById(R.id.Algemeen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlgemeen();
            }
        });
        button2 = (Button) findViewById(R.id.Specifiek);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpecifiek("");
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("Hond1");
        list.add("Hond2");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setVisibility(View.INVISIBLE);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSpecifiek(query);
                    }

                });
                // geselecteerde activiteit onthouden ==> getActiviteit

                listView.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSpecifiek(newText);
                    }
                });
                return false;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(adapter.getItem(position).toString(), false);
                searchView.clearFocus();
                listView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public ArrayList<String> getHondenLijst() {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document("pleeggezinnen");
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Processing when the value can be obtained
                                ArrayList<String> list = (ArrayList<String>) document.get("pleeggezinnen");//
                            } else {
                                list = new ArrayList<String>();
                                //What to do when the value does not exist
                            }
                        }
                    }
                });
        return list;
    }


    public void openAlgemeen() {
        Intent intent = new Intent(this, Activiteit.class);
        startActivity(intent);
    }

    public String getHond() {
        return query;
    }

    public void openSpecifiek(String query) {
        Intent intent = new Intent(this, ActZoeken.class);
        intent.putExtra("zoekQuery", query);
        startActivity(intent);
    }

}