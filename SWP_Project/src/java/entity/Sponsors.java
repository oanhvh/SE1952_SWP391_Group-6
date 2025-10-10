/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class Sponsors {

    private int sponsorID;
    private int userID;
    private String sponsorName;
    private String contactPerson;

    public Sponsors() {
    }

    public Sponsors(int sponsorID, int userID, String sponsorName, String contactPerson) {
        this.sponsorID = sponsorID;
        this.userID = userID;
        this.sponsorName = sponsorName;
        this.contactPerson = contactPerson;
    }

    public int getSponsorID() {
        return sponsorID;
    }

    public int getUserID() {
        return userID;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setSponsorID(int sponsorID) {
        this.sponsorID = sponsorID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

}
