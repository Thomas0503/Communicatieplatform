package com.communicatieplatform;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Afspraak implements Serializable{

    @Exclude private String id;

    private String startuur, einduur, datum, description, hond;
    public Afspraak() {

    }

    public Afspraak(String startuur, String einduur, String datum, String description, String hond) {
        this.startuur = startuur;
        this.einduur = einduur;
        this.description = description;
        this.datum= datum;
        this.hond = hond;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartuur() {
        return startuur;
    }

    public String getEinduur() {
        return einduur;
    }

    public String getDescription() {
        return description;
    }

    public String getDatum() {
        return datum;
    }


}

