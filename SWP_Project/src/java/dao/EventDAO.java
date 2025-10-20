/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duc
 */
public class EventDAO extends DBUtils {

    private Event extractEvent(ResultSet rs) throws Exception {
        Event event = new Event();
        event.setOrg(rs.getInt("EventID"));
        event.setEventID(rs.getInt("OrgID"));
        event.setEventName(rs.getString("EventName"));
        event.setDescription(rs.getString("Description"));
        event.setLocation(rs.getString("Location"));
        event.setStartDate(rs.getDate("StartDate"));
        event.setEndDate(rs.getDate("EndDate"));
        event.setStatus(rs.getString("Status"));
        event.setCapacity(rs.getInt("Capacity"));
        event.setKeywords(rs.getString("Keywords"));

        return event;
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM Event";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Event event = extractEvent(rs);
                eventList.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }

    public Event getEventByID(int eventID) {
        String sql = "SELECT * FROM Event WHERE EventID = ?";
        Event event = null;
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    event = extractEvent(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public void addEvent(Event event) {
        String sql = "INSERT INTO Event (OrgID, EventName, Description, Location, StartDate, EndDate, Status, Capacity, Keywords) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getOrgID());
            pstmt.setString(2, event.getEventName());
            pstmt.setString(3, event.getDescription());
            pstmt.setString(4, event.getLocation());
            pstmt.setDate(5, (Date) event.getStartDate());
            pstmt.setDate(6, (Date) event.getEndDate());
            pstmt.setString(7, event.getStatus());
            pstmt.setInt(8, event.getCapacity());
            pstmt.setString(9, event.getKeywords());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEvent(Event event) {
        String sql = "UPDATE Event SET OrgID = ?, EventName = ?, Description = ?, Location = ?, "
                + "StartDate = ?, EndDate = ?, Status = ?, Capacity = ?, Keywords = ? "
                + "WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getOrgID());
            pstmt.setString(2, event.getEventName());
            pstmt.setString(3, event.getDescription());
            pstmt.setString(4, event.getLocation());
            pstmt.setDate(5, (Date) event.getStartDate());
            pstmt.setDate(6, (Date) event.getEndDate());
            pstmt.setString(7, event.getStatus());
            pstmt.setInt(8, event.getCapacity());
            pstmt.setString(9, event.getKeywords());
            pstmt.setInt(10, event.getEventID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventID) {
        String sql = "DELETE FROM Event WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEventStatus(int eventID, String status) {
        String sql = "UPDATE Event SET Status = ? WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, eventID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
