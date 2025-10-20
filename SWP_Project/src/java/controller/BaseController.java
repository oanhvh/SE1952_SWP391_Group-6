/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDao;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author admin
 */
//@WebServlet(name="BaseController", urlPatterns={"/baseController"})
public class BaseController extends HttpServlet {

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
            // Get user list from DAO
            UserDao userDAO = new UserDao();
            List<Users> users = userDAO.getAllUsers();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User List</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>List of Users</h1>");

            if (users != null && !users.isEmpty()) {
                out.println("<table border='1' cellpadding='5' cellspacing='0'>");
                out.println("<tr>");
                out.println("<th>UserID</th>");
                out.println("<th>Username</th>");
                out.println("<th>Full Name</th>");
                out.println("<th>Email</th>");
                out.println("<th>Role</th>");
                out.println("<th>Status</th>");
                out.println("</tr>");

                for (Users user : users) {
                    out.println("<tr>");
                    out.println("<td>" + user.getUserID() + "</td>");
                    out.println("<td>" + user.getUsername() + "</td>");
                    out.println("<td>" + user.getFullName() + "</td>");
                    out.println("<td>" + user.getEmail() + "</td>");
                    out.println("<td>" + user.getRole() + "</td>");
                    out.println("<td>" + user.getStatus() + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            } else {
                out.println("<p>No users found.</p>");
            }

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
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
        processRequest(request, response);
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

}
