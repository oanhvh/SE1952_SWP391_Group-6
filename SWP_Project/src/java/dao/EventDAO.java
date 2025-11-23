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
import entity.ManagerEventView;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public List<Event> getAllEvents1() throws Exception {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Event ORDER BY StartDate DESC";

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
                try {
                    e.setDenyCount(rs.getInt("DenyCount"));
                } catch (SQLException ignore) {
                    // column may not exist yet
                }
                list.add(e);
            }
        }
        return list;
    }

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
        // Read DATETIME/DATETIME2 as Timestamp -> LocalDateTime (preserve time, avoid type mismatch)
        Timestamp tsStart = rs.getTimestamp("StartDate");
        event.setStartDate(tsStart != null ? tsStart.toLocalDateTime() : null);

        Timestamp tsEnd = rs.getTimestamp("EndDate");
        event.setEndDate(tsEnd != null ? tsEnd.toLocalDateTime() : null);
        event.setStatus(rs.getString("Status"));
        event.setCapacity(rs.getInt("Capacity"));
        event.setImage(rs.getString("Image"));
        event.setCategoryID(rs.getInt("CategoryID"));
        Timestamp tsCreated = rs.getTimestamp("CreatedAt");
        event.setCreatedAt(tsCreated != null ? tsCreated.toLocalDateTime() : null);
        try {
            event.setDenyCount(rs.getInt("DenyCount"));
        } catch (SQLException ignore) {
            // column may not exist yet
        }

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

    public boolean addEvent(Event event) {
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
            int rows = pstmt.executeUpdate();
            if (rows <= 0) {
                throw new SQLException("No rows inserted for Event");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert Event: " + e.getMessage(), e);
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

    // Tăng denyCount và chuyển trạng thái sang Cancelled
    public void incrementDenyCountAndCancel(int eventID) {
        String sql = "UPDATE Event SET DenyCount = DenyCount + 1, Status = 'Cancelled' WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventID) {
        String deleteNotificationsSql = "DELETE FROM Notifications WHERE EventID = ?";
        String deleteEventSql = "DELETE FROM Event WHERE EventID = ?";

        try (Connection conn = DBUtils.getConnection1()) {
            // Xóa tất cả notification liên quan đến event trước để tránh lỗi FK
            try (PreparedStatement psNoti = conn.prepareStatement(deleteNotificationsSql)) {
                psNoti.setInt(1, eventID);
                psNoti.executeUpdate();
            }

            // Sau đó mới xóa Event
            try (PreparedStatement psEvent = conn.prepareStatement(deleteEventSql)) {
                psEvent.setInt(1, eventID);
                psEvent.executeUpdate();
            }
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

    // Lấy sự kiện theo ManagerID và Status (dùng cho List Pending Event theo từng manager) (Thanhcocodo)
    public List<Event> getEventsByManagerAndStatus(int managerId, String status) {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM Event WHERE ManagerID = ? AND Status = ? ORDER BY StartDate DESC";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, managerId);
            pstmt.setString(2, status);
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

    public Event getEventById(int eventID) {
        String sql = "SELECT * FROM Event WHERE EventID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractEvent(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Truy vấn và lọc danh sách Event thuộc quyền quản lý của 1 manager cụ thể (Thanhcocodo)
    public List<ManagerEventView> getEventsByManager(int managerId, String nameKeyword, String status,
            LocalDate startFrom, LocalDate startTo) {
        List<ManagerEventView> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT e.EventID, e.EventName, e.Location, e.StartDate, e.EndDate, e.Status, "
                + "c.CategoryName, u.FullName AS CreatedByName "
                + "FROM Event e "
                + "LEFT JOIN Category c ON e.CategoryID = c.CategoryID "
                + "LEFT JOIN Staff s ON e.CreatedByStaffID = s.StaffID "
                + "LEFT JOIN Users u ON s.UserID = u.UserID "
                + "WHERE e.ManagerID = ?");

        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            sql.append(" AND e.EventName LIKE ?"); //Tìm kiếm theo tên sự kiện
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND e.Status = ?"); //Lọc theo trạng thái
        }
        if (startFrom != null) {
            sql.append(" AND CAST(e.StartDate AS DATE) >= ?"); //Sự kiện từ ngày này
        }
        if (startTo != null) {
            sql.append(" AND CAST(e.StartDate AS DATE) <= ?"); //Sự kiện đến ngày này
        }
        sql.append(" ORDER BY e.StartDate DESC"); //sắp xếp

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setInt(idx++, managerId);
            if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + nameKeyword.trim() + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(idx++, status.trim());
            }
            if (startFrom != null) {
                ps.setDate(idx++, java.sql.Date.valueOf(startFrom));
            }
            if (startTo != null) {
                ps.setDate(idx++, java.sql.Date.valueOf(startTo));
            }

            //Xử lý kết quả trả về
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ManagerEventView v = new ManagerEventView();
                    v.setEventID(rs.getInt("EventID"));
                    v.setEventName(rs.getString("EventName"));
                    v.setLocation(rs.getString("Location"));
                    Timestamp tsStart = rs.getTimestamp("StartDate");
                    v.setStartDate(tsStart != null ? tsStart.toLocalDateTime() : null);
                    Timestamp tsEnd = rs.getTimestamp("EndDate");
                    v.setEndDate(tsEnd != null ? tsEnd.toLocalDateTime() : null);
                    v.setStatus(rs.getString("Status"));
                    v.setCategoryName(rs.getString("CategoryName"));
                    v.setCreatedByName(rs.getString("CreatedByName"));
                    list.add(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
