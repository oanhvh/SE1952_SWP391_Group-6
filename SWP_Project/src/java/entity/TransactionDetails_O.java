/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class TransactionDetails_O {

    private int detailID;
    private TransactionHeader transaction;
    private Organization org;
    private String vendorName;
    private String invoiceNumber;
    private String purpose;

    public TransactionDetails_O() {
    }

    public TransactionDetails_O(int detailID, TransactionHeader transaction, Organization org,
            String vendorName, String invoiceNumber, String purpose) {
        this.detailID = detailID;
        this.transaction = transaction;
        this.org = org;
        this.vendorName = vendorName;
        this.invoiceNumber = invoiceNumber;
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

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
