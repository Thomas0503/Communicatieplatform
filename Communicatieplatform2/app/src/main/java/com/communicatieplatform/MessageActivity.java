package com.communicatieplatform;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView;
    TextView username;
    ImageButton sendbtn;
    EditText messageEt;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference rootref1,rootref2;
    MessageMember messageMember;
    String receiver_name, receiver_uid,sender_uid,url;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            url = bundle.getString("u");
            receiver_name = bundle.getString("n");
            receiver_uid = bundle.getString("uid");
        } else{
            Toast.makeText(this,"user missing",Toast.LENGTH_SHORT).show();
        }
        messageMember = new MessageMember();
        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        messageEt = findViewById(R.id.user_message);
        sendbtn = findViewById(R.id.send_image);
        username = findViewById(R.id.username_messageTv);
        username.setText(receiver_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sender_uid = user.getUid();

        rootref1 = db.getReference("Message").child(sender_uid).child(receiver_uid);
        rootref2 = db.getReference("Message").child(receiver_uid).child(sender_uid);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MessageMember> options1 =
                new FirebaseRecyclerOptions.Builder<MessageMember>().setQuery(rootref1,MessageMember.class).build();
        FirebaseRecyclerAdapter<MessageMember,MessageViewHolder> firebaseRecyclerAdapter1=
                new FirebaseRecyclerAdapter<MessageMember, MessageViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull MessageMember model) {
                    holder.setMessage(getApplication(),model.getMessage(),model.getTime(),model.getDate(),model.getSenderuid(),model.getReceiveruid());
                    }
                    @NonNull
                    @Override
                    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype){
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message,parent,false);
                        return new MessageViewHolder(view);
                    }
                };
    }

    private void SendMessage() {

        String message = messageEt.getText().toString();

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss dd-MMMM-yyyy");
        final String savetime = currentdate.format(ctime.getTime());

        String time = savedate + ":" + savetime;

        if (message.isEmpty()){
            Toast.makeText(this,"Kan geen leeg bericht verzenden", Toast.LENGTH_SHORT).show();
        } else {
            messageMember.setDate(savedate);
            messageMember.setTime(savetime);
            messageMember.setMessage(message);
            messageMember.setReceiveruid(receiver_uid);
            messageMember.setSenderuid(sender_uid);

            String id = rootref1.push().getKey();
            rootref1.child(id).setValue(messageMember);

            String id1 = rootref2.push().getKey();
            rootref2.child(id1).setValue(messageMember);

            messageEt.setText("");
        }

    }
}
