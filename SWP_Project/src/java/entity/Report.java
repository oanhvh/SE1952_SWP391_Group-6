/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class Report {

    private int reportID;
    private Organization org;
    private Event event;
    private Users generatedByUser;
    private String reportType;
    private String reportDetails;
    private LocalDateTime generatedDate;

    public Report() {
    }

    public Report(int reportID, Organization org, Event event, Users generatedByUser, String reportType,
            String reportDetails, LocalDateTime generatedDate) {
        this.reportID = reportID;
        this.org = org;
        this.event = event;
        this.generatedByUser = generatedByUser;
        this.reportType = reportType;
        this.reportDetails = reportDetails;
        this.generatedDate = generatedDate;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Users getGeneratedByUser() {
        return generatedByUser;
    }

    public void setGeneratedByUser(Users generatedByUser) {
        this.generatedByUser = generatedByUser;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }
}
