package com.communicatieplatform;

import static java.lang.Math.round;

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
        return size;
    }
    /*public String getSize() {
        Long numSize = Long.getLong(size);
        if(numSize < 1000) {
            return size + "B";
        } else if(numSize < 1000000){
            int kB =  round(numSize/1000);
            return Integer.toString(kB) + "kB";
        } else {
            int mB = round(numSize / 1000000);
            return Integer.toString(mB) + "mB";
        }
    }*/



}