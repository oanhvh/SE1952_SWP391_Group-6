/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.NotificationsDAO;
import dao.StaffDAO;
import entity.Category;
import entity.Event;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import service.EventService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "EventManagerController", urlPatterns = {"/manager/event"})
public class EventManagerController extends HttpServlet {

    private EventService eventService;
    private StaffDAO staffDAO;
    private CategoryDAO categoryDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        staffDAO = new StaffDAO();
        categoryDAO = new CategoryDAO();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "detail":
                showDetail(request, response);
                break;
            default:
                listEvents(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "approve":
                approveEvent(request, response);
                break;
            case "deny":
                denyEvent(request, response);
                break;
            default:
                response.sendRedirect("/manager/event?action=list");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        List<Event> eventList = new ArrayList<>(eventService.getAllEvents());
        List<Event> eventList = eventService.getEventsByStatus("Pending");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/manager/listEvent.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
        if (event != null) {
            Category category = categoryDAO.getCategoryById(event.getCategoryID());
            String categoryName = (category != null) ? category.getCategoryName() : "Unknown";

            String staffName = staffDAO.getUserNameByStaffId(event.getCreatedByStaffID());

            request.setAttribute("event", event);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("staffName", staffName);
        }

        request.getRequestDispatcher("/manager/viewEvent.jsp").forward(request, response);
    }

    private void approveEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        eventService.updateEventStatus(id, "Active"); // null = không cần reason
        response.sendRedirect(request.getContextPath() + "/manager/event?action=list");
    }

    private void denyEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        String reason = request.getParameter("reason"); // Lý do từ form

        eventService.updateEventStatus(eventId, "Inactive");

        Event event = eventService.getEventById(eventId);
        if (event != null && event.getCreatedByStaffID() != null) {
            int staffId = event.getCreatedByStaffID();

            int managerId = (Integer) request.getSession().getAttribute("managerId");

            // Send noti to staff
            NotificationsDAO notificationsDAO = new NotificationsDAO();
            String type = "EventDenied";
            String title = "Event Denied";
            String message = reason != null && !reason.isBlank() ? reason : "Your event has been denied.";
            notificationsDAO.addNotificationForStaff(staffId, managerId, type, title, message, eventId);
        }

        response.sendRedirect(request.getContextPath() + "/manager/event?action=list");
    }
}
