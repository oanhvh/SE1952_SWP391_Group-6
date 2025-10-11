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
public class Events {

    private int eventID;
    private int orgID;
    private String eventName;
    private String description;
    private String location;
    private Date startDate;
    private Date endDate;
    private String status;
    private int capacity;
    private String keywords;

    public Events() {
    }

    public Events(int eventID, int orgID, String eventName, String description, String location,
            Date startDate, Date endDate, String status, int capacity, String keywords) {
        this.eventID = eventID;
        this.orgID = orgID;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.capacity = capacity;
        this.keywords = keywords;
    }

    public int getEventID() {
        return eventID;
    }

    public int getOrgID() {
        return orgID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
