package dao;

import entity.Notifications;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDAO extends DBUtils {

    //Lấy VolunteerID từ UserID
    public Integer getVolunteerIdByUserId(int userId) {
        String sql = "SELECT VolunteerID FROM Volunteer WHERE UserID = ?";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Lấy limit thông báo mới nhất gửi cho volunteer
    public List<Notifications> listForVolunteer(int volunteerId, int limit) {
        String sql = "SELECT TOP (?) NotificationID, Title, Message, CreatedAt, IsRead, Type, EventID FROM Notifications WHERE ReceiverID = ? ORDER BY CreatedAt DESC";
        List<Notifications> list = new ArrayList<>();
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, volunteerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notifications it = new Notifications();
                    it.setNotificationId(rs.getInt("NotificationID"));
                    it.setTitle(rs.getString("Title"));
                    it.setMessage(rs.getString("Message"));
                    Timestamp t = rs.getTimestamp("CreatedAt");
                    it.setCreatedAt(t != null ? t.toInstant().toString() : null);
                    it.setRead(rs.getBoolean("IsRead"));
                    it.setType(rs.getString("Type"));
                    it.setEventId(rs.getObject("EventID") == null ? null : rs.getInt("EventID"));
                    list.add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Đếm số thông báo chưa đọc
    public int getUnreadCountForVolunteer(int volunteerId) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE ReceiverID = ? AND IsRead = 0";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //đánh dấu đã đọc
    public void markAsRead(int notificationId, int volunteerId) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE NotificationID = ? AND ReceiverID = ?";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            ps.setInt(2, volunteerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //đánh dấu tất cả đã đọc
    public void markAllAsRead(int volunteerId) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE ReceiverID = ? AND IsRead = 0";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lấy StaffID từ UserID
    public Integer getStaffIdByUserId(int userId) {
        String sql = "SELECT StaffID FROM Staff WHERE UserID = ?";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm thông báo cho Staff (receiver là Staff, sender là Manager)
    public void addNotificationForStaff(int staffId, int managerId, String type, String title, String message, Integer eventId) {
        String sql = "INSERT INTO Notifications (ReceiverID, SenderID, Type, Title, Message, ReceiverRole, EventID) "
                + "VALUES (?, ?, ?, ?, ?, 'Staff', ?)";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, staffId);          // ReceiverID
            ps.setInt(2, managerId);        // SenderID
            ps.setString(3, type);          // Type
            ps.setString(4, title);         // Title
            ps.setString(5, message);       // Message
            if (eventId != null) {
                ps.setInt(6, eventId);      // EventID
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
