package controller;

import entity.Users;
import entity.VolunteerApplications;
import service.VolunteerApplicationsService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/VolunteerTodayController")
public class VolunteerTodayController extends HttpServlet {

    private VolunteerApplicationsService service;

    @Override
    public void init() throws ServletException {
        service = new VolunteerApplicationsService();
    }

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
        try {
            List<VolunteerApplications> todayEvents = service.getTodayEventsByUserId(userId);
            request.setAttribute("todayEvents", todayEvents);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot fetch today's events.");
        }

        request.getRequestDispatcher("/volunteer/volunteer_today.jsp").forward(request, response);
    }
}
