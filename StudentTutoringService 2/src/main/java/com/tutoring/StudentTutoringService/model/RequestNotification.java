package com.tutoring.StudentTutoringService.model;

public class RequestNotification {
    private int userID;
    private int slotID;
    private String type;
    private String message;

    public RequestNotification() {}

    public RequestNotification(int userID, int slotID, String type, String message) {
        this.userID = userID;
        this.slotID = slotID;
        this.type = type;
        this.message = message;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public int getSlotID() { return slotID; }
    public void setSlotID(int slotID) { this.slotID = slotID; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}