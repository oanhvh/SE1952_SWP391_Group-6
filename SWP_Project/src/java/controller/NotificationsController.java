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

@WebServlet(name = "NotificationsController", urlPatterns = {"/notifications", "/notifications/mark", "/notifications/mark-all"})
public class NotificationsController extends HttpServlet {

    private final NotificationsDAO notificationsDAO = new NotificationsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Users user = (Users) session.getAttribute("authUser");
        String role = (String) session.getAttribute("role");
        //Chỉ hỗ trợ Volunteer ()
        Integer volunteerId = notificationsDAO.getVolunteerIdByUserId(user.getUserID());
        if (volunteerId == null) {
            resp.sendRedirect(req.getContextPath() + "/volunteer/index_1.jsp");
            return;
        }

        String servletPath = req.getServletPath();
        
        //xem danh sách thông báo (50 thông báo mới nhất 5+)
        if ("/notifications".equals(servletPath)) {
            List<Notifications> items = notificationsDAO.listForVolunteer(volunteerId, 50);
            int unread = notificationsDAO.getUnreadCountForVolunteer(volunteerId);
            req.setAttribute("notifications", items); //danh sách thông báo
            req.setAttribute("unread", unread);       //số chưa đọc
            req.getRequestDispatcher("/volunteer/notifications.jsp").forward(req, resp);
            return;
        }

        //đánh dấu 1 thông báo đã đọc
        if ("/notifications/mark".equals(servletPath)) {
            String idStr = req.getParameter("id");
            try {
                int id = Integer.parseInt(idStr);
                notificationsDAO.markAsRead(id, volunteerId);
            } catch (Exception ignored) {
                //im lặng nếu lỗi không crash
            }
            resp.sendRedirect(req.getContextPath() + "/notifications");
            return;
        }

        // đánh dấu đã đọc all
        if ("/notifications/mark-all".equals(servletPath)) {
            notificationsDAO.markAllAsRead(volunteerId);
            resp.sendRedirect(req.getContextPath() + "/notifications");
        }
    }
}
