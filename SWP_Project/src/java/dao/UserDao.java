/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author Duc
 */
public class UserDao extends DBUtils{
//    Connection conn = null;
//    PreparedStatement pstmt = null;
//    ResultSet rs = null;

    private Users extractUser(ResultSet rs) throws Exception {
        Users user = new Users();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPasswordHash(rs.getString("passwordHash"));
        user.setRole(rs.getString("Role"));
        user.setStatus(rs.getString("Status"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setDateOfBirth(rs.getDate("DateOfBirth") != null
                ? rs.getDate("DateOfBirth").toLocalDate()
                : null);
        user.setPhone(rs.getString("Phone"));
        user.setAvatar(rs.getString("Avatar"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt") != null
                ? rs.getTimestamp("CreatedAt").toLocalDateTime()
                : null);
        user.setUpdatedAt(rs.getTimestamp("UpdatedAt") != null
                ? rs.getTimestamp("UpdatedAt").toLocalDateTime()
                : null);

        return user;
    }

    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public Users getUserbyUsername(String Username) {
        String sql = "SELECT * from Users where Username = ?";
        Users user = null;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // dùng tạm để giữ cho code cũ tiếp tục chạy mà không bị crash do thay đổi tên phương thức
    public Users getUserbyUsername(String username) {
        return getUserByUsername(username);
    }

    public boolean isUsernameExisted(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }

    // tạo một user mới trong bảng Users
    public int createUser(Connection conn, Users user, boolean hashPassword) throws Exception {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, DateOfBirth, Phone, Avatar, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSUTCDATETIME(), SYSUTCDATETIME())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            String pwd = user.getPasswordHash();
            if (hashPassword && pwd != null) {
                pwd = sha256(pwd);
            }
            pstmt.setString(2, pwd);
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());
            pstmt.setDate(7, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            pstmt.setString(8, user.getPhone());
            pstmt.setString(9, user.getAvatar());
            int affected = pstmt.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Creating user failed, no rows affected.");
            }
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("Creating user failed, no ID obtained.");
        }
    }

    public void addUser(Users user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, DateOfBirth, Phone, Avatar, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());
            pstmt.setDate(7, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            pstmt.setString(8, user.getPhone());
            pstmt.setString(9, user.getAvatar());
            pstmt.setTimestamp(10, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            pstmt.setTimestamp(11, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Users user) {
        String sql = "UPDATE Users SET PasswordHash = ?, Role = ?, Status = ?, FullName = ?, "
                + "Email = ?, DateOfBirth = ?, Phone = ?, Avatar = ?, UpdatedAt = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPasswordHash());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getStatus());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getEmail());
            pstmt.setDate(6, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            pstmt.setString(7, user.getPhone());
            pstmt.setString(8, user.getAvatar());
            pstmt.setTimestamp(9, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            pstmt.setInt(10, user.getUserID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserStatus(int UserID, int Status) {
        String sql = "UPDATE Users SET Status = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Status);
            pstmt.setInt(2, UserID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Users> searchUser(String name, String role, String phone) {
        List<Users> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1");

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND fullName LIKE ?");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND role LIKE ?");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            sql.append(" AND phone LIKE ?");
        }

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (name != null && !name.trim().isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(index++, "%" + role + "%");
            }
            if (phone != null && !phone.trim().isEmpty()) {
                ps.setString(index, "%" + phone + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Users getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        Users user = null;
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public boolean updateProfileByUsername(Users user) {
        String sql = "UPDATE Users SET FullName = ?, Email = ?, Phone = ?, UpdatedAt = ? WHERE Username = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
            ps.setString(5, user.getUsername());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ghi lại log thời gian hoạt động gần nhất, xét từ ngay khi đăng nhập vào
    public boolean updateLastLogin(int userId) {
        String sql = "UPDATE Users SET UpdatedAt = SYSUTCDATETIME() WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(int userId) {
        String sql = "SELECT Role FROM Users WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}


}
