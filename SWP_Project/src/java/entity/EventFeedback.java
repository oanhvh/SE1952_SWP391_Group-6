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
    private Event event;
    private Volunteer volunteer;
    private String commentText;
    private LocalDateTime createdAt;
    private String replyText;
    private Users replyByUser;

    public EventFeedback() {
    }

    public EventFeedback(int commentID, Event event, Volunteer volunteer, String commentText,
            LocalDateTime createdAt, String replyText, Users replyByUser) {
        this.commentID = commentID;
        this.event = event;
        this.volunteer = volunteer;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.replyText = replyText;
        this.replyByUser = replyByUser;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public Users getReplyByUser() {
        return replyByUser;
    }

    public void setReplyByUser(Users replyByUser) {
        this.replyByUser = replyByUser;
    }
}
