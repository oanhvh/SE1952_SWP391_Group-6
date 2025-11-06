package controller;

import dao.VolunteerApplicationsDAO;
import entity.VolunteerApplications;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffApplicationsListController", urlPatterns = {"/staff/applications"})
public class StaffApplicationsListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object roleObj = (session != null) ? session.getAttribute("role") : null;
        String role = roleObj != null ? roleObj.toString() : null;
        if (role == null || !("Staff".equalsIgnoreCase(role) || "Manager".equalsIgnoreCase(role))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String status = request.getParameter("status");
        if (status == null || status.isBlank()) status = "Pending";

        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        List<VolunteerApplications> apps = dao.getVolunteerApplicationsByStatus(status);
        request.setAttribute("applications", apps);
        request.setAttribute("filterStatus", status);
        request.getRequestDispatcher("/staff/applications_list.jsp").forward(request, response);
    }
}
