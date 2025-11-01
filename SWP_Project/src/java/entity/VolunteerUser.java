package entity;

import java.time.LocalDate;

public class VolunteerUser {
    private int volunteerID;
    private Users user;
    private String profileInfo;
    private LocalDate joinDate;
    private String status;
    private String availability;
    private boolean isSponsor;
    private String badge;

    public VolunteerUser() {
    }

    public VolunteerUser(int volunteerID, Users user, String profileInfo, LocalDate joinDate, String status, String availability, boolean isSponsor, String badge) {
        this.volunteerID = volunteerID;
        this.user = user;
        this.profileInfo = profileInfo;
        this.joinDate = joinDate;
        this.status = status;
        this.availability = availability;
        this.isSponsor = isSponsor;
        this.badge = badge;
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

    public void setSponsor(boolean sponsor) {
        isSponsor = sponsor;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
