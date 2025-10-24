/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class TransactionHeader {

    private int transactionID;
    private Integer userID;
    private Integer managerID;
    private Integer eventID;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionType;
    private String status;
    private String paymentMethod;
    private String referenceNumber;
    private String currency;

    public TransactionHeader() {
    }

    public TransactionHeader(int transactionID, Integer userID, Integer managerID, Integer eventID, BigDecimal amount, LocalDateTime transactionDate, String transactionType, String status, String paymentMethod, String referenceNumber, String currency) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.managerID = managerID;
        this.eventID = eventID;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.referenceNumber = referenceNumber;
        this.currency = currency;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
