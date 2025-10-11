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
public class Organizations {

    private int orgID;
    private String orgName;
    private String description;
    private String contactInfo;
    private String address;
    private Date registrationDate;

    public Organizations() {
    }

    public Organizations(int orgID, String orgName, String description, String contactInfo,
            String address, Date registrationDate) {
        this.orgID = orgID;
        this.orgName = orgName;
        this.description = description;
        this.contactInfo = contactInfo;
        this.address = address;
        this.registrationDate = registrationDate;
    }

    public int getOrgID() {
        return orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getDescription() {
        return description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

}
