/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.VolunteerApplicationsDAO;
import entity.Users;
import entity.VolunteerApplications;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/VolunteerHistory")
public class VolunteerHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = authUser.getUserID(); // Lấy userID từ session
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        List<VolunteerApplications> historyList = dao.getCompletedApplicationsByUserId(userId);

        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("/volunteer/volunteer_history.jsp").forward(request, response);
    }
}

