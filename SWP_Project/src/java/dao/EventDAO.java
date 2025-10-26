/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author DucNM
 */
public class EventDAO extends DBUtils {

    private Event extractEvent(ResultSet rs) throws Exception {
        Event event = new Event();
        event.setEventID(rs.getInt("EventID"));
        event.setManagerID(rs.getInt("ManagerID"));

        int staffId = rs.getInt("CreatedByStaffID");
        if (rs.wasNull()) {
            event.setCreatedByStaffID(null);
        } else {
            event.setCreatedByStaffID(staffId);
        }

        event.setEventName(rs.getString("EventName"));
        event.setDescription(rs.getString("Description"));
        event.setLocation(rs.getString("Location"));
        event.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
        event.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
        event.setStatus(rs.getString("Status"));
        event.setCapacity(rs.getInt("Capacity"));
        event.setImage(rs.getString("Image"));
        event.setCategoryID(rs.getInt("CategoryID"));
        event.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());

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

    public void addEvent(Event event) {
        String sql = "INSERT INTO Event (ManagerID, CreatedByStaffID, EventName, Description, Location, StartDate, EndDate, Status, Capacity, Image, CategoryID, CreatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getManagerID());
            if (event.getCreatedByStaffID() != null) {
                pstmt.setInt(2, event.getCreatedByStaffID());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setString(3, event.getEventName());
            pstmt.setString(4, event.getDescription());
            pstmt.setString(5, event.getLocation());
            pstmt.setTimestamp(6, Timestamp.valueOf(event.getStartDate()));
            pstmt.setTimestamp(7, Timestamp.valueOf(event.getEndDate()));
            pstmt.setString(8, event.getStatus());
            pstmt.setInt(9, event.getCapacity());
            pstmt.setString(10, event.getImage());
            pstmt.setInt(11, event.getCategoryID());
            pstmt.setTimestamp(12, Timestamp.valueOf(event.getCreatedAt()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEvent(Event event) {
        String sql = "UPDATE Event SET ManagerID = ?, CreatedByStaffID = ?, EventName = ?, Description = ?, Location = ?, "
                + "StartDate = ?, EndDate = ?, Status = ?, Capacity = ?, Image = ?, CategoryID = ? WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, event.getManagerID());
            if (event.getCreatedByStaffID() != null) {
                pstmt.setInt(2, event.getCreatedByStaffID());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setString(3, event.getEventName());
            pstmt.setString(4, event.getDescription());
            pstmt.setString(5, event.getLocation());
            pstmt.setTimestamp(6, Timestamp.valueOf(event.getStartDate()));
            pstmt.setTimestamp(7, Timestamp.valueOf(event.getEndDate()));
            pstmt.setString(8, event.getStatus());
            pstmt.setInt(9, event.getCapacity());
            pstmt.setString(10, event.getImage());
            pstmt.setInt(11, event.getCategoryID());
            pstmt.setInt(12, event.getEventID());
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

    public List<Event> getEventsByStatus(String status) {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM Event WHERE Status = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event event = extractEvent(rs);
                    eventList.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }

}
