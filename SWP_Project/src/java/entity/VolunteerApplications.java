/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class VolunteerApplications {

    private int applicationID;
    private int volunteerID;
    private int eventID;
    private String status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private Integer approvedByStaffID; // nullable
    private Event event;



    public VolunteerApplications() {
    }

    public VolunteerApplications(int applicationID, int volunteerID, int eventID, String status, LocalDateTime applicationDate, LocalDateTime approvalDate, Integer approvedByStaffID) {
        this.applicationID = applicationID;
        this.volunteerID = volunteerID;
        this.eventID = eventID;
        this.status = status;
        this.applicationDate = applicationDate;
        this.approvalDate = approvalDate;
        this.approvedByStaffID = approvedByStaffID;
    }
    
    public int getApplicationID() {
        return applicationID;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public int getEventID() {
        return eventID;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public Integer getApprovedByStaffID() {
        return approvedByStaffID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void setApprovedByStaffID(Integer approvedByStaffID) {
        this.approvedByStaffID = approvedByStaffID;
    }
    public Event getEvent() {
    return event;
}

public void setEvent(Event event) {
    this.event = event;
}


}
