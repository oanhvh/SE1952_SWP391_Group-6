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
public class Manager {

    private int managerID;
    private Users userID;
    private String managerName;
    private String contactInfo;
    private String address;
    private LocalDate registrationDate;
    private Users createdBy;

    public Manager() {
    }

    public Manager(int managerID, Users user, String managerName, String contactInfo,
            String address, LocalDate registrationDate, Users createdBy) {
        this.managerID = managerID;
        this.userID = user;
        this.managerName = managerName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.registrationDate = registrationDate;
        this.createdBy = createdBy;
    }

    public int getManagerID() {
        return managerID;
    }

    public Users getUser() {
        return userID;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setUser(Users user) {
        this.userID = user;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    // ===== toString() =====
    @Override
    public String toString() {
        return "Manager{"
                + "managerId=" + managerID
                + ", userId=" + userID
                + ", managerName='" + managerName + '\''
                + ", contactInfo='" + contactInfo + '\''
                + ", address='" + address + '\''
                + ", registrationDate=" + registrationDate
                + ", createdByUserId=" + createdBy
                + '}';
    }

}
