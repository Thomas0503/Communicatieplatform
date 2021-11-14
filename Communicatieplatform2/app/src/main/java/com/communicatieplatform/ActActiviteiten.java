package com.communicatieplatform;

import com.google.firebase.firestore.Exclude;

public class ActActiviteiten{
    @Exclude private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private boolean Activiteit;
    public ActActiviteiten(Boolean activiteit) {
        this.Activiteit = activiteit;
    }
    public boolean getActiviteit() {
        return Activiteit;
    }
    public void setActiviteit(boolean activiteit){
        Activiteit = activiteit;
    }

}
