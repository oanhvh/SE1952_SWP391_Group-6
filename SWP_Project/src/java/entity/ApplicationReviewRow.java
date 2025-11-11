package entity;

import java.time.LocalDateTime;

public class ApplicationReviewRow {

    private int applicationID;
    private String volunteerFullName;
    private String eventName;
    private String status;
    private LocalDateTime applicationDate;
    private String motivation;
    private String experience;
    private String skills;

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getVolunteerFullName() {
        return volunteerFullName;
    }

    public void setVolunteerFullName(String volunteerFullName) {
        this.volunteerFullName = volunteerFullName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
