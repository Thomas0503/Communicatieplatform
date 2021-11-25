package com.communicatieplatform.documenten;

import static java.lang.Math.round;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Document implements Serializable{

    @Exclude private String id;

    private String url, name;
    private Integer size;
    public Document() {

    }

    public Document(String url, String name, Integer size) {
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