/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.VolunteerApplications;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import service.VolunteerApplicationsService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "VolunteerApplicationsController", urlPatterns = {"/application"})
public class VolunteerApplicationsController extends HttpServlet {

    private VolunteerApplicationsService volunteerAppService;

    @Override
    public void init() throws ServletException {
        volunteerAppService = new VolunteerApplicationsService();
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
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteApplication(request, response);
                break;
            case "status":
                updateStatus(request, response);
                break;
            default:
                listApplications(request, response);
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
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "add":
                addApplication(request, response);
                break;
            case "update":
                updateApplication(request, response);
                break;
            default:
                response.sendRedirect("volunteerApplications?action=list");
                break;
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

    private void listApplications(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<VolunteerApplications> list = volunteerAppService.getAllVolunteerApplications();
        request.setAttribute("applicationList", list);
        request.getRequestDispatcher("listVolunteerApplications.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VolunteerApplications app = volunteerAppService.getApplicationsByVolunteer(id).stream().findFirst().orElse(null);
        request.setAttribute("application", app);
        request.getRequestDispatcher("viewVolunteerApplication.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        VolunteerApplications app = volunteerAppService.getApplicationsByVolunteer(id).stream().findFirst().orElse(null);
        request.setAttribute("application", app);
        request.getRequestDispatcher("updateVolunteerApplication.jsp").forward(request, response);
    }

    private void addApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VolunteerApplications app = new VolunteerApplications();
            app.setVolunteerID(Integer.parseInt(request.getParameter("volunteerID")));
            app.setEventID(Integer.parseInt(request.getParameter("eventID")));
//            app.setStatus(request.getParameter("status"));
            app.setApplicationDate(request.getParameter("applicationDate") != null
                    ? LocalDateTime.parse(request.getParameter("applicationDate"))
                    : LocalDateTime.now());
            volunteerAppService.addVolunteerApplication(app);
            response.sendRedirect("volunteerApplications?action=list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("createVolunteerApplication.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error while adding application");
            request.getRequestDispatcher("createVolunteerApplication.jsp").forward(request, response);
        }
    }

    private void updateApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VolunteerApplications app = new VolunteerApplications();
            app.setApplicationID(Integer.parseInt(request.getParameter("applicationID")));
            app.setVolunteerID(Integer.parseInt(request.getParameter("volunteerID")));
            app.setEventID(Integer.parseInt(request.getParameter("eventID")));
            app.setStatus(request.getParameter("status"));
            app.setApplicationDate(request.getParameter("applicationDate") != null
                    ? LocalDateTime.parse(request.getParameter("applicationDate"))
                    : LocalDateTime.now());
            volunteerAppService.updateVolunteerApplication(app);
            response.sendRedirect("volunteerApplications?action=list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("updateVolunteerApplication.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error while updating application");
            request.getRequestDispatcher("updateVolunteerApplication.jsp").forward(request, response);
        }
    }

    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        volunteerAppService.deleteVolunteerApplication(id);
        response.sendRedirect("volunteerApplications?action=list");
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        Integer approvedBy = request.getParameter("approvedBy") != null && !request.getParameter("approvedBy").isBlank()
                ? Integer.parseInt(request.getParameter("approvedBy"))
                : null;
        volunteerAppService.updateStatus(id, status, approvedBy);
        response.sendRedirect("volunteerApplications?action=list");
    }
}
