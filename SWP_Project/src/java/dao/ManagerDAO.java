/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author NHThanh
 */
import entity.Manager;
import entity.Users;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {

    //Tìm managerID dựa theo userID (Thanhcocodo)
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

    // Lấy thông tin Manager theo ManagerID (không join Users)
    public Manager getManagerById(int managerId) {
        Manager manager = null;
        String sql = "SELECT ManagerID, ManagerName, ContactInfo, Address, RegistrationDate FROM Manager WHERE ManagerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    manager = new Manager();
                    manager.setManagerID(rs.getInt("ManagerID"));
                    manager.setManagerName(rs.getString("ManagerName"));
                    manager.setContactInfo(rs.getString("ContactInfo"));
                    manager.setAddress(rs.getString("Address"));
                    Date reg = rs.getDate("RegistrationDate");
                    if (reg != null) {
                        manager.setRegistrationDate(reg.toLocalDate());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager;
    }

    public int addManager(Manager manager) {
        String sql = "INSERT INTO Manager (UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, manager.getUser().getUserID());
            pstmt.setString(2, manager.getManagerName());
            pstmt.setString(3, manager.getContactInfo());
            pstmt.setString(4, manager.getAddress());
            pstmt.setDate(5, Date.valueOf(manager.getRegistrationDate()));
            pstmt.setInt(6, manager.getCreatedBy() != null ? manager.getCreatedBy().getUserID() : 0);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Manager> getAllManagers() {
        List<Manager> list = new ArrayList<>();

        String sql = "SELECT "
                + "m.ManagerID, m.ManagerName, m.ContactInfo, m.Address, m.RegistrationDate, "
                + "u.UserID, u.Username, u.Phone, u.Status, u.FullName, u.Email, u.Role, u.CreatedAt "
                + "FROM Manager m "
                + "JOIN Users u ON m.UserID = u.UserID";

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPhone(rs.getString("Phone"));
                user.setStatus(rs.getString("Status"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getString("Role"));

                Timestamp created = rs.getTimestamp("CreatedAt");
                if (created != null) {
                    user.setCreatedAt(created.toLocalDateTime());
                }

                Manager manager = new Manager();
                manager.setManagerID(rs.getInt("ManagerID"));
                manager.setManagerName(rs.getString("ManagerName"));
                manager.setContactInfo(rs.getString("ContactInfo"));
                manager.setAddress(rs.getString("Address"));

                Date regDate = rs.getDate("RegistrationDate");
                if (regDate != null) {
                    manager.setRegistrationDate(regDate.toLocalDate());
                }

                manager.setUser(user);
                list.add(manager);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Manager getManagerByUserId(int userId) {
        Manager manager = null;
        String sql = "SELECT m.*, u.* FROM Manager m "
                + "JOIN Users u ON m.UserID = u.UserID WHERE u.UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setRole(rs.getString("Role"));
                user.setStatus(rs.getString("Status"));
                user.setDateOfBirth(rs.getDate("DateOfBirth") != null
                        ? rs.getDate("DateOfBirth").toLocalDate() : null);

                manager = new Manager();
                manager.setManagerID(rs.getInt("ManagerID"));
                manager.setManagerName(rs.getString("ManagerName"));
                manager.setContactInfo(rs.getString("ContactInfo"));
                manager.setAddress(rs.getString("Address"));
                manager.setRegistrationDate(rs.getDate("RegistrationDate") != null
                        ? rs.getDate("RegistrationDate").toLocalDate() : null);
                manager.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager;
    }

    public List<Manager> searchManagers(String managerName, String phone, String status) {
        List<Manager> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT m.*, u.* FROM Manager m "
                + "JOIN Users u ON m.UserID = u.UserID WHERE 1=1");

        if (managerName != null && !managerName.trim().isEmpty()) {
            sql.append(" AND m.ManagerName LIKE ?");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            sql.append(" AND u.Phone LIKE ?");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND u.Status = ?");
        }

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (managerName != null && !managerName.trim().isEmpty()) {
                ps.setString(index++, "%" + managerName + "%");
            }
            if (phone != null && !phone.trim().isEmpty()) {
                ps.setString(index++, "%" + phone + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, status);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setStatus(rs.getString("Status"));

                Manager manager = new Manager();
                manager.setManagerID(rs.getInt("ManagerID"));
                manager.setManagerName(rs.getString("ManagerName"));
                manager.setUser(user);

                list.add(manager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getFullNameByManagerId(int managerId) {
        String fullName = null;
        String sql = "SELECT u.FullName "
                + "FROM Manager m "
                + "JOIN Users u ON m.UserID = u.UserID "
                + "WHERE m.ManagerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("FullName");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fullName;
    }
}
