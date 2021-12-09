package com.communicatieplatform.dagboek;

import android.media.Image;
import android.widget.ImageView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Activiteit2 implements Serializable {
    //private Activiteit product;
    @Exclude
    private String id;
    private List<String> signalenLijst;
    private Timestamp datum;
    private String oefening, url;
    private String description;
    private Integer stressniveau;

    /*String id = product.getId();
    EditText datum = product.getDatum();
    Integer progress = product.getNiveau();
    ArrayList<ActStresssignalen> signalenLijst = product.getStresssignalenLijst();*/
    public Activiteit2() {
    }

    public Activiteit2(Timestamp datum, String oefening, Integer stressniveau, List<String> stresssignalen, String description, String url) {
        this.datum = datum;
        this.signalenLijst = stresssignalen;
        this.stressniveau = stressniveau;
        this.oefening = oefening;
        this.description = description;
        this.url = url;
    }


    public String getId() {
        return id;
    }

    public String getUrl(){return url;}

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDatum() {
        return datum;
    }
    public String getDatumString() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getDatum().toDate().toInstant(), ZoneId.systemDefault());
        String datum = localDateTime.getDayOfMonth() + " " +
                localDateTime.getMonth().toString() + " " + localDateTime.getYear();
        return datum;
    }
    public LocalDateTime getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getDatum().toDate().toInstant(), ZoneId.systemDefault());
        return localDateTime;
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

