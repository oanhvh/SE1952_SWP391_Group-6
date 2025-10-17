/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Organization;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Admin
 */
public class OrganizationDAO {
    public int createOrganization(Connection conn, int userId, String orgName, String description, String contactInfo, String address, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO Organization(UserID, OrgName, Description, ContactInfo, Address, RegistrationDate, CreatedByUserID) "
                   + "VALUES(?, ?, ?, ?, ?, CAST(SYSUTCDATETIME() AS DATE), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            if (userId <= 0) ps.setNull(1, java.sql.Types.INTEGER); else ps.setInt(1, userId);
            ps.setString(2, orgName);
            ps.setString(3, description);
            ps.setString(4, contactInfo);
            ps.setString(5, address);
            if (createdByUserId == null) ps.setNull(6, java.sql.Types.INTEGER); else ps.setInt(6, createdByUserId);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new RuntimeException("Create organization failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            throw new RuntimeException("No OrgID returned");
        }
    }
}
