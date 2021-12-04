package com.communicatieplatform.kalender;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Afspraak implements Serializable {

    @Exclude
    private String id;

    private Timestamp start, eind;
    private String title, locatie, opmerkingen;

    public Afspraak() {

    }

    public Afspraak(Timestamp start, Timestamp eind, String title, String locatie, String opmerkingen) {
        this.start = start;
        this.eind = eind;
        this.title = title;
        this.locatie = locatie;
        this.opmerkingen = opmerkingen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEind() {
        return eind;
    }

    public String getTitle() {
        return title;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getOpmerkingen() {
        return opmerkingen;
    }

    public String getStartuur() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getStart().toDate().toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String tijd = localDateTime.format(dateTimeFormatter);
        return tijd;
    }

    public String getEinduur() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getEind().toDate().toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String tijd = localDateTime.format(dateTimeFormatter);
        return tijd;
    }

    public String getDatumString() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getStart().toDate().toInstant(), ZoneId.systemDefault());
        String datum = localDateTime.getDayOfWeek().toString() + " " + localDateTime.getDayOfMonth() + " " +
                localDateTime.getMonth().toString() + " " + localDateTime.getYear();
        return datum;
    }

    public LocalDateTime getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                getStart().toDate().toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }
}

