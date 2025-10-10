/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author NHThanh
 */
public class VolunteerApplications {

    private int participationID;
    private int volunteerID;
    private int eventID;
    private String status;
    private Date applicationDate;
    private Date approvalDate;

    public VolunteerApplications() {
    }

    public VolunteerApplications(int participationID, int volunteerID, int eventID,
            String status, Date applicationDate, Date approvalDate) {
        this.participationID = participationID;
        this.volunteerID = volunteerID;
        this.eventID = eventID;
        this.status = status;
        this.applicationDate = applicationDate;
        this.approvalDate = approvalDate;
    }

    public int getParticipationID() {
        return participationID;
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

    public Date getApplicationDate() {
        return applicationDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setParticipationID(int participationID) {
        this.participationID = participationID;
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

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

}
