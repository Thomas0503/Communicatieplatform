package com.communicatieplatform;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Chatbericht implements Serializable{

    @Exclude private String id;

    private String naam, onderwerp, datum, bericht, uur;
    public Chatbericht() {

    }

    public Chatbericht(String naam, String onderwerp, String datum, String bericht, String uur) {
        this.naam = naam;
        this.onderwerp = onderwerp;
        this.datum = datum;
        this.bericht = bericht;
        this.uur= uur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public String getDatum() {
        return datum;
    }

    public String getBericht() {
        return bericht;
    }

    public String getUur() {
        return uur;
    }


}

