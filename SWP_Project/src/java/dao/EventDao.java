/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author admin
 */


import entity.Event;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    public List<Event> getAllEvents() throws Exception {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Event ORDER BY StartDate DESC";

        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setManagerID(rs.getInt("ManagerID"));
                e.setCreatedByStaffID((Integer) rs.getObject("CreatedByStaffID"));
                e.setEventName(rs.getString("EventName"));
                e.setDescription(rs.getString("Description"));
                e.setLocation(rs.getString("Location"));
                e.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
                e.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
                e.setStatus(rs.getString("Status"));
                e.setCapacity(rs.getInt("Capacity"));
                e.setImage(rs.getString("Image"));
                e.setCategoryID(rs.getInt("CategoryID"));
                e.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                list.add(e);
            }
        }
        return list;
    }
    

    
} 

