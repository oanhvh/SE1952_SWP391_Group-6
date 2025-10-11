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
    private Volunteer volunteer;
    private Event event;
    private String status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private OrgStaff approvedByStaff;
    private String notes;

    public VolunteerApplications() {
    }

    public VolunteerApplications(int applicationID, Volunteer volunteer, Event event, String status,
            LocalDateTime applicationDate, LocalDateTime approvalDate,
            OrgStaff approvedByStaff, String notes) {
        this.applicationID = applicationID;
        this.volunteer = volunteer;
        this.event = event;
        this.status = status;
        this.applicationDate = applicationDate;
        this.approvalDate = approvalDate;
        this.approvedByStaff = approvedByStaff;
        this.notes = notes;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public OrgStaff getApprovedByStaff() {
        return approvedByStaff;
    }

    public void setApprovedByStaff(OrgStaff approvedByStaff) {
        this.approvedByStaff = approvedByStaff;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
