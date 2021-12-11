package com.communicatieplatform.dagboek;

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
import com.communicatieplatform.documenten.TrainerDocumentActivity;
import com.communicatieplatform.documenten.TrainerDocumentenToevoegenActivity;
import com.communicatieplatform.documenten.TrainerDocumentenToevoegenActivitySpecifiek;
import com.communicatieplatform.documenten.TrainerDocumenten_keuze;
import com.communicatieplatform.test_chat.KiesContactActivity;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TrainerDagboek_keuze extends AppCompatActivity {
    private Button button;
    private ArrayList list;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_keuze_dagboek);

        button = (Button) findViewById(R.id.Specifiek);

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("tt");
        list.add("pcm");
        list.add("Thomas");
        list.add("Charlotte");
        HashMap<String, String> dict = new HashMap<>();
        dict.put("tt","wVEKX0xL6xRiSvztD1EFV3gHos12");
        dict.put("pcm","EqI10LALkGOjjonWT9LGSUIdc572");
        dict.put("Thomas","gbua3TQ24nUWY0JITHPIDk5uMc43");
        dict.put("Charlotte","LDQHVSjkIeZ0yBLwe0zr6NWFwgM2");
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
