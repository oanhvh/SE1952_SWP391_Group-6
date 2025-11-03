/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.VolunteerApplicationsDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CompleteEventController")
public class CompleteEventController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("applicationId"));
        VolunteerApplicationsDao dao = new VolunteerApplicationsDao();
        boolean success = dao.markAsCompleted(appId);

        response.sendRedirect(request.getContextPath() + "/VolunteerTodayController");
    }
}

