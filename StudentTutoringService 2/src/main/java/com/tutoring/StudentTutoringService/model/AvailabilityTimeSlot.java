package com.tutoring.StudentTutoringService.model;

public class AvailabilityTimeSlot {
    private int slotID;
    private int tutorID;
    private int subjectID;
    private String tutorName;
    private String subject;
    private String startTime;
    private String endTime;
    private int capacity;
    private int bookedCount;
    private String status;

    public AvailabilityTimeSlot() {}

    public AvailabilityTimeSlot(int slotID, int tutorID, int subjectID, String tutorName,
                                String subject, String startTime, String endTime,
                                int capacity, int bookedCount, String status) {
        this.slotID = slotID;
        this.tutorID = tutorID;
        this.subjectID = subjectID;
        this.tutorName = tutorName;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.bookedCount = bookedCount;
        this.status = status;
    }

    public int getSlotID() { return slotID; }
    public void setSlotID(int slotID) { this.slotID = slotID; }

    public int getTutorID() { return tutorID; }
    public void setTutorID(int tutorID) { this.tutorID = tutorID; }

    public int getSubjectID() { return subjectID; }
    public void setSubjectID(int subjectID) { this.subjectID = subjectID; }

    public String getTutorName() { return tutorName; }
    public void setTutorName(String tutorName) { this.tutorName = tutorName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getBookedCount() { return bookedCount; }
    public void setBookedCount(int bookedCount) { this.bookedCount = bookedCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}