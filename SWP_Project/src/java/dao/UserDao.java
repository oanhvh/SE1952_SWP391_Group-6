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

    public int addUser(Users user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, Phone, Avatar, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());
            pstmt.setString(7, user.getPhone());
            pstmt.setString(8, user.getAvatar());
            pstmt.setTimestamp(9, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            pstmt.setTimestamp(10, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateUser(Users user) {
        String sql = "UPDATE Users SET Password = ?, Role = ?, Status = ?, FullName = ?, "
                + "Email = ?, Phone = ?, Avatar = ?, UpdateAt = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPasswordHash());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getStatus());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getAvatar());
            pstmt.setTimestamp(8, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            pstmt.setInt(9, user.getUserID());

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
}

//    public List<Users> getUsers() {
//        List<Users> userList = new ArrayList<>();
//        String sql = "SELECT * FROM Users";
//
//        try (Connection conn = DBUtils.getConnection1(); 
//                PreparedStatement pstmt = conn.prepareStatement(sql); 
//                ResultSet rs = pstmt.executeQuery()) {
//
//            while (rs.next()) {
//                Users user = new Users();
//                user.setUserId(rs.getInt("UserID"));
//                user.setUsername(rs.getString("Username"));
//                user.setPassword(rs.getString("Password"));
//                user.setRole(rs.getString("Role"));
//                user.setStatus(rs.getString("Status"));
//                user.setFullName(rs.getString("FullName"));
//                user.setEmail(rs.getString("Email"));
//                user.setPhone(rs.getString("Phone"));
//                user.setAvatar(rs.getString("Avatar"));
//                user.setCreatedAt(rs.getTimestamp("CreatedAt") != null
//                        ? rs.getTimestamp("CreatedAt").toLocalDateTime()
//                        : null);
//                user.setUpdateAt(rs.getTimestamp("UpdateAt") != null
//                        ? rs.getTimestamp("UpdateAt").toLocalDateTime()
//                        : null);
//
//                userList.add(user);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return userList;
//    }
