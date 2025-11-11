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
    private Integer managerID;
    private Integer eventID;
    private Integer generatedByUserID;
    private String reportType;
    private String reportDetails;
    private LocalDateTime generatedDate;

    public Report() {
    }

    public Report(int reportID, Integer managerID, Integer eventID, Integer generatedByUserID, String reportType, String reportDetails, LocalDateTime generatedDate) {
        this.reportID = reportID;
        this.managerID = managerID;
        this.eventID = eventID;
        this.generatedByUserID = generatedByUserID;
        this.reportType = reportType;
        this.reportDetails = reportDetails;
        this.generatedDate = generatedDate;
    }

    public int getReportID() {
        return reportID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public Integer getGeneratedByUserID() {
        return generatedByUserID;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public void setGeneratedByUserID(Integer generatedByUserID) {
        this.generatedByUserID = generatedByUserID;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

}
