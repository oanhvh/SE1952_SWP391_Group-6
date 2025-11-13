package controller;

import entity.Event;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EventService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewEventController", urlPatterns = {"/volunteer/events"})
public class ViewEventController extends HttpServlet {

    private EventService eventService;

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users authUser = (session != null) ? (Users) session.getAttribute("authUser") : null;

        if (authUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (role == null || !role.equalsIgnoreCase("Volunteer")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            // ✅ Lấy chỉ các sự kiện có trạng thái "Active"
            List<Event> eventList = eventService.getActiveEvents();

            request.setAttribute("eventList", eventList);
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load event list. Please try again later.");
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
        }
    }
}
