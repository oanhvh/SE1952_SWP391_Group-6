package dao;

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
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    
    //đếm số thông báo chưa đọc
    public int getUnreadCountForVolunteer(int volunteerId) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE ReceiverID = ? AND IsRead = 0";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    public List<NotificationItem> listForVolunteer(int volunteerId, int limit) {
        String sql = "SELECT TOP (?) NotificationID, Title, Message, CreatedAt, IsRead, Type, EventID FROM Notifications WHERE ReceiverID = ? ORDER BY CreatedAt DESC";
        List<NotificationItem> list = new ArrayList<>();
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, volunteerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NotificationItem it = new NotificationItem();
                    it.notificationId = rs.getInt("NotificationID");
                    it.title = rs.getString("Title");
                    it.message = rs.getString("Message");
                    Timestamp t = rs.getTimestamp("CreatedAt");
                    it.createdAt = t != null ? t.toInstant().toString() : null;
                    it.read = rs.getBoolean("IsRead");
                    it.type = rs.getString("Type");
                    it.eventId = rs.getObject("EventID") == null ? null : rs.getInt("EventID");
                    list.add(it);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    //đánh dấu đã đọc
    public void markAsRead(int notificationId, int volunteerId) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE NotificationID = ? AND ReceiverID = ?";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            ps.setInt(2, volunteerId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    //đánh dấu tất cả đã đọc
    public void markAllAsRead(int volunteerId) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE ReceiverID = ? AND IsRead = 0";
        try (Connection cn = DBUtils.getConnection1(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static class NotificationItem {
        public int notificationId;
        public String title;
        public String message;
        public String createdAt;
        public boolean read;
        public String type;
        public Integer eventId;
    }
}
