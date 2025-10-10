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
public class Volunteer {

    private int volunteerID;
    private int userID;
    private String profileInfo;
    private Date joinDate;
    private String status;
    private String availability;

    public Volunteer() {
    }

    public Volunteer(int volunteerID, int userID, String profileInfo, Date joinDate,
            String status, String availability) {
        this.volunteerID = volunteerID;
        this.userID = userID;
        this.profileInfo = profileInfo;
        this.joinDate = joinDate;
        this.status = status;
        this.availability = availability;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public String getStatus() {
        return status;
    }

    public String getAvailability() {
        return availability;
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

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

}
