package com.communicatieplatform;

import android.widget.EditText;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activiteit2 implements Serializable {
    private Activiteit product;
    //private String id;
    //private ArrayList<ActStresssignalen> signalenLijst;
    // private EditText datum;
    //private Integer progress;
    String id = product.getId();
    EditText datum = product.getDatum();
    Integer progress = product.getNiveau();
    ArrayList<ActStresssignalen> signalenLijst = product.getStresssignalenLijst();

    public Activiteit2(EditText datum, ArrayList<ActStresssignalen> signalenLijst, Integer progress) {
        this.datum = (EditText) datum;
        this.signalenLijst = signalenLijst;
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EditText getDatum() {
        return datum;
    }

    public ArrayList<ActStresssignalen> getStresssignalenLijst(){
        return signalenLijst;
    }
    public Integer getNiveau() {
        return progress;
    }


}
