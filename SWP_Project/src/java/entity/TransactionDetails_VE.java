/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class TransactionDetails_VE {

    private int detailID;
    private int transactionID;
    private Integer volunteerID;
    private Integer eventID;
    private String purpose;

    public TransactionDetails_VE() {
    }

    public TransactionDetails_VE(int detailID, int transactionID, Integer volunteerID, Integer eventID, String purpose) {
        this.detailID = detailID;
        this.transactionID = transactionID;
        this.volunteerID = volunteerID;
        this.eventID = eventID;
        this.purpose = purpose;
    }

    public int getDetailID() {
        return detailID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Integer getVolunteerID() {
        return volunteerID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setVolunteerID(Integer volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
