package com.communicatieplatform.dagboek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.communicatieplatform.R;
import com.communicatieplatform.documenten.TrainerDocumentActivity;
import com.communicatieplatform.documenten.TrainerDocumentenToevoegenActivity;
import com.communicatieplatform.documenten.TrainerDocumentenToevoegenActivitySpecifiek;
import com.communicatieplatform.documenten.TrainerDocumenten_keuze;
import com.communicatieplatform.test_chat.KiesContactActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TrainerDagboek_keuze extends AppCompatActivity {
    private Button button;
    private ArrayList list;
    private ArrayAdapter adapter;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_keuze_dagboek);

        button = (Button) findViewById(R.id.Specifiek);

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        HashMap<String, String> dict = new HashMap<>();
        db = FirebaseFirestore.getInstance();
        for(String user:getIntent().getStringArrayListExtra("pleeggezin")){
            db.collection("users").document("user").get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String username = document.getString("name");
                                    dict.put(username,user);
                                    list.add(username);
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setVisibility(View.INVISIBLE);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.contains(query)){
                            voegDocumentenSpecifiek(dict.get(query));
                        } else {
                            Toast.makeText(TrainerDagboek_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
                        }
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.contains(newText)){
                            voegDocumentenSpecifiek(dict.get(newText));
                        } else {
                            Toast.makeText(TrainerDagboek_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
                        }
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

    public void voegDocumentenSpecifiek(String query) {
        Intent intent = new Intent(this, TrainerDagboek.class);
        intent.putExtra("pleeggezin", query);
        startActivity(intent);
    }

}
