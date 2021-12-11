package com.communicatieplatform.dagboek;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainerActZoeken extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActzoekAdapter adapter;
    private List<Activiteit2> productList;
    private ProgressBar progressBar;
    // private Dagboek product;
//    String activiteit = product.getActiviteit();
    Map<String, Boolean> data;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String zoekQuery = getIntent().getStringExtra("zoekQuery");
        setContentView(R.layout.activiteit_zoeken);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<Activiteit2>();
        adapter = new ActzoekAdapter(this, productList);

        recyclerView.setAdapter(adapter);
        String gezin = getIntent().getStringExtra("pleeggezin");

        db = FirebaseFirestore.getInstance();

        if(!zoekQuery.equals("")) {
            db.collection("dagboekje").document("dagboekje").collection(
                    gezin).whereEqualTo("oefening", zoekQuery).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            progressBar.setVisibility(View.GONE);

                            if (!queryDocumentSnapshots.isEmpty()) {

                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {

                                    List<String> stresssignalen = (List<String>) d.get("stresssignalen");
                                    Activiteit2 p = new Activiteit2(d.getTimestamp("createdAt"), d.getString("oefening"), d.getLong("stressniveau").intValue(), stresssignalen, d.getString("desc"), d.getString("link"));
                                    p.setId(d.getId());
                                    productList.add(p);

                                }

                                //for (Map.Entry<String, Boolean> o : data.entrySet()) {
                                //    String key = product.getActiviteit();
                                //    Boolean value = o.getValue();
                                //    if (value == true){
                                //        for (Map.Entry<String, Boolean> m : data.entrySet()) {
                                //            String key2 = m.getKey();
                                //            Boolean value2 = m.getValue();}
                                //    }
                                //}

                                adapter.notifyDataSetChanged();

                            }
                            //if (db.collection("dagboek").document("oefening1").get(activiteit) == true) {
                            //    db.collection("dagboek").document("oefening1").get();
                            //}
                            // Create a reference to the cities collection
                            //CollectionReference cities = db.collection("dagboek");
                            // Create a query against the collection.
                            //Query query = cities.whereEqualTo(activiteit, true);
                            // retrieve  query results asynchronously using query.get()
                            //signalen = cities.whereEqualTo(,true);
                            //ApiFuture<QuerySnapshot> querySnapshot = query.get();

                            //for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                            //    System.out.println(document.getId());
                            //}


                        }
                    });
        } else {
            db.collection("dagboekje").document("dagboekje").collection(
                    gezin).orderBy("createdAt").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            progressBar.setVisibility(View.GONE);

                            if (!queryDocumentSnapshots.isEmpty()) {

                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {

                                    List<String> stresssignalen = (List<String>) d.get("stresssignalen");
                                    Activiteit2 p = new Activiteit2(d.getTimestamp("createdAt"), d.getString("oefening"), d.getLong("stressniveau").intValue(), stresssignalen, d.getString("desc"), d.getString("link"));
                                    p.setId(d.getId());
                                    productList.add(p);

                                }
                                adapter.notifyDataSetChanged();

                            }}
                    });

        }
    }}
