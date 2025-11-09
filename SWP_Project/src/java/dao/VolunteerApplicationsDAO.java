/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ApplicationReviewRow;
import entity.Event;
import entity.VolunteerApplications;
import service.EmailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
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
        String sql = "SELECT * FROM VolunteerApplications";
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

    // Data for staff review list with volunteer full name and event name
    public List<ApplicationReviewRow> getApplicationsForReviewByStatus(String status) {
        List<ApplicationReviewRow> list = new ArrayList<>();
        String sql = "";
//                """
//                            SELECT va.ApplicationID,
//                                   u.FullName AS VolunteerFullName,
//                                   e.EventName,
//                                   va.Status,
//                                   va.ApplicationDate,
//                                   va.Motivation,
//                                   va.Experience,
//                                   (
//                                       SELECT STRING_AGG(s.SkillName, ', ')
//                                       FROM EventApplicationSkills eas
//                                       JOIN Skills s ON s.SkillID = eas.SkillID
//                                       WHERE eas.ApplicationID = va.ApplicationID
//                                   ) AS SkillsCsv
//                            FROM VolunteerApplications va
//                            JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
//                            JOIN Users u ON v.UserID = u.UserID
//                            JOIN Event e ON va.EventID = e.EventID
//                            WHERE va.Status = ?
//                            ORDER BY va.ApplicationDate DESC
//                        """;
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ApplicationReviewRow row = new ApplicationReviewRow();
                    row.setApplicationID(rs.getInt("ApplicationID"));
                    row.setVolunteerFullName(rs.getString("VolunteerFullName"));
                    row.setEventName(rs.getString("EventName"));
                    row.setStatus(rs.getString("Status"));
                    Timestamp t = rs.getTimestamp("ApplicationDate");
                    if (t != null) row.setApplicationDate(t.toLocalDateTime());
                    row.setMotivation(rs.getString("Motivation"));
                    row.setExperience(rs.getString("Experience"));
                    row.setSkills(rs.getString("SkillsCsv"));
                    list.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addVolunteerApplication(VolunteerApplications app) {
        String sql = "INSERT INTO VolunteerApplications (VolunteerID, EventID, Status, ApplicationDate, ApprovalDate, ApprovedByStaffID) "
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
        String sql = "UPDATE VolunteerApplications SET VolunteerID = ?, EventID = ?, Status = ?, "
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
        String sql = "DELETE FROM VolunteerApplications WHERE ApplicationID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, applicationID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VolunteerApplications> getVolunteerApplicationsByStatus(String status) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "SELECT * FROM VolunteerApplications WHERE Status = ?";
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
        String sql = "SELECT * FROM VolunteerApplications WHERE VolunteerID = ?";
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
        String sql = "UPDATE VolunteerApplications SET Status = ?, ApprovalDate = ?, ApprovedByStaffID = ? WHERE ApplicationID = ?";
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

    // Review application: approve/reject, set staff comment, create notification for volunteer
    public boolean reviewApplication(int applicationId, int staffUserId, boolean approve, String staffComment) {
        String sqlSelect = "SELECT va.VolunteerID, va.EventID, e.EventName, e.Location, u.Email "
                + "FROM VolunteerApplications va "
                + "JOIN Event e ON va.EventID = e.EventID "
                + "JOIN Volunteer v ON va.VolunteerID = v.VolunteerID "
                + "JOIN Users u ON v.UserID = u.UserID "
                + "WHERE va.ApplicationID = ?";
        String sqlUpdate = "UPDATE VolunteerApplications SET Status = ?, ApprovalDate = ?, ApprovedByStaffID = ?, StaffComment = ? WHERE ApplicationID = ?";
        String sqlNoti = "INSERT INTO Notifications (Title, Message, ReceiverID, IsRead, CreatedAt, Type, EventID) VALUES (?, ?, ?, 0, SYSUTCDATETIME(), 'Application', ?)";

        try (Connection con = DBUtils.getConnection1();
             PreparedStatement psSel = con.prepareStatement(sqlSelect);
             PreparedStatement psUpd = con.prepareStatement(sqlUpdate);
             PreparedStatement psNoti = con.prepareStatement(sqlNoti)) {

            con.setAutoCommit(false);

            psSel.setInt(1, applicationId);
            ResultSet rs = psSel.executeQuery();
            if (!rs.next()) {
                con.rollback();
                return false;
            }
            int volunteerId = rs.getInt("VolunteerID");
            int eventId = rs.getInt("EventID");
            String eventName = rs.getString("EventName");
            String location = rs.getString("Location");
            String volunteerEmail = rs.getString("Email");

            String newStatus = approve ? "Approved" : "Rejected";
            psUpd.setString(1, newStatus);
            psUpd.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psUpd.setInt(3, staffUserId);
            psUpd.setString(4, staffComment);
            psUpd.setInt(5, applicationId);
            int updated = psUpd.executeUpdate();
            if (updated <= 0) {
                con.rollback();
                return false;
            }

            String title = (approve ? "Đơn ứng tuyển được duyệt" : "Đơn ứng tuyển bị từ chối") +
                    (eventName != null && !eventName.isEmpty() ? (" - " + eventName) : "");
            String message = approve
                    ? String.format("Đơn của bạn cho sự kiện '%s'%s đã được duyệt.",
                    eventName != null ? eventName : "",
                    (location != null && !location.isEmpty() ? " tại " + location : ""))
                    : String.format("Đơn của bạn cho sự kiện '%s'%s đã bị từ chối.%s",
                    eventName != null ? eventName : "",
                    (location != null && !location.isEmpty() ? " tại " + location : ""),
                    (staffComment != null && !staffComment.isEmpty() ? " Lý do: " + staffComment : ""));
            psNoti.setString(1, title);
            psNoti.setString(2, message);
            psNoti.setInt(3, volunteerId);
            psNoti.setInt(4, eventId);
            psNoti.executeUpdate();

            con.commit();

            // Attempt to send email notification (non-blocking to DB result)
            try {
                if (volunteerEmail != null && !volunteerEmail.isEmpty()) {
                    String subject = title;
                    String body = message;
                    EmailService.sendEmail(volunteerEmail, subject, body);
                }
            } catch (Exception mailEx) {
                // Do not rollback DB; just log the failure
                mailEx.printStackTrace();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    // ✅ Apply event có thêm lý do và kinh nghiệm
    public boolean applyEventByUserId(int userID, int eventID, String motivation, String experience) {
        int volunteerID = getVolunteerIdByUserId(userID);
        if (volunteerID == -1) return false;

        String sql = "";
//                    """
//                    INSERT INTO VolunteerApplications
//                    (VolunteerID, EventID, Status, ApplicationDate, Motivation, Experience)
//                    VALUES (?, ?, 'Pending', SYSUTCDATETIME(), ?, ?)
//                """;

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerID);
            ps.setInt(2, eventID);
            ps.setString(3, motivation);
            ps.setString(4, experience);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ✅ Lấy danh sách đã apply
    public List<VolunteerApplications> getApplicationsByUserId(int userID) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "";
//                    """
//                    SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
//                    FROM VolunteerApplications va
//                    JOIN Event e ON va.EventID = e.EventID
//                    JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
//                    WHERE v.UserID = ?
//                    ORDER BY va.ApplicationDate DESC
//                """;

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
    public String cancelApplication(int applicationId, String cancelReason) {
        String sqlSelect = "SELECT Status, StaffComment FROM VolunteerApplications WHERE ApplicationID = ?";
        String sqlDeleteSkills = "DELETE FROM EventApplicationSkills WHERE ApplicationID = ?";
        String sqlDeleteApp = "DELETE FROM VolunteerApplications WHERE ApplicationID = ? AND Status = 'Pending'";
        String sqlUpdateCancel = "";
//                    """
//                    UPDATE VolunteerApplications
//                    SET Status = 'Cancelled',
//                        Motivation = CONCAT(ISNULL(Motivation, ''), CHAR(13) + CHAR(10), '--- Cancelled Reason: ', ?)
//                    WHERE ApplicationID = ? AND Status = 'Approved'
//                """;

        try (Connection con = DBUtils.getConnection1();
             PreparedStatement psSelect = con.prepareStatement(sqlSelect);
             PreparedStatement psDelSkill = con.prepareStatement(sqlDeleteSkills);
             PreparedStatement psDelApp = con.prepareStatement(sqlDeleteApp);
             PreparedStatement psUpdate = con.prepareStatement(sqlUpdateCancel)) {

            con.setAutoCommit(false);

            psSelect.setInt(1, applicationId);
            ResultSet rs = psSelect.executeQuery();
            if (!rs.next()) {
                con.rollback();
                return "NOT_FOUND";
            }

            String status = rs.getString("Status");
            String staffComment = rs.getString("StaffComment");

            if ("Pending".equalsIgnoreCase(status)) {
                psDelSkill.setInt(1, applicationId);
                psDelSkill.executeUpdate();

                psDelApp.setInt(1, applicationId);
                int rows = psDelApp.executeUpdate();

                if (rows > 0) {
                    con.commit();
                    return "PENDING_CANCELLED";
                } else {
                    con.rollback();
                    return "ERROR";
                }

            } else if ("Approved".equalsIgnoreCase(status)) {
                psUpdate.setString(1, cancelReason != null ? cancelReason : "Không có lý do");
                psUpdate.setInt(2, applicationId);

                int rows = psUpdate.executeUpdate();
                if (rows > 0) {
                    con.commit();
                    return "APPROVED_CANCELLED";
                } else {
                    con.rollback();
                    return "ERROR";
                }

            } else if ("Rejected".equalsIgnoreCase(status)) {
                System.out.println("Application was rejected. Staff comment: " + staffComment);
                con.rollback();
                return "REJECTED";
            }

            con.rollback();
            return "ERROR";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }


    // ✅ Lấy danh sách sự kiện tình nguyện hôm nay cho volunteer
    public List<VolunteerApplications> getTodayEventsByUserId(int userID) {
        List<VolunteerApplications> list = new ArrayList<>();
        String sql = "";
//                    """
//                    SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
//                    FROM VolunteerApplications va
//                    JOIN Event e ON va.EventID = e.EventID
//                    JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
//                    WHERE v.UserID = ?
//                      AND va.Status = 'Approved'
//                      AND CAST(GETDATE() AS DATE) BETWEEN CAST(e.StartDate AS DATE) AND CAST(e.EndDate AS DATE)
//                    ORDER BY e.StartDate
//                """;

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
        String sql ="";
//                """
//                    SELECT va.*, e.EventName, e.Location, e.StartDate, e.EndDate
//                    FROM VolunteerApplications va
//                    JOIN Event e ON va.EventID = e.EventID
//                    JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
//                    WHERE v.UserID = ? AND va.Status = 'Completed'
//                    ORDER BY va.ApplicationDate DESC
//                """;

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

    public boolean hasAppliedForEvent(int userID, int eventID) {
        int volunteerID = getVolunteerIdByUserId(userID);
        String sql = "SELECT COUNT(*) FROM VolunteerApplications WHERE VolunteerID = ? AND EventID = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerID);
            ps.setInt(2, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

