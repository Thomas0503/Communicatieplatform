package com.communicatieplatform.documenten;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;
import com.communicatieplatform.databinding.TrainerDocumentsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrainerDocumentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DocumentAdapter adapter;
    private List<Document> productList;
    private ProgressBar progressBar;
    private TrainerDocumentsBinding binding;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = TrainerDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new DocumentAdapter(this, productList);

        recyclerView.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();

        String gezin = getIntent().getStringExtra("pleeggezin");
        db.collection("formulier").document("formulier").collection(gezin).orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            productList.removeAll(productList);

                            for (DocumentSnapshot d : list) {

                                Document p = new Document(d.getString("link"), d.getString("name"), d.getLong("size").intValue(), d.getTimestamp("createdAt"));
                                p.setId(d.getId());
                                productList.add(p);

                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openDocumentenToevoegen();}
        });

    }
    public void openDocumentenToevoegen() {
        Intent intent = new Intent(this, DocumentenToevoegenActivity.class);
        startActivity(intent);
    }
}
