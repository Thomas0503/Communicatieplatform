package com.communicatieplatform.test_chat;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.communicatieplatform.R;
import com.communicatieplatform.databinding.ChatBinding;
import com.communicatieplatform.databinding.DocumentsPleeggezinBinding;
import com.communicatieplatform.documenten.Document;
import com.communicatieplatform.documenten.DocumentAdapter;
import com.communicatieplatform.documenten.DocumentenToevoegenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class KiesContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private test_adapter adapter;
    private List<Model_User> productList, copy;
    private ProgressBar progressBar;
    private DocumentsPleeggezinBinding binding;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchView searchView = findViewById(R.id.search_userch);
        productList = new ArrayList<>();


        db = FirebaseFirestore.getInstance();


        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Model_User p = new Model_User(d.getString("name"), d.getString("uid"));
                                p.setId(d.getId());
                                productList.add(p);

                            }
                            adapter = new test_adapter(KiesContactActivity.this, productList);

                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }


                    }
                });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                // geselecteerde activiteit onthouden ==> getActiviteit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter != null) {
                adapter.getFilter().filter(newText);}
                return false;
            }
        });
    }


}

