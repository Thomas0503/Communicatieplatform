package com.communicatieplatform.test_chat;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Model_User implements Serializable{

    private String name,uid, id;



    public Model_User(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

}


