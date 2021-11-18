package com.communicatieplatform;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Chatbericht implements Serializable{

    @Exclude private String id;

    private String Naam, Datum, Bericht, Uur;
    public Chatbericht() {}

    public Chatbericht(String Naam, String Datum, String Bericht, String Uur) {
        this.Naam = Naam;
        this.Datum = Datum;
        this.Bericht = Bericht;
        this.Uur= Uur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return Naam;
    }

    public String getDatum() {
        return Datum;
    }

    public String getBericht() {
        return Bericht;
    }

    public String getUur() {
        return Uur;
    }


}

