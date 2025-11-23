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
import entity.Manager;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import service.EventService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "ViewEventDetailsVolunteerController", urlPatterns = {"/volunteer/eventdetails"})
public class ViewEventDetailsVolunteerController extends HttpServlet {

    private EventService eventService;
    private CategoryDAO categoryDAO;
    private StaffDAO staffDAO;
    private ManagerDAO managerDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        categoryDAO = new CategoryDAO();
        staffDAO = new StaffDAO();
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

        switch (action) {
            case "detail":
                showDetail(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/volunteer/events.jsp");
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

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Event event = eventService.getEventById(id);

            if (event == null) {
                request.setAttribute("error", "Event not found!");
                request.getRequestDispatcher("/volunteer/events.jsp").forward(request, response);
                return;
            }

            Category category = categoryDAO.getCategoryById(event.getCategoryID());

            String staffName = staffDAO.getUserNameByStaffId(event.getCreatedByStaffID());
            String managerName = null;
            String managerContact = null;
            String managerAddress = null;

            if (event.getManagerID() > 0) {
                Manager manager = managerDAO.getManagerById(event.getManagerID());
                if (manager != null) {
                    managerName = manager.getManagerName();
                    managerContact = manager.getContactInfo();
                    managerAddress = manager.getAddress();
                }
            }

            request.setAttribute("event", event);
            request.setAttribute("categoryName", category != null ? category.getCategoryName() : "Unknown");
            request.setAttribute("staffName", staffName);
            request.setAttribute("managerName", managerName);
            request.setAttribute("managerContact", managerContact);
            request.setAttribute("managerAddress", managerAddress);

            request.getRequestDispatcher("/volunteer/viewEvent.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
        }
    }
}
