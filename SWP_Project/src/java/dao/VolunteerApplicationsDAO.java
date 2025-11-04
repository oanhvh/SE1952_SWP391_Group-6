/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Event;
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
    
    // ✅ Lấy volunteerID từ userID
    private int getVolunteerIdByUserId(int userID) {
        String sql = "SELECT VolunteerID FROM Volunteer WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("VolunteerID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Apply event (chặn trùng sự kiện hoặc trùng ngày)
    public boolean applyEventByUserId(int userID, int eventID) {
        int volunteerID = getVolunteerIdByUserId(userID);
        if (volunteerID == -1) return false;

        String checkSql = """
            SELECT COUNT(*) FROM VolunteerApplications va
            JOIN Event e1 ON va.EventID = e1.EventID
            JOIN Event e2 ON e2.EventID = ?
            WHERE va.VolunteerID = ?
            AND (va.EventID = ? OR
                (e1.StartDate <= e2.EndDate AND e1.EndDate >= e2.StartDate))
        """;

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, eventID);
            checkPs.setInt(2, volunteerID);
            checkPs.setInt(3, eventID);

            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("❌ Đã apply trùng sự kiện hoặc trùng ngày!");
                    return false;
                }
            }

            String insertSql = "INSERT INTO VolunteerApplications (VolunteerID, EventID, Status, ApplicationDate) VALUES (?, ?, 'Pending', ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, volunteerID);
                ps.setInt(2, eventID);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Lấy danh sách đã apply
    public List<VolunteerApplications> getApplicationsByUserId(int userID) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = """
            SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
            FROM VolunteerApplications va
            JOIN Event e ON va.EventID = e.EventID
            JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
            WHERE v.UserID = ?
            ORDER BY va.ApplicationDate DESC
        """;

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VolunteerApplications app = new VolunteerApplications();
                    app.setApplicationID(rs.getInt("ApplicationID"));
                    app.setVolunteerID(rs.getInt("VolunteerID"));
                    app.setEventID(rs.getInt("EventID"));
                    app.setStatus(rs.getString("Status"));

                    Event e = new Event();
                    e.setEventID(rs.getInt("EventID"));
                    e.setEventName(rs.getString("EventName"));
                    e.setLocation(rs.getString("Location"));
                    e.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
                    e.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
                    app.setEvent(e);
                    list.add(app);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Hủy đơn (chỉ khi đang pending)
    public boolean cancelApplication(int applicationID) {
        String sql = "DELETE FROM VolunteerApplications WHERE ApplicationID = ? AND Status = 'Pending'";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, applicationID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Lấy danh sách sự kiện tình nguyện hôm nay cho volunteer
public List<VolunteerApplications> getTodayEventsByUserId(int userID) {
    List<VolunteerApplications> list = new ArrayList<>();
    String sql = """
        SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
        FROM VolunteerApplications va
        JOIN Event e ON va.EventID = e.EventID
        JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
        WHERE v.UserID = ? 
          AND va.Status = 'Approved'
          AND CAST(GETDATE() AS DATE) BETWEEN CAST(e.StartDate AS DATE) AND CAST(e.EndDate AS DATE)
        ORDER BY e.StartDate
    """;

    try (Connection conn = DBUtils.getConnection1();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userID);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                VolunteerApplications app = new VolunteerApplications();
                app.setApplicationID(rs.getInt("ApplicationID"));
                app.setVolunteerID(rs.getInt("VolunteerID"));
                app.setEventID(rs.getInt("EventID"));
                app.setStatus(rs.getString("Status"));

                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                e.setLocation(rs.getString("Location"));
                e.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
                e.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
                app.setEvent(e);

                list.add(app);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    // ✅ Đánh dấu sự kiện đã hoàn thành
public boolean markAsCompleted(int applicationId) {
    String sql = "UPDATE VolunteerApplications SET Status = 'Completed' WHERE ApplicationID = ?";
    try (Connection conn = DBUtils.getConnection1();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, applicationId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


    // Lấy danh sách các event đã hoàn thành (Completed)
public List<VolunteerApplications> getCompletedApplicationsByUserId(int userID) {
    List<VolunteerApplications> list = new ArrayList<>();
    String sql = """
        SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
        FROM VolunteerApplications va
        JOIN Event e ON va.EventID = e.EventID
        JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
        WHERE v.UserID = ? AND va.Status = 'Completed'
        ORDER BY va.ApplicationDate DESC
    """;

    try (Connection conn = DBUtils.getConnection1();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userID);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                VolunteerApplications app = new VolunteerApplications();
                app.setApplicationID(rs.getInt("ApplicationID"));
                app.setVolunteerID(rs.getInt("VolunteerID"));
                app.setEventID(rs.getInt("EventID"));
                app.setStatus(rs.getString("Status"));
                Timestamp t = rs.getTimestamp("ApplicationDate");
                if (t != null) app.setApplicationDate(t.toLocalDateTime());

                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                e.setLocation(rs.getString("Location"));
                Timestamp start = rs.getTimestamp("StartDate");
                Timestamp end = rs.getTimestamp("EndDate");
                if (start != null) e.setStartDate(start.toLocalDateTime());
                if (end != null) e.setEndDate(end.toLocalDateTime());
                app.setEvent(e);

                list.add(app);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
}
