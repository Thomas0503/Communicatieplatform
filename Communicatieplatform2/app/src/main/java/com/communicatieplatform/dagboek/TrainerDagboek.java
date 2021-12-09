package com.communicatieplatform.dagboek;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;

public class TrainerDagboek extends AppCompatActivity {
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
        setContentView(R.layout.trainer_dagboek);
        String gezin = getIntent().getStringExtra("pleeggezin");
        button2 = (Button) findViewById(R.id.ActiviteitZoeken);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActZoeken("", gezin);
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("Bench");
        list.add("Dierenarts");
        list.add("Houdingen");
        list.add("Kinderen");
        list.add("Korte lijn zonder prikkels");
        list.add("Korte lijn met prikkels");
        list.add("Mee naar het werk");
        list.add("Op dingen springen / onder dingen kruipen");
        list.add("Stofzuiger");
        list.add("Tafeloefening");
        list.add("Voedselweigering");
        list.add("Vreemde ondergronden");
        list.add("Privé vervoer");
        list.add("Openbaar vervoer");
        list.add("Openbare plaatsen");
        list.add("Shopping");
        list.add("Horeca");
        list.add("Overige");
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
                        openActZoeken(query, gezin);
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
                        openActZoeken(newText, gezin);
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



    public String getActiviteit() {
        return query;
    }

    public void openActZoeken(String query, String gezin) {
        Intent intent = new Intent(this, TrainerActZoeken.class);
        intent.putExtra("zoekQuery", query);
        intent.putExtra("pleeggezin", gezin);
        startActivity(intent);
    }

}