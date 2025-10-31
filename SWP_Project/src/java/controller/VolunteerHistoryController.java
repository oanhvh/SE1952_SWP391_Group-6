/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 *
 * @author admin
 */
package controller;

import dao.VolunteerApplicationsHistoryDao;
import dao.VolunteerDAO;
import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "VolunteerHistoryController", urlPatterns = {"/volunteer/history"})
public class VolunteerHistoryController extends HttpServlet {

    private final VolunteerDAO volunteerDao = new VolunteerDAO();
    private final VolunteerApplicationsHistoryDao applicationsDao = new VolunteerApplicationsHistoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("authUser") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/volunteer/login.jsp");
            return;
        }

        try {
            Integer volunteerId = volunteerDao.getVolunteerIdByUserId(user.getUserID());
            if (volunteerId == null) {
                request.setAttribute("error", "No volunteer profile found for your account.");
                request.getRequestDispatcher("/volunteer/history.jsp").forward(request, response);
                return;
            }

            List<VolunteerApplications> applications =
                    applicationsDao.getApplicationsByVolunteerId(volunteerId);

            request.setAttribute("applications", applications);
            request.getRequestDispatcher("/volunteer/history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load volunteer history.");
            request.getRequestDispatcher("/volunteer/history.jsp").forward(request, response);
        }
    }
}
