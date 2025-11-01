/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;

/**
 *
 * @author NHThanh
 */
public class Volunteer {

    private int volunteerID;
    private int userID;
    private String profileInfo;
    private LocalDate joinDate;
    private String status;
    private String availability;
    private boolean isSponsor;
    private String badge;

    public Volunteer() {
    }

    public Volunteer(int volunteerID, int userID, String profileInfo, LocalDate joinDate, String status, String availability, boolean isSponsor, String badge) {
        this.volunteerID = volunteerID;
        this.userID = userID;
        this.profileInfo = profileInfo;
        this.joinDate = joinDate;
        this.status = status;
        this.availability = availability;
        this.isSponsor = isSponsor;
        this.badge = badge;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public int getUserID() {
        return userID;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getStatus() {
        return status;
    }

    public String getAvailability() {
        return availability;
    }

    public boolean isIsSponsor() {
        return isSponsor;
    }

    public String getBadge() {
        return badge;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setIsSponsor(boolean isSponsor) {
        this.isSponsor = isSponsor;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

}
