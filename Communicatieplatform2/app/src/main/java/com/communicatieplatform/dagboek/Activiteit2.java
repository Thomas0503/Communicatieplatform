package com.communicatieplatform.dagboek;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class Activiteit2 implements Serializable {
    //private Activiteit product;
    @Exclude private String id;
    private List<String> signalenLijst;
    private String datum;
    private String oefening;
    private Integer stressniveau;
    /*String id = product.getId();
    EditText datum = product.getDatum();
    Integer progress = product.getNiveau();
    ArrayList<ActStresssignalen> signalenLijst = product.getStresssignalenLijst();*/
    public Activiteit2() {

    }
    public Activiteit2(String datum, String oefening, Integer stressniveau, List<String> stresssignalen) {
        this.datum =  datum;
        this.signalenLijst = stresssignalen;
        this.stressniveau = stressniveau;
        this.oefening = oefening;
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
    public String getOefening() {
        return oefening;
    }

    public List<String> getStresssignalenLijst(){
        return signalenLijst;
    }
    public Integer getNiveau() {
        return stressniveau;
    }

}

