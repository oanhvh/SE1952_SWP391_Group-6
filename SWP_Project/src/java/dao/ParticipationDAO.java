package dao;

import entity.Participation;
import java.sql.*;
import java.util.*;

public class ParticipationDAO {

    private Participation mapRow(ResultSet rs) throws SQLException {
        Participation p = new Participation();
        p.setApplicationID(rs.getInt("ApplicationID"));
        p.setFullName(rs.getString("VolunteerName"));
        p.setEventName(rs.getString("EventName"));
        p.setStatus(rs.getString("Status"));
        p.setApplicationDate(rs.getTimestamp("ApplicationDate"));
        p.setApprovedByStaffName(rs.getString("ApprovedByStaffName"));
        return p;
    }

    // ========== LẤY DANH SÁCH TẤT CẢ CÓ PHÂN TRANG ==========
    public List<Participation> getAllParticipations(int pageIndex, int pageSize) {
        List<Participation> list = new ArrayList<>();
        String sql = """
            SELECT * FROM (
                SELECT ROW_NUMBER() OVER (ORDER BY va.ApplicationDate DESC) AS RowNum,
                       va.ApplicationID, u.FullName AS VolunteerName, e.EventName,
                       va.Status, va.ApplicationDate, sUser.FullName AS ApprovedByStaffName
                FROM VolunteerApplications va
                JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
                JOIN Users u ON v.UserID = u.UserID
                JOIN Event e ON va.EventID = e.EventID
                LEFT JOIN Staff s ON va.ApprovedByStaffID = s.StaffID
                LEFT JOIN Users sUser ON s.UserID = sUser.UserID
            ) AS Temp
            WHERE RowNum BETWEEN (? - 1) * ? + 1 AND ? * ?
        """;

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageIndex);
            ps.setInt(2, pageSize);
            ps.setInt(3, pageIndex);
            ps.setInt(4, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int countAllParticipations() {
        String sql = "SELECT COUNT(*) FROM VolunteerApplications";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ========== DROPDOWN DATA ==========
    public List<String> getAllEventNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT EventName FROM Event ORDER BY EventName";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("EventName"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getAllStatuses() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT Status FROM VolunteerApplications ORDER BY Status";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("Status"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getAllApproverNames() {
        List<String> list = new ArrayList<>();
        String sql = """
            SELECT DISTINCT u.FullName FROM Users u
            JOIN Staff s ON u.UserID = s.UserID
            WHERE s.StaffID IN (SELECT DISTINCT ApprovedByStaffID FROM VolunteerApplications WHERE ApprovedByStaffID IS NOT NULL)
            ORDER BY u.FullName
        """;
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("FullName"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ========== SEARCH HÀM RIÊNG ==========
    public List<Participation> searchByFilters(String volunteerName, String eventName, String status,
                                               String approverName, String fromDate, String toDate) {
        List<Participation> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT va.ApplicationID, u.FullName AS VolunteerName, e.EventName, va.Status, va.ApplicationDate,
                   sUser.FullName AS ApprovedByStaffName
            FROM VolunteerApplications va
            JOIN Volunteer v ON va.VolunteerID = v.VolunteerID
            JOIN Users u ON v.UserID = u.UserID
            JOIN Event e ON va.EventID = e.EventID
            LEFT JOIN Staff s ON va.ApprovedByStaffID = s.StaffID
            LEFT JOIN Users sUser ON s.UserID = sUser.UserID
            WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (volunteerName != null && !volunteerName.isEmpty()) {
            sql.append(" AND u.FullName LIKE ?");
            params.add("%" + volunteerName + "%");
        }
        if (eventName != null && !eventName.isEmpty()) {
            sql.append(" AND e.EventName = ?");
            params.add(eventName);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND va.Status = ?");
            params.add(status);
        }
        if (approverName != null && !approverName.isEmpty()) {
            sql.append(" AND sUser.FullName = ?");
            params.add(approverName);
        }
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            sql.append(" AND va.ApplicationDate BETWEEN ? AND ?");
            params.add(fromDate);
            params.add(toDate);
        }

        sql.append(" ORDER BY va.ApplicationDate DESC");

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
}
