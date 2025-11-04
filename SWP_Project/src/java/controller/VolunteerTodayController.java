package controller;

import dao.VolunteerApplicationsDAO;
import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/VolunteerTodayController")
public class VolunteerTodayController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");
        if (authUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = authUser.getUserID();
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        List<VolunteerApplications> todayEvents = dao.getTodayEventsByUserId(userId);

        request.setAttribute("todayEvents", todayEvents);
        request.getRequestDispatcher("/volunteer/volunteer_today.jsp").forward(request, response);
    }
}

