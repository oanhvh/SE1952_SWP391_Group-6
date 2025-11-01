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

public class StaffDAO {
    
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
}
