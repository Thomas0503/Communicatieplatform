package com.communicatieplatform;

public class MessageMember{
    private String message,time,date,name,senderuid,receiveruid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return message;
    }

    public void setDate(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderuid() {
        return senderuid;
    }

    public void setSenderuid(String senderuid) {
        this.senderuid = senderuid;
    }

    public String getReceiveruid() {
        return receiveruid;
    }

    public void setReceiveruid(String receiveruid) {
        this.receiveruid = receiveruid;
    }

    public MessageMember() {

    }

    MessageMember(String message, String name, String time, String date, String senderuid, String receiveruid) {
        this.message = message;
        this.date = date;
        this.name = name;
        this.time = time;
        this.senderuid = senderuid;
        this.receiveruid = receiveruid;
    }

}