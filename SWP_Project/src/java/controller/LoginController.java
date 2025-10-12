/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import service.PasswordValidatorService;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final PasswordValidatorService passwordService = new PasswordValidatorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ tài khoản và mật khẩu");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        Users user = userDao.getUserbyUsername(username);
        if (user == null) {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        boolean ok = passwordService.validatePassword(user.getPasswordHash(), password);
        if (!ok) {
            request.setAttribute("error", "Password is incorrect");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        boolean updated = userDao.updateLastLogin(user.getUserID());
        if (!updated) {
            request.setAttribute("error", "Failed to update last login");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        String role = userDao.getUserRole(user.getUserID());

        // Store session
        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", user);
        session.setAttribute("role", role);

        // Redirect by role (placeholder paths)
        if ("Volunteer".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/volunteer/home");
        } else if ("OrgStaff".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/orgstaff/home");
        } else if ("Admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else if ("Organization".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/organization/home");
        } else {
            // Unknown role -> default
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // show login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
