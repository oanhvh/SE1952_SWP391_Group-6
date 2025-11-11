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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import service.EventService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "DelEventController", urlPatterns = {"/staff/delEvent"})
public class DelEventController extends HttpServlet {

    private EventService eventService;
    private StaffDAO staffDAO;
    private CategoryDAO categoryDAO;
    private ManagerDAO managerDAO;

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        staffDAO = new StaffDAO();
        categoryDAO = new CategoryDAO();
        managerDAO = new ManagerDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DelEventController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DelEventController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
                showDelDetail(request, response);
                break;
            case "restore":
                restoreEvent(request, response);
                break;
            default:
                listDelEvents(request, response);
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
        processRequest(request, response);
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

    private void listDelEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Event> eventList = eventService.getEventsByStatus("Inactive");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/staff/listDelEvent.jsp").forward(request, response);
    }

    private void showDelDetail(HttpServletRequest request, HttpServletResponse response)
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
        request.getRequestDispatcher("/staff/viewDelEvent.jsp").forward(request, response);
    }
    
    private void restoreEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        eventService.updateEventStatus(id, "Active");
        response.sendRedirect(request.getContextPath() + "/staff/delEvent");
    }
}
