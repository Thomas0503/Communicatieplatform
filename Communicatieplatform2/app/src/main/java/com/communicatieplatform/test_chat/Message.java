package com.communicatieplatform.test_chat;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Message implements Serializable{

    private Date datum;
    private String sender, receiver, text;
    @Exclude private String id;
    public Message() {

    }

    public Message(Timestamp datum, String sender, String receiver, String text) {
        this.datum = datum.toDate();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatum() {
        return datum.toString();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }
}


