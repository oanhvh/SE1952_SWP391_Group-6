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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCodesDAO {

    public static class CodeInfo {

        public final int codeId;
        public final int managerId;
        public final String codeValue;
        public final boolean isUsed;
        public final Timestamp createdAt;

        public CodeInfo(int codeId, int managerId, String codeValue, boolean isUsed, Timestamp createdAt) {
            this.codeId = codeId;
            this.managerId = managerId;
            this.codeValue = codeValue;
            this.isUsed = isUsed;
            this.createdAt = createdAt;
        }
    }
    
    //kiểm tra mã hợp lệ
    public CodeInfo getValidCodeInfo(Connection conn, String codeValue) throws Exception {
        String sql = "SELECT CodeID, ManagerID, CodeValue, IsUsed, CreatedAt FROM EmployeeCodes WHERE CodeValue = ? AND IsUsed = 0"; //IsUsed = 1(đã được sử dụng)
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codeValue);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CodeInfo(
                            rs.getInt("CodeID"),
                            rs.getInt("ManagerID"),
                            rs.getString("CodeValue"),
                            rs.getBoolean("IsUsed"),
                            rs.getTimestamp("CreatedAt")
                    );
                }
            }
        }
        return null;
    }
    
    //đánh dấu mã đã dùng   
    public void markCodeUsed(Connection conn, int codeId) throws Exception {
        String sql = "UPDATE EmployeeCodes SET IsUsed = 1 WHERE CodeID = ?"; //đổi trạng thái thành đã dùng
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeId);
            ps.executeUpdate();
        }
    }
    
    //tạo mã code mới
    public int createCode(Connection conn, int managerId, String codeValue, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO EmployeeCodes(ManagerID, CodeValue, IsUsed, CreatedAt, CreatedByUserID) VALUES(?, ?, 0, SYSUTCDATETIME(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //tạo codeID
            ps.setInt(1, managerId);
            ps.setString(2, codeValue); //tạo code
            if (createdByUserId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, createdByUserId);
            }
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Create code failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {  
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("No CodeID returned");
        }
    }
    
    //list 20 code theo ID đã tạo 
    public List<CodeInfo> listCodesByManager(Connection conn, int managerId, int limit) throws Exception {
        String sql = "SELECT TOP (ISNULL(?, 20)) CodeID, ManagerID, CodeValue, IsUsed, CreatedAt FROM EmployeeCodes WHERE ManagerID = ? ORDER BY CodeID DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (limit <= 0) {
                limit = 20;
            }
            ps.setInt(1, limit);
            ps.setInt(2, managerId);
            List<CodeInfo> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CodeInfo(
                            rs.getInt("CodeID"),
                            rs.getInt("ManagerID"),
                            rs.getString("CodeValue"),
                            rs.getBoolean("IsUsed"),
                            rs.getTimestamp("CreatedAt")
                    ));
                }
            }
            return list;
        }
    }
    
    //xóa code
    public int deleteCodeByManager(Connection conn, int managerId, int codeId) throws Exception {
        String sql = "DELETE FROM EmployeeCodes WHERE CodeID = ? AND ManagerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeId);
            ps.setInt(2, managerId);
            return ps.executeUpdate();
        }
    }
}
