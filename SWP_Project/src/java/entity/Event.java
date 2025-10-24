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
public class Event {

    private int eventID;
    private int managerID;
    private Integer createdByStaffID; // nullable
    private String eventName;
    private String description;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private int capacity;
    private String image;
    private int categoryID;
    private LocalDateTime createdAt;

    public Event() {
    }

    public Event(int eventID, int managerID, Integer createdByStaffID, String eventName, String description, String location, LocalDateTime startDate, LocalDateTime endDate, String status, int capacity, String image, int categoryID, LocalDateTime createdAt) {
        this.eventID = eventID;
        this.managerID = managerID;
        this.createdByStaffID = createdByStaffID;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.capacity = capacity;
        this.image = image;
        this.categoryID = categoryID;
        this.createdAt = createdAt;
    }

    public int getEventID() {
        return eventID;
    }

    public int getManagerID() {
        return managerID;
    }

    public Integer getCreatedByStaffID() {
        return createdByStaffID;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getImage() {
        return image;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setCreatedByStaffID(Integer createdByStaffID) {
        this.createdByStaffID = createdByStaffID;
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

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
