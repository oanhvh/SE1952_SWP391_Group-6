/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class Staff {

    private int staffID;
    private int userID;
    private int managerID;
    private String position;
    private int employeeCodeID;
    private boolean employeeCodeVerified;
    private String contactInfo;
    private LocalDateTime joinDate;

    public Staff() {
    }

    public Staff(int staffID, int userID, int managerID, String position, int employeeCodeID, boolean employeeCodeVerified, String contactInfo, LocalDateTime joinDate) {
        this.staffID = staffID;
        this.userID = userID;
        this.managerID = managerID;
        this.position = position;
        this.employeeCodeID = employeeCodeID;
        this.employeeCodeVerified = employeeCodeVerified;
        this.contactInfo = contactInfo;
        this.joinDate = joinDate;
    }

    public int getStaffID() {
        return staffID;
    }

    public int getUserID() {
        return userID;
    }

    public int getManagerID() {
        return managerID;
    }

    public String getPosition() {
        return position;
    }

    public int getEmployeeCodeID() {
        return employeeCodeID;
    }

    public boolean isEmployeeCodeVerified() {
        return employeeCodeVerified;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmployeeCodeID(int employeeCodeID) {
        this.employeeCodeID = employeeCodeID;
    }

    public void setEmployeeCodeVerified(boolean employeeCodeVerified) {
        this.employeeCodeVerified = employeeCodeVerified;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

}
