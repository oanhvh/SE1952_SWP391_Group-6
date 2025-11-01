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
public class OrgStaff {

    private int staffID;
    private Users user;
    private Organization org;
    private String position;
    private OrgEmployeeCodes employeeCode;
    private boolean employeeCodeVerified;
    private String contactInfo;
    private LocalDateTime joinDate;

    public OrgStaff() {
    }

    public OrgStaff(int staffID, Users user, Organization org, String position, OrgEmployeeCodes employeeCode,
            boolean employeeCodeVerified, String contactInfo, LocalDateTime joinDate) {
        this.staffID = staffID;
        this.user = user;
        this.org = org;
        this.position = position;
        this.employeeCode = employeeCode;
        this.employeeCodeVerified = employeeCodeVerified;
        this.contactInfo = contactInfo;
        this.joinDate = joinDate;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public OrgEmployeeCodes getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(OrgEmployeeCodes employeeCode) {
        this.employeeCode = employeeCode;
    }

    public boolean isEmployeeCodeVerified() {
        return employeeCodeVerified;
    }

    public void setEmployeeCodeVerified(boolean employeeCodeVerified) {
        this.employeeCodeVerified = employeeCodeVerified;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
