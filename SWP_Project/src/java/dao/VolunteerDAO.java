/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Users;
import entity.VolunteerUser;

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

    //Tạo Volunteer (Thanhcocodo)
    public void createVolunteer(Connection conn, int userId) throws Exception {
        String sql = "INSERT INTO Volunteer(UserID, Status) VALUES(?, 'Pending')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
    
    //Tìm VolunteerID từ UserID - để biết user này có phải là Volunteer không (Thanhcocodo)
    public Integer getVolunteerIdByUserId(int userId) {
        String sql = "SELECT VolunteerID FROM Volunteer WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("VolunteerID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VolunteerUser> getAllVolunteers() {
        List<VolunteerUser> list = new ArrayList<>();
        String sql = "SELECT v.VolunteerID, v.ProfileInfo, v.JoinDate, v.Status, v.Availability, "
                + "v.IsSponsor, u.UserID, u.Username, u.FullName, u.Email, u.Phone, "
                + "u.Role, u.Status AS UserStatus, u.Avatar "
                + "FROM Volunteer v JOIN Users u ON v.UserID = u.UserID";

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToVolunteer(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<VolunteerUser> searchVolunteers(String name, String phone, String email, String status) {
        List<VolunteerUser> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT v.VolunteerID, v.ProfileInfo, v.JoinDate, v.Status, v.Availability, "
                + "v.IsSponsor, u.UserID, u.Username, u.FullName, u.Email, u.Phone, "
                + "u.Role, u.Status AS UserStatus, u.Avatar "
                + "FROM Volunteer v JOIN Users u ON v.UserID = u.UserID WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND u.FullName LIKE ?");
            params.add("%" + name + "%");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            sql.append(" AND u.Phone LIKE ?");
            params.add("%" + phone + "%");
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND u.Email LIKE ?");
            params.add("%" + email + "%");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND v.Status = ?");
            params.add(status);
        }

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToVolunteer(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private VolunteerUser mapResultSetToVolunteer(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setPhone(rs.getString("Phone"));
        user.setRole(rs.getString("Role"));
        user.setStatus(rs.getString("UserStatus"));
        user.setAvatar(rs.getString("Avatar"));

        VolunteerUser v = new VolunteerUser();
        v.setVolunteerID(rs.getInt("VolunteerID"));
        v.setUser(user);
        v.setProfileInfo(rs.getString("ProfileInfo"));
        Date joinDate = rs.getDate("JoinDate");
        if (joinDate != null) {
            v.setJoinDate(joinDate.toLocalDate());
        }
        v.setStatus(rs.getString("Status"));
        v.setAvailability(rs.getString("Availability"));
        v.setSponsor(rs.getBoolean("IsSponsor"));
        return v;
    }

    public VolunteerUser getVolunteerById(int volunteerId) {
        String sql = "SELECT v.VolunteerID, v.ProfileInfo, v.JoinDate, v.Status, v.Availability, "
                + "v.IsSponsor, "
                + "u.UserID, u.avatar, u.Username, u.FullName, u.Email, u.Phone, u.Role, u.Status AS UserStatus "
                + "FROM Volunteer v "
                + "JOIN Users u ON v.UserID = u.UserID "
                + "WHERE v.VolunteerID = ?";

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
                    user.setAvatar(rs.getString("avatar"));

                    VolunteerUser volunteer = new VolunteerUser();
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

                    return volunteer;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateVolunteerStatus(int volunteerID, String status) {
        String sql = "UPDATE Volunteer SET status = ? WHERE volunteerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, volunteerID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
   
}
