package controller;

import dao.NotificationsDAO;
import dao.NotificationsDAO.NotificationItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NotificationsController", urlPatterns = {"/notifications", "/notifications/mark", "/notifications/mark-all"})
public class NotificationsController extends HttpServlet {

    private final NotificationsDAO notificationsDAO = new NotificationsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("authUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        Users user = (Users) session.getAttribute("authUser");
        String role = (String) session.getAttribute("role");
        // Hiện tại hỗ trợ volunteer bell UI
        Integer volunteerId = notificationsDAO.getVolunteerIdByUserId(user.getUserID());
        if (volunteerId == null) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }

        String servletPath = req.getServletPath();
        if ("/notifications".equals(servletPath)) {
            List<NotificationItem> items = notificationsDAO.listForVolunteer(volunteerId, 50);
            int unread = notificationsDAO.getUnreadCountForVolunteer(volunteerId);
            req.setAttribute("notifications", items);
            req.setAttribute("unread", unread);
            req.getRequestDispatcher("/volunteer/notifications.jsp").forward(req, resp);
            return;
        }

        // mark single as read via ?id=...
        if ("/notifications/mark".equals(servletPath)) {
            String idStr = req.getParameter("id");
            try {
                int id = Integer.parseInt(idStr);
                notificationsDAO.markAsRead(id, volunteerId);
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/notifications");
            return;
        }

        // mark all
        if ("/notifications/mark-all".equals(servletPath)) {
            notificationsDAO.markAllAsRead(volunteerId);
            resp.sendRedirect(req.getContextPath() + "/notifications");
        }
    }
}
