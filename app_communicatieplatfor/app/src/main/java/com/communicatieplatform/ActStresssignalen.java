package com.communicatieplatform;

import com.google.firebase.firestore.Exclude;

public class ActStresssignalen{
    @Exclude private String id;
    public ActStresssignalen() {}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private boolean Stresssignaal;
    public ActStresssignalen(Boolean stresssignaal) {
        this.Stresssignaal = stresssignaal;
    }
    public boolean getStresssignaal() {
        return Stresssignaal;
    }
    public void setStresssignaal(boolean stresssignaal){
        Stresssignaal = stresssignaal;
    }

}
