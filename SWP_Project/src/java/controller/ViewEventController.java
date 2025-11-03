/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.EventDao;
import entity.Event;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewEventController", urlPatterns = {"/volunteer/events"})
public class ViewEventController extends HttpServlet {

    private final EventDao eventDao = new EventDao();

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
            // Lấy tất cả sự kiện có thể apply (hoặc tuỳ điều kiện)
            List<Event> eventList = eventDao.getAllEvents();
            request.setAttribute("eventList", eventList);

            // Forward sang trang JSP
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể tải danh sách sự kiện. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
        }
    }
}
