/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author NHThanh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO extends DBUtils{
    
    //tạo Staff
    public void createStaff(Connection conn, int userId, int managerId, int employeeCodeId, boolean verified) throws Exception {
        String sql = "INSERT INTO Staff(UserID, ManagerID, Position, EmployeeCodeID, EmployeeCodeVerified, ContactInfo, JoinDate) "
                + "VALUES(?, ?, NULL, ?, ?, NULL, SYSUTCDATETIME())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, managerId);
            ps.setInt(3, employeeCodeId);
            ps.setBoolean(4, verified);
            ps.executeUpdate();
        }
    }
    
    public Integer getStaffIdByUserId(int userId) {
        String sql = "SELECT StaffID FROM Staff WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("StaffID");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getManagerIdByUserId(int userId) {
        String sql = "SELECT ManagerID FROM Staff WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ManagerID");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
