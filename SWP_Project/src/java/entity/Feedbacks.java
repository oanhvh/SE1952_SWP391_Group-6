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
public class Feedbacks {

    private int feedbackID;
    private int userID;
    private String details;
    private Date feedbackDate;
    private String status;

    public Feedbacks() {
    }

    public Feedbacks(int feedbackID, int userID, String details, Date feedbackDate, String status) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.details = details;
        this.feedbackDate = feedbackDate;
        this.status = status;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public int getUserID() {
        return userID;
    }

    public String getDetails() {
        return details;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public String getStatus() {
        return status;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
