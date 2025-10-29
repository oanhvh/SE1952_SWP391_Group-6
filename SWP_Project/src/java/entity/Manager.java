package entity;

import java.time.LocalDate;

public class Manager {

    private int managerId;        
    private Integer userId;        
    private String managerName;    
    private String contactInfo;    
    private String address;       
    private LocalDate registrationDate; 
    private Integer createdByUserId;   

    public Manager() {
    }

    public Manager(int managerId, Integer userId, String managerName, String contactInfo,
                   String address, LocalDate registrationDate, Integer createdByUserId) {
        this.managerId = managerId;
        this.userId = userId;
        this.managerName = managerName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.registrationDate = registrationDate;
        this.createdByUserId = createdByUserId;
    }

    public Manager(Integer userId, String managerName, String contactInfo,
                   String address, LocalDate registrationDate, Integer createdByUserId) {
        this.userId = userId;
        this.managerName = managerName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.registrationDate = registrationDate;
        this.createdByUserId = createdByUserId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    // ===== toString() =====
    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", userId=" + userId +
                ", managerName='" + managerName + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                ", registrationDate=" + registrationDate +
                ", createdByUserId=" + createdByUserId +
                '}';
    }
}
