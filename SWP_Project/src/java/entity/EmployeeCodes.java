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
public class EmployeeCodes {

    private int codeID;
    private int managerID;
    private String codeValue;
    private boolean isUsed;
    private LocalDateTime createdAt;
    private Integer createdByUserID;

    public EmployeeCodes() {
    }

    public EmployeeCodes(int codeID, int managerID, String codeValue, boolean isUsed, LocalDateTime createdAt, Integer createdByUserID) {
        this.codeID = codeID;
        this.managerID = managerID;
        this.codeValue = codeValue;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.createdByUserID = createdByUserID;
    }

    public int getCodeID() {
        return codeID;
    }

    public int getManagerID() {
        return managerID;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCodeID(int codeID) {
        this.codeID = codeID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedByUserID(Integer createdByUserID) {
        this.createdByUserID = createdByUserID;
    }
}
