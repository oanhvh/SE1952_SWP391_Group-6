/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class TransactionDetails_M {

    private int detailID;
    private int transactionID;
    private Integer managerID;
    private String vendorName;
    private String invoiceNumber;
    private String purpose;

    public TransactionDetails_M() {
    }

    public TransactionDetails_M(int detailID, int transactionID, Integer managerID, String vendorName, String invoiceNumber, String purpose) {
        this.detailID = detailID;
        this.transactionID = transactionID;
        this.managerID = managerID;
        this.vendorName = vendorName;
        this.invoiceNumber = invoiceNumber;
        this.purpose = purpose;
    }

    public int getDetailID() {
        return detailID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
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

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
