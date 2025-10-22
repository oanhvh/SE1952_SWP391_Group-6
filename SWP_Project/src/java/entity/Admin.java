/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class Admin {

    private int adminID;
    private Users user;

    public Admin() {
    }

    public Admin(int adminID, Users user) {
        this.adminID = adminID;
        this.user = user;
    }

    public int getAdminID() {
        return adminID;
    }

    public Users getUser() {
        return user;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
