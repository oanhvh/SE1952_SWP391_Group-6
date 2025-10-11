/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class OrgEmployeeCodes {

    private int codeID;
    private Organization org;
    private String codeValue;
    private boolean isUsed;
    private LocalDateTime createdAt;
    private Users createdByUser;

    public OrgEmployeeCodes() {
    }

    public OrgEmployeeCodes(int codeID, Organization org, String codeValue, boolean isUsed,
            LocalDateTime createdAt, Users createdByUser) {
        this.codeID = codeID;
        this.org = org;
        this.codeValue = codeValue;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.createdByUser = createdByUser;
    }

    public int getCodeID() {
        return codeID;
    }

    public void setCodeID(int codeID) {
        this.codeID = codeID;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Users getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(Users createdByUser) {
        this.createdByUser = createdByUser;
    }
}
