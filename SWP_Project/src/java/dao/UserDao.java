/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author Duc
 */
public class UserDao extends DBUtils {
//    Connection conn = null;
//    PreparedStatement pstmt = null;
//    ResultSet rs = null;

    private Users extractUser(ResultSet rs) throws Exception {
        Users user = new Users();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPasswordHash(rs.getString("PasswordHash"));
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
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public Users getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        Users user = null;
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
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
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }

    public void addUser(Users user) {
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
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Users user) {
        String sql = "UPDATE Users SET PasswordHash = ?, Role = ?, Status = ?, FullName = ?, "
                + "Email = ?, Phone = ?, Avatar = ?, UpdatedAt = ? WHERE UserID = ?";
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

    public void deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserStatus(int userID, String status) {
        String sql = "UPDATE Users SET Status = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
