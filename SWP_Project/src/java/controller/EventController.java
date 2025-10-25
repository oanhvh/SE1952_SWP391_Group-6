/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Event;
import entity.Staff;
import entity.Organization;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.EventService;

/**
 *
 * @author Duc
 */
@WebServlet(name = "EventController", urlPatterns = {"/event"})
public class EventController extends HttpServlet {

    private EventService eventService;

    @Override
    public void init() {
        eventService = new EventService();
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

//        String action = request.getParameter("action");
//        if (action == null) {
//            action = "list";
//        }
//
//        switch (action) {
//            case "detail":
//                showDetail(request, response);
//                break;
//            case "edit":
//                showEditForm(request, response);
//                break;
//            case "list":
//            default:
//                listEvents(request, response);
//                break;
//        }
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

//        String action = request.getParameter("action");
//        if (action == null) {
//            action = "";
//        }
//
//        switch (action) {
//            case "add":
//                addEvent(request, response);
//                break;
//            case "update":
//                updateEvent(request, response);
//                break;
//            default:
//                response.sendRedirect("event?action=list");
//        }
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

//    private void listEvents(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        List<Event> events = eventService.getAllEvents();
//        request.setAttribute("events", events);
//        request.getRequestDispatcher("listEvents.jsp").forward(request, response);
//    }
//
//    private void showDetail(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Event event = eventService.getEventByID(id);
//        request.setAttribute("event", event);
//        request.getRequestDispatcher("viewEvent.jsp").forward(request, response);
//    }
//
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Event event = eventService.getEventByID(id);
//        request.setAttribute("event", event);
//        request.getRequestDispatcher("updateEvent.jsp").forward(request, response);
//    }
//
//    private void addEvent(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        Event event = new Event();
//        event.setEventName(request.getParameter("eventName"));
//        event.setDescription(request.getParameter("description"));
//        event.setLocation(request.getParameter("location"));
//        event.setStartDate(LocalDateTime.parse(request.getParameter("startDate")));
//        event.setEndDate(LocalDateTime.parse(request.getParameter("endDate")));
//        event.setStatus(request.getParameter("status"));
//        event.setCapacity(Integer.parseInt(request.getParameter("capacity")));
//        event.setImage(request.getParameter("image"));
//        event.setCategoryID(Integer.parseInt(request.getParameter("categoryID")));
//        event.setCreatedAt(LocalDateTime.now());
//
//        Organization org = new Organization();
//        org.setOrgID(1);
//        event.setOrg(org);
//
//        OrgStaff staff = new OrgStaff();
//        staff.setStaffID(1);
//        event.setCreatedByStaff(staff);
//
//        Map<String, String> errors = new HashMap<>();
//        boolean success = eventService.addEvent(event, errors);
//
//        if (success) {
//            response.sendRedirect("event?action=list");
//        } else {
//            request.setAttribute("errors", errors);
//            request.setAttribute("event", event);
//            request.getRequestDispatcher("createEvent.jsp").forward(request, response);
//        }
////        eventService.addEvent(event);
////        response.sendRedirect("event?action=list");
//    }
//
//    private void updateEvent(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("eventID"));
//        Event event = eventService.getEventByID(id);
//        if (event == null) {
//            response.sendRedirect("event?action=list");
//            return;
//        }
//
//        event.setEventName(request.getParameter("eventName"));
//        event.setDescription(request.getParameter("description"));
//        event.setLocation(request.getParameter("location"));
//        event.setStartDate(LocalDateTime.parse(request.getParameter("startDate")));
//        event.setEndDate(LocalDateTime.parse(request.getParameter("endDate")));
//        event.setStatus(request.getParameter("status"));
//        event.setCapacity(Integer.parseInt(request.getParameter("capacity")));
//        event.setImage(request.getParameter("image"));
//        event.setCategoryID(Integer.parseInt(request.getParameter("categoryID")));
//
//        Map<String, String> errors = new HashMap<>();
//        boolean success = eventService.updateEvent(event, errors);
//        if (success) {
//            response.sendRedirect("event?action=detail&id=" + id);
//        } else {
//            request.setAttribute("errors", errors);
//            request.setAttribute("event", event);
//            request.getRequestDispatcher("updateEvent.jsp").forward(request, response);
//        }
////        eventService.updateEvent(event);
////        response.sendRedirect("event?action=detail&id=" + id);
//    }
}
