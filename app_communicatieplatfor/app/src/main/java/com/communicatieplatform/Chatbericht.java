package com.communicatieplatform;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Chatbericht implements Serializable{

    @Exclude private String id;

    private String naam, onderwerp, datum, bericht, uur;
    public Chatbericht() {

    }

    public Chatbericht(String Naam, String Onderwerp, String Datum, String Bericht, String Uur) {
        this.naam = Naam;
        this.onderwerp = Onderwerp;
        this.datum = Datum;
        this.bericht = Bericht;
        this.uur= Uur;
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

