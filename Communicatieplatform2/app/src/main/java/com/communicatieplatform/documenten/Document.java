package com.communicatieplatform.documenten;

import static java.lang.Math.round;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Document implements Serializable{

    @Exclude private String id;

    private String url, name, size;
    public Document() {

    }

    public Document(String url, String name, String size) {
        this.url = url;
        this.name = name;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
    public String getSize() {
        Double numSize = Double.parseDouble(size);
        if(numSize < 1000) {
            return size + "B";
        } else if(numSize < 1000000){
            Long kB = round(numSize / 10.00)/100;
            return Long.toString(kB) + " kB";
        } else {
            Long MB = round(numSize / 10000.00)/100;
            return Long.toString(MB) + " MB";
        }
    }



}