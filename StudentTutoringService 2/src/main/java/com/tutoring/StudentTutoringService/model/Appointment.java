package com.tutoring.StudentTutoringService.model;

public class Appointment {
    private int appointmentID;
    private int userID;
    private int slotID;
    private int tutorID;
    private String status;
    private String details;
    private String tutorName;
    private String subject;
    private String startTime;
    private String endTime;

    public Appointment() {}

    public int getAppointmentID() {

        return appointmentID;
    }
    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    public int getUserID() {

        return userID;
    }
    public void setUserID(int userID) {

        this.userID = userID;
    }

    public int getSlotID() {

        return slotID;
    }
    public void setSlotID(int slotID) {

        this.slotID = slotID;
    }

    public int getTutorID() {

        return tutorID;
    }
    public void setTutorID(int tutorID) {

        this.tutorID = tutorID;
    }

    public String getStatus() {

        return status;
    }
    public void setStatus(String status) {

        this.status = status;
    }

    public String getDetails() {

        return details;
    }
    public void setDetails(String details) {

        this.details = details;
    }

    public String getTutorName() {
        return tutorName;
    }
    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}