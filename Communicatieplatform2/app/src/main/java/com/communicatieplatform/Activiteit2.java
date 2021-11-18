package com.communicatieplatform;

import android.widget.EditText;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activiteit2 implements Serializable {
    //private Activiteit product;
    @Exclude private String id;
    private ArrayList<String> signalenLijst;
    private String datum;
    private String stressniveau;
    /*String id = product.getId();
    EditText datum = product.getDatum();
    Integer progress = product.getNiveau();
    ArrayList<ActStresssignalen> signalenLijst = product.getStresssignalenLijst();*/
    public Activiteit2() {

    }
    public Activiteit2(String datum, ArrayList<ActStresssignalen> oefening, String stressniveau, ArrayList<String> stresssignalen) {
        this.datum =  datum;
        this.signalenLijst = stresssignalen;
        this.stressniveau = stressniveau;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public ArrayList<String> getStresssignalenLijst(){
        return signalenLijst;
    }
    public String getNiveau() {
        return stressniveau;
    }

}

