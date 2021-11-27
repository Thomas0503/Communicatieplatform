package com.communicatieplatform.dagboek;

import android.widget.ImageView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activiteit2 implements Serializable {
    //private Activiteit product;
    @Exclude
    private String id;
    private List<String> signalenLijst;
    private Date datum;
    private String oefening;
    private String description;
    private Integer stressniveau;

    /*String id = product.getId();
    EditText datum = product.getDatum();
    Integer progress = product.getNiveau();
    ArrayList<ActStresssignalen> signalenLijst = product.getStresssignalenLijst();*/

    public Activiteit2(Timestamp datum, String oefening, Integer stressniveau, List<String> stresssignalen, String description) {
        this.datum = datum.toDate();
        this.signalenLijst = stresssignalen;
        this.stressniveau = stressniveau;
        this.oefening = oefening;
        this.description = description;
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

    public String getOefening() {
        return oefening;
    }

    public List<String> getStresssignalenLijst() {
        return signalenLijst;
    }

    public String getStresssignalenString() {
        String x;
        String tekst = "";
        for (String i : signalenLijst) {
            x = i + "\n";
            tekst += x;
        }
        StringBuffer tekstAangepast = new StringBuffer(tekst);
        tekst = tekstAangepast.deleteCharAt(tekstAangepast.length() - 1).toString();
        return tekst;
    }

    ;

    public Integer getNiveau() {
        return stressniveau;
    }

    public String getDescription() {
        return description;
    }

}

