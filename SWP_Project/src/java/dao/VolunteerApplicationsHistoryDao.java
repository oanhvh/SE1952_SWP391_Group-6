/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author admin
 */


import entity.VolunteerApplications;
import entity.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolunteerApplicationsHistoryDao {

    public List<VolunteerApplications> getApplicationsByVolunteerId(int volunteerId) {
        List<VolunteerApplications> list = new ArrayList<>();

        String sql = """
            SELECT va.ApplicationID, va.VolunteerID, va.EventID, va.Status,
                   va.ApplicationDate, va.ApprovalDate, va.ApprovedByStaffID,
                   e.EventName, e.Location, e.StartDate, e.EndDate, e.Image
            FROM VolunteerApplications va
            JOIN Event e ON va.EventID = e.EventID
            WHERE va.VolunteerID = ?
            ORDER BY va.ApplicationDate DESC
        """;

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                VolunteerApplications app = new VolunteerApplications();
                app.setApplicationID(rs.getInt("ApplicationID"));
                app.setVolunteerID(rs.getInt("VolunteerID"));
                app.setEventID(rs.getInt("EventID"));
                app.setStatus(rs.getString("Status"));

                Timestamp appDate = rs.getTimestamp("ApplicationDate");
                Timestamp apprDate = rs.getTimestamp("ApprovalDate");
                app.setApplicationDate(appDate != null ? appDate.toLocalDateTime() : null);
                app.setApprovalDate(apprDate != null ? apprDate.toLocalDateTime() : null);
                app.setApprovedByStaffID((Integer) rs.getObject("ApprovedByStaffID"));

                Event event = new Event();
                event.setEventID(rs.getInt("EventID"));
                event.setEventName(rs.getString("EventName"));
                event.setLocation(rs.getString("Location"));
                Timestamp sDate = rs.getTimestamp("StartDate");
                Timestamp eDate = rs.getTimestamp("EndDate");
                event.setStartDate(sDate != null ? sDate.toLocalDateTime() : null);
                event.setEndDate(eDate != null ? eDate.toLocalDateTime() : null);
                event.setImage(rs.getString("Image"));

                app.setEvent(event);
                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

