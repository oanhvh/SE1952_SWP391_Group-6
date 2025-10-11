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
    private TransactionHeader transaction;
    private Volunteer volunteer;
    private Event event;
    private String purpose;

    public TransactionDetails_VE() {
    }

    public TransactionDetails_VE(int detailID, TransactionHeader transaction, Volunteer volunteer,
            Event event, String purpose) {
        this.detailID = detailID;
        this.transaction = transaction;
        this.volunteer = volunteer;
        this.event = event;
        this.purpose = purpose;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public TransactionHeader getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionHeader transaction) {
        this.transaction = transaction;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
