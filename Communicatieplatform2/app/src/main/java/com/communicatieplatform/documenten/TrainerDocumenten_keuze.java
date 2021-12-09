package com.communicatieplatform.documenten;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.communicatieplatform.R;
import com.communicatieplatform.test_chat.KiesContactActivity;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TrainerDocumenten_keuze extends AppCompatActivity {
    private Button button, button2, buttonBekijkDoc;
    private ArrayList list;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_keuze_documenten);

        button = (Button) findViewById(R.id.Algemeen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voegDocumentenAlgemeen();
            }
        });
        button2 = (Button) findViewById(R.id.Specifiek);
        buttonBekijkDoc = (Button) findViewById(R.id.specifiek);

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("tt");
        list.add("pcm");
        HashMap<String, String> dict = new HashMap<>();
        dict.put("tt","wVEKX0xL6xRiSvztD1EFV3gHos12");
        dict.put("pcm","EqI10LALkGOjjonWT9LGSUIdc572");
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
                        if(list.contains(query)){
                            voegDocumentenSpecifiek(dict.get(query));
                        } else {
                            Toast.makeText(TrainerDocumenten_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                buttonBekijkDoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.contains(query)){
                            bekijkDocumenten(dict.get(query));
                        } else {
                            Toast.makeText(TrainerDocumenten_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
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
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.contains(newText)){
                            voegDocumentenSpecifiek(dict.get(newText));
                        } else {
                            Toast.makeText(TrainerDocumenten_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                buttonBekijkDoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list.contains(newText)){
                            bekijkDocumenten(dict.get(newText));
                        } else {
                            Toast.makeText(TrainerDocumenten_keuze.this,"Kies een pleeggezin", Toast.LENGTH_SHORT).show();
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
    public void voegDocumentenAlgemeen() {
        Intent intent = new Intent(this, TrainerDocumentenToevoegenActivity.class);
        ArrayList<String> pleeggezin = getIntent().getStringArrayListExtra("pleeggezin");
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }
    public void voegDocumentenSpecifiek(String query) {
        Intent intent = new Intent(this, TrainerDocumentenToevoegenActivitySpecifiek.class);
        intent.putExtra("pleeggezin", query);
        startActivity(intent);
    }
    public void bekijkDocumenten(String query) {
        Intent intent = new Intent(this, TrainerDocumentActivity.class);
        intent.putExtra("pleeggezin", query);
        startActivity(intent);
    }

}
