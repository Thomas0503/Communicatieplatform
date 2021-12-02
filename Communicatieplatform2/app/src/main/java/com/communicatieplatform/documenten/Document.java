package com.communicatieplatform.documenten;

import static java.lang.Math.round;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.sql.Time;

public class Document implements Serializable{

    @Exclude private String id;

    private String url, name;
    private Integer size;
    private Timestamp datum;
    public Document() {

    }

    public Document(String link, String name, Integer size, Timestamp createdAt) {
        this.url = link;
        this.name = name;
        this.size = size;
        this.datum = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDatum() {return datum;}

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
    public String getSize() {
        Double numSize = size.doubleValue();
        if(numSize < 1000) {
            return size + "B";
        } else if(numSize < 1000000){
            Long kB = round(numSize / 1000);
            return Long.toString(kB) + " kB";
        } else {
            Long MB = round(numSize / 1000000);
            return Long.toString(MB) + " MB";
        }
    }



}