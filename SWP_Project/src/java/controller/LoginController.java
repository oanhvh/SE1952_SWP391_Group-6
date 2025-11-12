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
/**
 *
 * @author NHThanh
 */

@WebServlet(name = "LoginController", urlPatterns = {"/login", "/login.html"})
public class LoginController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final PasswordValidatorService passwordService = new PasswordValidatorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Please enter username and password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        Users user = userDao.getUserbyUsername(username);
        if (user == null) {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // If the account has no local password, require provider login (e.g., Google only account)
        if (user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()) {
            String provider = user.getLoginProvider();
            if (provider == null || provider.trim().isEmpty()) {
                provider = "Google"; // default friendly hint
            }
            request.setAttribute("error", "This account uses " + provider + " login. Please use " + provider + " to sign in.");
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

        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", user);
        session.setAttribute("role", role);
        session.setAttribute("userId", user.getUserID());

        if ("Volunteer".equalsIgnoreCase(role)) {
            session.setAttribute("volunteerId", user.getUserID());
            response.sendRedirect(request.getContextPath() + "/volunteer/index_1.jsp");
        } else if ("Staff".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/staff/index_1.jsp");
        } else if ("Manager".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/manager/index_1.jsp");
        } else if ("Admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/listAccount.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
