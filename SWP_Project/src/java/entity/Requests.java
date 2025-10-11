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
public class Requests {

    private int requestID;
    private Users user;
    private Organization org;
    private Event event;
    private String requestType;
    private String notes;
    private LocalDateTime requestDate;
    private String status;
    private Users approvedByUser;
    private LocalDateTime approvalDate;

    public Requests() {
    }

    public Requests(int requestID, Users user, Organization org, Event event, String requestType,
            String notes, LocalDateTime requestDate, String status, Users approvedByUser,
            LocalDateTime approvalDate) {
        this.requestID = requestID;
        this.user = user;
        this.org = org;
        this.event = event;
        this.requestType = requestType;
        this.notes = notes;
        this.requestDate = requestDate;
        this.status = status;
        this.approvedByUser = approvedByUser;
        this.approvalDate = approvalDate;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(Users approvedByUser) {
        this.approvedByUser = approvedByUser;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }
}
