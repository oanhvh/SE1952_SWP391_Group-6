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

        int userID = authUser.getUserID();
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        List<VolunteerApplications> list = dao.getApplicationsByUserId(userID);

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

        int userID = authUser.getUserID();
        int eventID = Integer.parseInt(request.getParameter("eventId"));

        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        boolean success = dao.applyEventByUserId(userID, eventID);

        if (success) {
            response.sendRedirect("ApplyEventController");
        } else {
            request.setAttribute("error", "You have already applied for this event or have a schedule conflict!");
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
        }
    }
}
