package controller;

import dao.NotificationsDAO;
import entity.Notifications;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NotificationsController", urlPatterns = {"/notifications", "/notifications/mark", "/notifications/mark-all", "/notifications/api", "/notifications/unread"})
public class NotificationsController extends HttpServlet {

    private final NotificationsDAO notificationsDAO = new NotificationsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Users user = (Users) session.getAttribute("authUser");
        String role = (String) session.getAttribute("role");

        // Xác định ID người nhận thông báo (VolunteerID hoặc StaffID) dựa trên role
        Integer receiverId = null;
        if (user != null && role != null) {
            if ("Volunteer".equalsIgnoreCase(role)) {
                receiverId = notificationsDAO.getVolunteerIdByUserId(user.getUserID());
            } else if ("Staff".equalsIgnoreCase(role)) {
                receiverId = notificationsDAO.getStaffIdByUserId(user.getUserID());
            }
        }

        if (receiverId == null) {
            // Không tìm thấy ID phù hợp -> quay về trang index theo role nếu có
            String ctx = req.getContextPath();
            if ("Staff".equalsIgnoreCase(role)) {
                resp.sendRedirect(ctx + "/staff/index_1.jsp");
            } else {
                resp.sendRedirect(ctx + "/volunteer/index_1.jsp");
            }
            return;
        }

        String servletPath = req.getServletPath();
        resp.setCharacterEncoding("UTF-8");

        //xem danh sách thông báo (50 thông báo mới nhất)
        if ("/notifications".equals(servletPath)) {
            List<Notifications> items;
            int unread;

            if ("Staff".equalsIgnoreCase(role)) {
                items = notificationsDAO.listForStaff(receiverId, 50);
                unread = notificationsDAO.getUnreadCountForStaff(receiverId);
            } else {
                items = notificationsDAO.listForVolunteer(receiverId, 50);
                unread = notificationsDAO.getUnreadCountForVolunteer(receiverId);
            }
            req.setAttribute("notifications", items); //danh sách thông báo
            req.setAttribute("unread", unread);       //số chưa đọc

            String jsp;
            if ("Staff".equalsIgnoreCase(role)) {
                jsp = "/staff/notifications.jsp";
            } else {
                jsp = "/volunteer/notifications.jsp";
            }

            req.getRequestDispatcher(jsp).forward(req, resp);
            return;
        }

        //đánh dấu 1 thông báo đã đọc
        if ("/notifications/mark".equals(servletPath)) {
            String idStr = req.getParameter("id");
            try {
                int id = Integer.parseInt(idStr);
                notificationsDAO.markAsRead(id, receiverId);
            } catch (Exception ignored) {
                //im lặng nếu lỗi không crash
            }
            resp.sendRedirect(req.getContextPath() + "/notifications");
            return;
        }

        // đánh dấu đã đọc all
        if ("/notifications/mark-all".equals(servletPath)) {
            notificationsDAO.markAllAsRead(receiverId);
            resp.sendRedirect(req.getContextPath() + "/notifications");
            return;
        }

        // API: trả JSON cho dropdown bell (items + unread)
        if ("/notifications/api".equals(servletPath)) {
            List<Notifications> items;
            int unread;

            if ("Staff".equalsIgnoreCase(role)) {
                items = notificationsDAO.listForStaff(receiverId, 10);
                unread = notificationsDAO.getUnreadCountForStaff(receiverId);
            } else {
                items = notificationsDAO.listForVolunteer(receiverId, 10);
                unread = notificationsDAO.getUnreadCountForVolunteer(receiverId);
            }
            resp.setContentType("application/json");
            StringBuilder sb = new StringBuilder();
            sb.append('{').append("\"unread\":").append(unread).append(",\"items\":[");
            for (int i = 0; i < items.size(); i++) {
                Notifications n = items.get(i);
                if (i > 0) sb.append(',');
                sb.append('{')
                  .append("\"id\":").append(n.getNotificationId()).append(',')
                  .append("\"title\":\"").append(escape(n.getTitle())).append("\",")
                  .append("\"message\":\"").append(escape(n.getMessage())).append("\",")
                  .append("\"createdAt\":\"").append(n.getCreatedAt() != null ? escape(n.getCreatedAt()) : "").append("\",")
                  .append("\"read\":").append(n.isRead()).append(',')
                  .append("\"type\":\"").append(n.getType() != null ? escape(n.getType()) : "").append("\",")
                  .append("\"eventId\":").append(n.getEventId() == null ? "null" : n.getEventId())
                  .append('}');
            }
            sb.append("]}");
            resp.getWriter().write(sb.toString());
            return;
        }

        // API: chỉ trả số chưa đọc
        if ("/notifications/unread".equals(servletPath)) {
            int unread;
            if ("Staff".equalsIgnoreCase(role)) {
                unread = notificationsDAO.getUnreadCountForStaff(receiverId);
            } else {
                unread = notificationsDAO.getUnreadCountForVolunteer(receiverId);
            }
            resp.setContentType("application/json");
            resp.getWriter().write("{\"unread\":" + unread + "}");
            return;
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
