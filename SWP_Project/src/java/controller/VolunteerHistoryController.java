package controller;

import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.VolunteerApplicationsService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/VolunteerHistory")
public class VolunteerHistoryController extends HttpServlet {

    private VolunteerApplicationsService service;

    @Override
    public void init() {
        service = new VolunteerApplicationsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users authUser = (session != null) ? (Users) session.getAttribute("authUser") : null;

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = authUser.getUserID();

        // Lấy tham số filter từ request
        String eventName = request.getParameter("eventName");
        String location = request.getParameter("location");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty()) ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty()) ? LocalDate.parse(endDateStr) : null;

        try {
            List<VolunteerApplications> historyList = service.getCompletedApplicationsByUserId(userId);
                historyList = service.filterByEventName(historyList, eventName);
                historyList = service.filterByLocation(historyList, location);
                historyList = service.filterByDate(historyList, startDate, endDate);
            // Truyền danh sách ra JSP
            request.setAttribute("historyList", historyList);
            request.getRequestDispatcher("/volunteer/volunteer_history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load volunteer history.");
            request.getRequestDispatcher("/volunteer/volunteer_history.jsp").forward(request, response);
        }
    }
}
