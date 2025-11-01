/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.VolunteerApplications;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DucNM
 */
public class VolunteerApplicationsDAO extends DBUtils {

    private VolunteerApplications extractVolunteerApplications(ResultSet rs) throws Exception {
        VolunteerApplications app = new VolunteerApplications();
        app.setApplicationID(rs.getInt("ApplicationID"));
        app.setVolunteerID(rs.getInt("VolunteerID"));
        app.setEventID(rs.getInt("EventID"));
        app.setStatus(rs.getString("Status"));
        app.setApplicationDate(rs.getTimestamp("ApplicationDate").toLocalDateTime());

        Timestamp approvalTs = rs.getTimestamp("ApprovalDate");
        app.setApprovalDate(approvalTs != null ? approvalTs.toLocalDateTime() : null);

        int approvedBy = rs.getInt("ApprovedByStaffID");
        if (rs.wasNull()) {
            app.setApprovedByStaffID(null);
        } else {
            app.setApprovedByStaffID(approvedBy);
        }

        return app;
    }

    public List<VolunteerApplications> getAllVolunteerApplications() {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "SELECT * FROM VolunteerApplication";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                VolunteerApplications app = extractVolunteerApplications(rs);
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addVolunteerApplication(VolunteerApplications app) {
        String sql = "INSERT INTO VolunteerApplication (VolunteerID, EventID, Status, ApplicationDate, ApprovalDate, ApprovedByStaffID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, app.getVolunteerID());
            pstmt.setInt(2, app.getEventID());
            pstmt.setString(3, app.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(app.getApplicationDate()));

            if (app.getApprovalDate() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(app.getApprovalDate()));
            } else {
                pstmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (app.getApprovedByStaffID() != null) {
                pstmt.setInt(6, app.getApprovedByStaffID());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVolunteerApplication(VolunteerApplications app) {
        String sql = "UPDATE VolunteerApplication SET VolunteerID = ?, EventID = ?, Status = ?, "
                + "ApplicationDate = ?, ApprovalDate = ?, ApprovedByStaffID = ? WHERE ApplicationID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, app.getVolunteerID());
            pstmt.setInt(2, app.getEventID());
            pstmt.setString(3, app.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(app.getApplicationDate()));

            if (app.getApprovalDate() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(app.getApprovalDate()));
            } else {
                pstmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (app.getApprovedByStaffID() != null) {
                pstmt.setInt(6, app.getApprovedByStaffID());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }

            pstmt.setInt(7, app.getApplicationID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVolunteerApplication(int applicationID) {
        String sql = "DELETE FROM VolunteerApplication WHERE ApplicationID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, applicationID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VolunteerApplications> getVolunteerApplicationsByStatus(String status) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "SELECT * FROM VolunteerApplication WHERE Status = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VolunteerApplications app = extractVolunteerApplications(rs);
                    list.add(app);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<VolunteerApplications> getApplicationsByVolunteer(int volunteerID) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "SELECT * FROM VolunteerApplication WHERE VolunteerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, volunteerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VolunteerApplications app = extractVolunteerApplications(rs);
                    list.add(app);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(int applicationID, String status, Integer approvedByStaffID) {
        String sql = "UPDATE VolunteerApplication SET Status = ?, ApprovalDate = ?, ApprovedByStaffID = ? WHERE ApplicationID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            if (approvedByStaffID != null) {
                pstmt.setInt(3, approvedByStaffID);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.setInt(4, applicationID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
