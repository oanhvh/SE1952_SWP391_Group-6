package controller;

import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.VolunteerApplicationsService;
import java.io.IOException;
import java.util.List;

@WebServlet("/ApplyEventController")
public class ApplyEventController extends HttpServlet {

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
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = authUser.getUserID();
        List<VolunteerApplications> list = service.getApplicationsByUserId(userId);

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

        if (service.hasAppliedForEvent(userId, eventId)) {
            request.setAttribute("error", "You've already applied this event!");
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
            return;
        }

        boolean success = service.applyForEvent(userId, eventId, motivation, experience);

        if (success) {
            response.sendRedirect("ApplyEventController");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
