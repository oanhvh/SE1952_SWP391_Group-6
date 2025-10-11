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
    private Volunteer volunteer;
    private String sponsorsName;
    private String contactPerson;
    private String sponsorDescription;

    public VolunteerSponsorDetails() {
    }

    public VolunteerSponsorDetails(int sponsorID, Volunteer volunteer, String sponsorsName, String contactPerson, String sponsorDescription) {
        this.sponsorID = sponsorID;
        this.volunteer = volunteer;
        this.sponsorsName = sponsorsName;
        this.contactPerson = contactPerson;
        this.sponsorDescription = sponsorDescription;
    }

    public int getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(int sponsorID) {
        this.sponsorID = sponsorID;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public String getSponsorsName() {
        return sponsorsName;
    }

    public void setSponsorsName(String sponsorsName) {
        this.sponsorsName = sponsorsName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getSponsorDescription() {
        return sponsorDescription;
    }

    public void setSponsorDescription(String sponsorDescription) {
        this.sponsorDescription = sponsorDescription;
    }
}
