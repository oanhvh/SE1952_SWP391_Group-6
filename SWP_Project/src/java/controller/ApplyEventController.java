package controller;

import dao.VolunteerApplicationsDAO;
import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ApplyEventController")
public class ApplyEventController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = authUser.getUserID();
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        List<VolunteerApplications> list = dao.getApplicationsByUserId(userId);

        request.setAttribute("appliedEvents", list);
        request.getRequestDispatcher("/volunteer/applied_events.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = authUser.getUserID();
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String motivation = request.getParameter("motivation");
        String experience = request.getParameter("experience");

        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();

        // ✅ Kiểm tra trùng sự kiện hoặc trùng ngày
        if (dao.hasAppliedForEvent(userId, eventId)) {
            request.setAttribute("error", "You've already applied this event!");
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
            return;
        }

        boolean success = dao.applyEventByUserId(userId, eventId, motivation, experience);

        if (success) {
            response.sendRedirect("ApplyEventController");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
