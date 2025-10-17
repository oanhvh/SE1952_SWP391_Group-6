/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author NHThanh
 */
public class OrgStaffDAO {
    public void createOrgStaff(Connection conn, int userId, int orgId, int employeeCodeId, boolean verified) throws Exception {
        String sql = "INSERT INTO OrgStaff(UserID, OrgID, Position, EmployeeCodeID, EmployeeCodeVerified, ContactInfo, JoinDate) "
                   + "VALUES(?, ?, NULL, ?, ?, NULL, SYSUTCDATETIME())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, orgId);
            ps.setInt(3, employeeCodeId);
            ps.setBoolean(4, verified);
            ps.executeUpdate();
        }
    }
}
