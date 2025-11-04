/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DBUtils;
import dao.ManagerDAO;
import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 *
 * @author NHThanh
 */
@WebServlet(name = "CreateManagerController", urlPatterns = {"/admin/manager_create"})
public class CreateManagerController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final ManagerDAO managerDAO = new ManagerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Only Admin can access
        HttpSession session = request.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;
        if (role == null || !"Admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        request.getRequestDispatcher("/admin/manager_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Only Admin can post
        HttpSession session = request.getSession(false);
        String sessionRole = session != null ? (String) session.getAttribute("role") : null;
        if (sessionRole == null || !"Admin".equalsIgnoreCase(sessionRole)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        // Accept either 'type' or 'role' but force Manager
        String type = param(request, "type");
        String roleParam = param(request, "role");
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");
        // Extra fields from JSP (optional for now)
        String managerCode = param(request, "managerCode");
        String department = param(request, "department");
        String position = param(request, "position");

        if (isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Please enter required information");
            request.getRequestDispatcher("/admin/manager_create.jsp").forward(request, response);
            return;
        }

        if (userDao.isUsernameExisted(username)) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("/admin/manager_create.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);
            // Force Manager regardless of provided param
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Manager");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                int userId = userDao.createUser(conn, u, true);
                // Keep DAO call compatible with existing signature. Map available info.
                String managerName = !isBlank(fullName) ? fullName : username;
                String contactInfo = !isBlank(phone) ? phone : email;
                String address = position; // temporary mapping; adjust DAO to proper fields later
                managerDAO.createManager(conn, userId, managerName, contactInfo, address, null);

                conn.commit();
                response.sendRedirect(request.getContextPath() + "/admin/manager_create.jsp?success=1");
                return;

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manager_create.jsp?error=" + ex.getMessage());
        }
    }

    //xóa khoảng trắng
    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name); //lấy giá trị từ form
        return v != null ? v.trim() : null;
    }

    //kiểm tra chuỗi rỗng
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
