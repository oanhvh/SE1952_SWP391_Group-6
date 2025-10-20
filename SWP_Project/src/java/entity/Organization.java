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
public class Organization {

    private int orgID;
    private Users user;               // FK: UserID
    private String orgName;
    private String description;
    private String contactInfo;
    private String address;
    private LocalDate registrationDate;
    private Users createdByUser;      // FK: CreatedByUserID
    private String category;          // NEW COLUMN

    public Organization() {
    }

    public Organization(int orgID, Users user, String orgName, String description, String contactInfo,
            String address, LocalDate registrationDate, Users createdByUser, String category) {
        this.orgID = orgID;
        this.user = user;
        this.orgName = orgName;
        this.description = description;
        this.contactInfo = contactInfo;
        this.address = address;
        this.registrationDate = registrationDate;
        this.createdByUser = createdByUser;
        this.category = category;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Users getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(Users createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
