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

public class ManagerDAO {

    public int createManager(Connection conn, int userId, String managerName, String contactInfo, String address, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO Manager(UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID) "
                + "VALUES(?, ?, ?, ?, CAST(SYSUTCDATETIME() AS DATE), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            if (userId <= 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, userId);
            }
            ps.setString(2, managerName);
            ps.setString(3, contactInfo);
            ps.setString(4, address);
            if (createdByUserId == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, createdByUserId);
            }
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Create manager failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("No ManagerID returned");
        }
    }
    
    //tìm managerID dựa theo userID
    public Integer getManagerIdByUserId(Connection conn, int userId) throws Exception {
        String sql = "SELECT ManagerID FROM Manager WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ManagerID");
                }
            }
        }
        return null;
    }
}
