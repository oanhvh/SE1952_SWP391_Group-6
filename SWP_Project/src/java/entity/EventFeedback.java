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
public class EventFeedback {

    private int commentID;
    private int eventID;
    private Integer volunteerID;
    private String commentText;
    private LocalDateTime createdAt;
    private String replyText;
    private Integer replyByUserID;

    public EventFeedback() {
    }

    public EventFeedback(int commentID, int eventID, Integer volunteerID, String commentText, LocalDateTime createdAt, String replyText, Integer replyByUserID) {
        this.commentID = commentID;
        this.eventID = eventID;
        this.volunteerID = volunteerID;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.replyText = replyText;
        this.replyByUserID = replyByUserID;
    }

    public int getCommentID() {
        return commentID;
    }

    public int getEventID() {
        return eventID;
    }

    public Integer getVolunteerID() {
        return volunteerID;
    }

    public String getCommentText() {
        return commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReplyText() {
        return replyText;
    }

    public Integer getReplyByUserID() {
        return replyByUserID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setVolunteerID(Integer volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public void setReplyByUserID(Integer replyByUserID) {
        this.replyByUserID = replyByUserID;
    }

}
