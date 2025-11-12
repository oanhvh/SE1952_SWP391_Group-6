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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import service.AdminUserService;

/**
 *
 * @author NHThanh
 */
@WebServlet(name = "AdminUserManagementController", urlPatterns = {"/admin/admin_create"})
public class CreateAdminController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final ManagerDAO managerDAO = new ManagerDAO();
    private final AdminUserService adminUserService = new AdminUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra role Admin
//        HttpSession session = request.getSession(false);
//        String role = session != null ? (String) session.getAttribute("role") : null;
//        if (role == null || !"Admin".equalsIgnoreCase(role)) {
//            response.sendRedirect(request.getContextPath() + "/login.jsp");
//            return;
//        }
//        request.getRequestDispatcher("/admin/admin_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // kiểm tra Role Admin
//        HttpSession session = request.getSession(false);
//        String sessionRole = session != null ? (String) session.getAttribute("role") : null;
//        if (sessionRole == null || !"Admin".equalsIgnoreCase(sessionRole)) {
//            response.sendRedirect(request.getContextPath() + "/login.jsp");
//            return;
//        }
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");

        Map<String, String> errors = adminUserService.validateCreateAdmin(username, password, fullName, email, phone);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            Map<String, String> form = new HashMap<>();
            form.put("username", nullToEmpty(username));
            form.put("fullName", nullToEmpty(fullName));
            form.put("email", nullToEmpty(email));
            form.put("phone", nullToEmpty(phone));
            request.setAttribute("form", form);
            request.getRequestDispatcher("/admin/admin_create.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        try {
            conn = DBUtils.getConnection1();
            conn.setAutoCommit(false);
            Users u = new Users();
            u.setUsername(username);
            u.setPasswordHash(password);
            u.setRole("Admin");
            u.setStatus("Active");
            u.setFullName(fullName);
            u.setEmail(email);
            u.setPhone(phone);

            adminUserService.createAdmin(conn, u);
            conn.commit();
            // PRG redirect with success flag
            response.sendRedirect(request.getContextPath() + "/admin/admin_create.jsp?success=1");
            return;

        } catch (Exception ex) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignore) {}
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/admin_create.jsp?error=" + ex.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
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

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
