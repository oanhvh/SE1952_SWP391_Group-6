/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Users;
import entity.Volunteer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class VolunteerDAO {
    public List<Volunteer> getAllVolunteers() {
        List<Volunteer> volunteers = new ArrayList<>();
        String sql = "SELECT v.VolunteerID, v.ProfileInfo, v.JoinDate, v.Status, v.Availability, v.IsSponsor, v.Age, " +
                "u.UserID, u.Username, u.FullName, u.Email, u.Phone, u.Role, u.Status AS UserStatus " +
                "FROM Volunteer v JOIN Users u ON v.UserID = u.UserID";

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setRole(rs.getString("Role"));
                user.setStatus(rs.getString("UserStatus"));

                Volunteer volunteer = new Volunteer();
                volunteer.setVolunteerID(rs.getInt("VolunteerID"));
                volunteer.setUser(user);
                volunteer.setProfileInfo(rs.getString("ProfileInfo"));

                Date joinDate = rs.getDate("JoinDate");
                if (joinDate != null) {
                    volunteer.setJoinDate(joinDate.toLocalDate());
                }

                volunteer.setStatus(rs.getString("Status"));
                volunteer.setAvailability(rs.getString("Availability"));
                volunteer.setSponsor(rs.getBoolean("IsSponsor"));
                volunteer.setAge(rs.getInt("Age"));

                volunteers.add(volunteer);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return volunteers;
    }

    public Volunteer getVolunteerById(int volunteerId) {
        String sql = "SELECT v.VolunteerID, v.ProfileInfo, v.JoinDate, v.Status, v.Availability, "
                + "v.IsSponsor, v.Age, "
                + "u.UserID, u.Username, u.FullName, u.Email, u.Phone, u.Role, u.Status AS UserStatus "
                + "FROM Volunteer v "
                + "JOIN Users u ON v.UserID = u.UserID "
                + "WHERE v.VolunteerID = ?";

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Users user = new Users();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    user.setRole(rs.getString("Role"));
                    user.setStatus(rs.getString("UserStatus"));

                    Volunteer volunteer = new Volunteer();
                    volunteer.setVolunteerID(rs.getInt("VolunteerID"));
                    volunteer.setUser(user);
                    volunteer.setProfileInfo(rs.getString("ProfileInfo"));

                    Date joinDate = rs.getDate("JoinDate");
                    if (joinDate != null) {
                        volunteer.setJoinDate(joinDate.toLocalDate());
                    }

                    volunteer.setStatus(rs.getString("Status"));
                    volunteer.setAvailability(rs.getString("Availability"));
                    volunteer.setSponsor(rs.getBoolean("IsSponsor"));
                    volunteer.setAge(rs.getInt("Age"));

                    return volunteer;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Volunteer> searchVolunteers(String name, String status, String availability) {
        List<Volunteer> list = new ArrayList<>();
        String sql = "SELECT v.*, u.UserID, u.FullName, u.Email, u.Phone, u.Avatar " +
                "FROM Volunteer v JOIN Users u ON v.UserID = u.UserID WHERE 1=1 ";

        if (name != null && !name.trim().isEmpty()) sql += "AND u.FullName LIKE ? ";
        if (status != null && !status.trim().isEmpty()) sql += "AND v.Status LIKE ? ";
        if (availability != null && !availability.trim().isEmpty()) sql += "AND v.Availability LIKE ? ";

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            if (name != null && !name.trim().isEmpty()) ps.setString(i++, "%" + name + "%");
            if (status != null && !status.trim().isEmpty()) ps.setString(i++, "%" + status + "%");
            if (availability != null && !availability.trim().isEmpty()) ps.setString(i++, "%" + availability + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Volunteer v = new Volunteer();
                v.setVolunteerID(rs.getInt("VolunteerID"));
                v.setProfileInfo(rs.getString("ProfileInfo"));
                v.setJoinDate(rs.getDate("JoinDate").toLocalDate());
                v.setStatus(rs.getString("Status"));
                v.setAvailability(rs.getString("Availability"));
                v.setSponsor(rs.getBoolean("IsSponsor"));
                v.setAge(rs.getInt("Age"));

                Users u = new Users();
                u.setUserID(rs.getInt("UserID"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAvatar(rs.getString("Avatar"));
                v.setUser(u);

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
