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

/**
 *
 * @author admin
 */
public class UserDao {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public List<Users> getUsers() {
        List<Users> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DBUtils.getConnection1(); 
                PreparedStatement pstmt = conn.prepareStatement(sql); 
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                user.setStatus(rs.getString("Status"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setAvatar(rs.getString("Avatar"));
                user.setCreatedAt(rs.getTimestamp("CreatedAt") != null
                        ? rs.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);
                user.setUpdateAt(rs.getTimestamp("UpdateAt") != null
                        ? rs.getTimestamp("UpdateAt").toLocalDateTime()
                        : null);

                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

}
