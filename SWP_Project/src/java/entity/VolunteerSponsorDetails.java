/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class VolunteerSponsorDetails {

    private int sponsorID;
    private int volunteerID;
    private String sponsorsName;
    private String contactPerson;
    private String sponsorDescription;

    public VolunteerSponsorDetails() {
    }

    public VolunteerSponsorDetails(int sponsorID, int volunteerID, String sponsorsName, String contactPerson, String sponsorDescription) {
        this.sponsorID = sponsorID;
        this.volunteerID = volunteerID;
        this.sponsorsName = sponsorsName;
        this.contactPerson = contactPerson;
        this.sponsorDescription = sponsorDescription;
    }

    public void setSponsorID(int sponsorID) {
        this.sponsorID = sponsorID;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setSponsorsName(String sponsorsName) {
        this.sponsorsName = sponsorsName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setSponsorDescription(String sponsorDescription) {
        this.sponsorDescription = sponsorDescription;
    }

    public int getSponsorID() {
        return sponsorID;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public String getSponsorsName() {
        return sponsorsName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getSponsorDescription() {
        return sponsorDescription;
    }

}
