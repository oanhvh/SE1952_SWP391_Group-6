/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author NHThanh
 */
public class Users {

    private int userID;
    private String username;
    private String passwordHash;
    private String role;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    private String googleID;
    private String facebookID;
    private String loginProvider;
    private boolean isEmailVerified;
    private boolean isPhoneVerified;
    private LocalDate dateOfBirth;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Users() {
    }

    public Users(int userID, String username, String passwordHash, String role, String fullName,
            String email, String phone, String status, String googleID, String facebookID,
            String loginProvider, boolean isEmailVerified, boolean isPhoneVerified,
            LocalDate dateOfBirth, String avatar, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.googleID = googleID;
        this.facebookID = facebookID;
        this.loginProvider = loginProvider;
        this.isEmailVerified = isEmailVerified;
        this.isPhoneVerified = isPhoneVerified;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public String getGoogleID() {
        return googleID;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public boolean isIsEmailVerified() {
        return isEmailVerified;
    }

    public boolean isIsPhoneVerified() {
        return isPhoneVerified;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public void setIsPhoneVerified(boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
