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

public class StaffDAO extends DBUtils {

    //tạo Staff (Thanhcocodo)
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

    //Tìm StaffID từ UserID - để biết user này có phải là Staff không (Thanhcocodo)
    public Integer getStaffIdByUserId(int userId) {
        String sql = "SELECT StaffID FROM Staff WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("StaffID");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getManagerIdByUserId(int userId) {
        String sql = "SELECT ManagerID FROM Staff WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public String getUserNameByStaffId(int staffId) {
        String sql = "SELECT u.FullName FROM Users u "
                + "JOIN Staff s ON u.UserID = s.UserID "
                + "WHERE s.StaffID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FullName");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Chuyển từ ResultSet sang Java Objects (Thanhcocodo)
    public java.util.List<entity.Users> getStaffUsersByManager(int managerId, String name, String phone, String status) {
        java.util.List<entity.Users> list = new java.util.ArrayList<>();
        try (Connection conn = DBUtils.getConnection1(); ResultSet rs = getStaffUsersByManagerRaw(conn, managerId, name, phone, status)) {
            while (rs.next()) {
                try {
                    entity.Users u = new entity.Users();
                    u.setUserID(rs.getInt("UserID"));
                    u.setUsername(rs.getString("Username"));
                    u.setPasswordHash(rs.getString("PasswordHash"));
                    u.setRole(rs.getString("Role"));
                    u.setStatus(rs.getString("Status"));
                    u.setFullName(rs.getString("FullName"));
                    u.setEmail(rs.getString("Email"));
                    u.setPhone(rs.getString("Phone"));

                    //Chuyển từ DATETIME sang LocalDateTime
                    java.sql.Timestamp created = rs.getTimestamp("CreatedAt");
                    if (created != null) {
                        u.setCreatedAt(created.toLocalDateTime());
                    }
                    java.sql.Timestamp updated = rs.getTimestamp("UpdatedAt");
                    if (updated != null) {
                        u.setUpdatedAt(updated.toLocalDateTime());
                    }
                    list.add(u);
                } catch (Exception ignore) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Lấy tất cả thông tin user của các staff thuộc manager này (Thanhcocodo)
    public ResultSet getStaffUsersByManagerRaw(Connection conn, int managerId, String name, String phone, String status) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT u.* FROM Staff s JOIN Users u ON s.UserID = u.UserID WHERE s.ManagerID = ?");
        
        //Thêm điều kiện tìm kiếm
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND u.FullName LIKE ?"); // Tìm theo tên
        }
        if (phone != null && !phone.trim().isEmpty()) {
            sql.append(" AND u.Phone LIKE ?"); // Tìm theo số điện thoại
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND u.Status = ?"); // Lọc theo trạng thái
        }

        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int idx = 1;
        ps.setInt(idx++, managerId);
        if (name != null && !name.trim().isEmpty()) {
            ps.setString(idx++, "%" + name + "%");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            ps.setString(idx++, "%" + phone + "%");
        }
        if (status != null && !status.trim().isEmpty()) {
            ps.setString(idx++, status);
        }
        return ps.executeQuery();
    }
}
