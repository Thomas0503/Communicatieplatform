package com.communicatieplatform.chat;

import static com.google.api.AnnotationsProto.http;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import com.communicatieplatform.MessageActivity;
import com.communicatieplatform.R;
import com.communicatieplatform.dagboek.ActZoeken;
import com.communicatieplatform.dagboek.Dagboek;
import com.communicatieplatform.documenten.DocumentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Chatbericht> productList;
    private ProgressBar progressBar;
    private ChatAdapter.ChatViewHolder viewholder;
    private SearchView searchView;
    private ListView listView;
    private ArrayList list;
    private ArrayAdapter adapter2;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<Chatbericht>();
        adapter = new ChatAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("chat").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Chatbericht p = d.toObject(Chatbericht.class);
                                p.setId(d.getId());
                                productList.add(p);

                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });

        /*viewholder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessage()
            }
        });*/

        searchView = findViewById(R.id.search_userch);

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter2);
        listView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                    adapter2.getFilter().filter(query);
                    adapter.setQuery(query);
                    /*viewholder.getButton().setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){openMessage2(query); }
                    });*/

                }else{
                    Toast.makeText(Chat.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                listView.setVisibility(View.INVISIBLE);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter2.getFilter().filter(newText);
                adapter.setQuery(newText);
                /*viewholder.getButton().setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){openMessage2(newText); }
                });*/
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(adapter2.getItem(position).toString(), false);
                searchView.clearFocus();
                listView.setVisibility(View.INVISIBLE);
            }
        });

}

    public void openMessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
    public void openMessage2(String query) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("zoekQuery", query );
        startActivity(intent);
    }
}
