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
    private Users user;
    private String profileInfo;
    private LocalDate joinDate;
    private String status;
    private String availability;
    private boolean isSponsor;
    private Integer age;

    public Volunteer() {
    }

    public Volunteer(int volunteerID, Users user, String profileInfo, LocalDate joinDate, String status,
            String availability, boolean isSponsor, Integer age) {
        this.volunteerID = volunteerID;
        this.user = user;
        this.profileInfo = profileInfo;
        this.joinDate = joinDate;
        this.status = status;
        this.availability = availability;
        this.isSponsor = isSponsor;
        this.age = age;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isSponsor() {
        return isSponsor;
    }

    public void setSponsor(boolean isSponsor) {
        this.isSponsor = isSponsor;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
