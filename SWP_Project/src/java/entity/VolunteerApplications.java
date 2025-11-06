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
public class VolunteerApplications {

    private int applicationID;
    private int volunteerID;
    private int eventID;
    private String status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private Integer approvedByStaffID; // nullable
    private String motivation;         // ✅ Lý do tham gia
    private String experience;         // ✅ Kinh nghiệm
    private String staffComment;       // ✅ Ghi chú nhân viên
    private Event event;

    public VolunteerApplications() {}

    public VolunteerApplications(int applicationID, int volunteerID, int eventID, String status,
                                 LocalDateTime applicationDate, LocalDateTime approvalDate,
                                 Integer approvedByStaffID, String motivation, String experience, String staffComment) {
        this.applicationID = applicationID;
        this.volunteerID = volunteerID;
        this.eventID = eventID;
        this.status = status;
        this.applicationDate = applicationDate;
        this.approvalDate = approvalDate;
        this.approvedByStaffID = approvedByStaffID;
        this.motivation = motivation;
        this.experience = experience;
        this.staffComment = staffComment;
    }

    // 🔹 Getter & Setter
    public int getApplicationID() { return applicationID; }
    public void setApplicationID(int applicationID) { this.applicationID = applicationID; }

    public int getVolunteerID() { return volunteerID; }
    public void setVolunteerID(int volunteerID) { this.volunteerID = volunteerID; }

    public int getEventID() { return eventID; }
    public void setEventID(int eventID) { this.eventID = eventID; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public LocalDateTime getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; }

    public Integer getApprovedByStaffID() { return approvedByStaffID; }
    public void setApprovedByStaffID(Integer approvedByStaffID) { this.approvedByStaffID = approvedByStaffID; }

    public String getMotivation() { return motivation; }
    public void setMotivation(String motivation) { this.motivation = motivation; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getStaffComment() { return staffComment; }
    public void setStaffComment(String staffComment) { this.staffComment = staffComment; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}
