package com.communicatieplatform.test_chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.communicatieplatform.R;
import com.communicatieplatform.dagboek.Activiteit2;
import com.communicatieplatform.dagboek.ActzoekAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test_MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> productList;
    private ProgressBar progressBar;
    TextView username;
    ImageButton sendbtn;
    EditText messageEt;
    private FirebaseFirestore db;
    private String receiver, currentUid;
    private String document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiver = getIntent().getStringExtra("receiveruid");
        String receiverName = getIntent().getStringExtra("receivername");
        //progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        productList = new ArrayList<>();
        adapter = new MessageAdapter(this, productList);

        recyclerView.setAdapter(adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = user.getUid();
        messageEt = findViewById(R.id.user_message);
        sendbtn = findViewById(R.id.send_image);
        username = findViewById(R.id.username_messageTv);
        username.setText(receiverName);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });


        db = FirebaseFirestore.getInstance();
        document = "";
        if(currentUid.compareTo(receiver)<0) {
            document = receiver + currentUid;}
        else {
            document = currentUid+ receiver;}
        db.collection("messages").document(document).collection("chat").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Message p = new Message(d.getTimestamp("createdAt"), d.getString("from"), d.getString("to"), d.getString("text"));
                                p.setId(d.getId());
                                productList.add(p);

                            }


                            adapter.notifyDataSetChanged();

                        }


                    }
                });
        }
    private void SendMessage() {

        String message = messageEt.getText().toString();

        if (message.isEmpty()){
            Toast.makeText(this,"Kan geen leeg bericht verzenden", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            currentUid = user.getUid();
            receiver = getIntent().getStringExtra("receiveruid");
            document = "";
            if(currentUid.compareTo(receiver)<0) {
                document = receiver+ currentUid;}
            else {
                document = currentUid+receiver;}
            HashMap<String, Object> data = new HashMap<>();
            data.put("from", currentUid);
            data.put("createdAt", Timestamp.now());
            data.put("text", message);
            data.put("to", receiver);
            db = FirebaseFirestore.getInstance();
            db.collection("messages").document(document).collection("chat").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(test_MessageActivity.this, "Bericht verstuurd", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("TAG", "Error adding document", e);
                }
            });

            messageEt.setText("");
        }

    }
}
