/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ManagerDAO;
import dao.StaffDAO;
import entity.Category;
import entity.Event;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import service.EventService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
/**
 *
 * @author DucNM
 */
@WebServlet(name = "PendEventController", urlPatterns = {"/staff/pendEvent"})
public class PendEventController extends HttpServlet {

    private EventService eventService;
    private CategoryDAO categoryDAO;
    private StaffDAO staffDAO;
    private ManagerDAO managerDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        staffDAO = new StaffDAO();
        categoryDAO = new CategoryDAO();
        managerDAO = new ManagerDAO();
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
            action = "";
        }
        switch (action) {
            case "detail":
                showPendDetail(request, response);
                break;
            default:
                listPendEvents(request, response);
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

    private void listPendEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Event> eventList = eventService.getEventsByStatus("Pending");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/staff/listPendEvent.jsp").forward(request, response);
    }

    private void showPendDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
//        request.setAttribute("event", event);
        if (event != null) {
            Category category = categoryDAO.getCategoryById(event.getCategoryID());
            String categoryName = (category != null) ? category.getCategoryName() : "Unknown";

            String staffName = staffDAO.getUserNameByStaffId(event.getCreatedByStaffID());
            String managerName = managerDAO.getFullNameByManagerId(event.getManagerID());

            request.setAttribute("event", event);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("staffName", staffName);
            request.setAttribute("managerName", managerName);
        }
        request.getRequestDispatcher("/staff/viewPendEvent.jsp").forward(request, response);
    }
}
