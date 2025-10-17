/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author NHThanh
 */
public class OrgEmployeeCodesDAO {
    public static class CodeInfo {
        public final int codeId;
        public final int orgId;
        public final String codeValue;
        public final boolean isUsed;

        public CodeInfo(int codeId, int orgId, String codeValue, boolean isUsed) {
            this.codeId = codeId;
            this.orgId = orgId;
            this.codeValue = codeValue;
            this.isUsed = isUsed;
        }
    }

    public CodeInfo getValidCodeInfo(Connection conn, String codeValue) throws Exception {
        String sql = "SELECT CodeID, OrgID, CodeValue, IsUsed FROM OrgEmployeeCodes WHERE CodeValue = ? AND IsUsed = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codeValue);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CodeInfo(
                            rs.getInt("CodeID"),
                            rs.getInt("OrgID"),
                            rs.getString("CodeValue"),
                            rs.getBoolean("IsUsed")
                    );
                }
            }
        }
        return null;
    }

    public void markCodeUsed(Connection conn, int codeId) throws Exception {
        String sql = "UPDATE OrgEmployeeCodes SET IsUsed = 1 WHERE CodeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeId);
            ps.executeUpdate();
        }
    }

    // For Organization/Admin to create new employee codes
    public int createCode(Connection conn, int orgId, String codeValue, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO OrgEmployeeCodes(OrgID, CodeValue, IsUsed, CreatedAt, CreatedByUserID) "
                   + "VALUES(?, ?, 0, SYSUTCDATETIME(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orgId);
            ps.setString(2, codeValue);
            if (createdByUserId == null) ps.setNull(3, java.sql.Types.INTEGER); else ps.setInt(3, createdByUserId);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new RuntimeException("Create code failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            throw new RuntimeException("No CodeID returned");
        }
    }
}
