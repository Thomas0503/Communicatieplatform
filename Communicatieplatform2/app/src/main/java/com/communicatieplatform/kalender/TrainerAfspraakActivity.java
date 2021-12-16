package com.communicatieplatform.kalender;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class TrainerAfspraakActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AfspraakAdapter adapter;
    private List<Afspraak> productList;
    private ProgressBar progressBar;
    private Button button;
    FirebaseStorage storage; //used for uploading files
    FirebaseFirestore database;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        button = (Button) findViewById(R.id.afspraakToev);
        storage = FirebaseStorage.getInstance(); //return object of Firebase Storage
        database = FirebaseFirestore.getInstance(); //return object of Firebase Database
        String gezin = getIntent().getStringExtra("pleeggezin");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAfspraakMaken(gezin);
            }
        });

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new AfspraakAdapter(this, productList);

        recyclerView.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();


        db.collection("calenderT").document("calenderT").collection(gezin)
                .whereGreaterThanOrEqualTo("start", Timestamp.now())
                .orderBy("start")
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
                            productList.removeAll(productList);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                Afspraak p = new Afspraak(d.getTimestamp("start"), d.getTimestamp("end"),d.getString("title"),
                                        d.getString("locatie"), d.getString("opmerkingen"));
                                p.setId(d.getId());
                                productList.add(p);

                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });
    }
    public void openAfspraakMaken(String pleeggezin) {
        Intent intent = new Intent(this, TrainerAfspraakMaken.class);
        intent.putExtra("pleeggezin", pleeggezin);
        startActivity(intent);
    }
}
