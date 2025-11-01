package entity;

import java.util.Date;

public class Participation {
    private int applicationID;
    private String fullName;
    private String eventName;
    private String status;
    private Date applicationDate;
    private String approvedByStaffName;

    public Participation() {}

    public int getApplicationID() { return applicationID; }
    public void setApplicationID(int applicationID) { this.applicationID = applicationID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getApplicationDate() { return applicationDate; }
    public void setApplicationDate(Date applicationDate) { this.applicationDate = applicationDate; }

    public String getApprovedByStaffName() { return approvedByStaffName; }
    public void setApprovedByStaffName(String approvedByStaffName) { this.approvedByStaffName = approvedByStaffName; }
}
