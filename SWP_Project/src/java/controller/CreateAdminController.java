///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package controller;
//
//import dao.DBUtils;
//import dao.ManagerDAO;
//import dao.UserDao;
//import entity.Users;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Connection;
//
///**
// *
// * @author NHThanh
// */
//@WebServlet(name = "AdminUserManagementController", urlPatterns = {"/admin/admin_create"})
//public class CreateAdminController extends HttpServlet {
//
//    private final UserDao userDao = new UserDao();
//    private final ManagerDAO managerDAO = new ManagerDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Only Admin can access
//        HttpSession session = request.getSession(false);
//        String role = session != null ? (String) session.getAttribute("role") : null;
//        if (role == null || !"Admin".equalsIgnoreCase(role)) {
//            response.sendRedirect(request.getContextPath() + "/login.jsp");
//            return;
//        }
//        request.getRequestDispatcher("/admin/admin_create.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Only Admin can post
//        HttpSession session = request.getSession(false);
//        String sessionRole = session != null ? (String) session.getAttribute("role") : null;
//        if (sessionRole == null || !"Admin".equalsIgnoreCase(sessionRole)) {
//            response.sendRedirect(request.getContextPath() + "/login.jsp");
//            return;
//        }
//        // Accept either 'type' or 'role' but force Admin
//        String type = param(request, "type");
//        String roleParam = param(request, "role");
//        String username = param(request, "username");
//        String password = param(request, "password");
//        String fullName = param(request, "fullName");
//        String email = param(request, "email");
//        String phone = param(request, "phone");
//
//        if (isBlank(username) || isBlank(password)) {
//            request.setAttribute("error", "Please enter required information");
//            request.getRequestDispatcher("/admin/admin_create.jsp").forward(request, response);
//            return;
//        }
//
//        if (userDao.isUsernameExisted(username)) {
//            request.setAttribute("error", "Username already exists");
//            request.getRequestDispatcher("/admin/admin_create.jsp").forward(request, response);
//            return;
//        }
//
//        try (Connection conn = DBUtils.getConnection1()) {
//            conn.setAutoCommit(false);
//            // Force Admin regardless of provided param
//                Users u = new Users();
//                u.setUsername(username);
//                u.setPasswordHash(password);
//                u.setRole("Admin");
//                u.setStatus("Active");
//                u.setFullName(fullName);
//                u.setEmail(email);
//                u.setPhone(phone);
//
//                userDao.createUser(conn, u, true);
//                conn.commit();
//                // PRG redirect with success flag
//                response.sendRedirect(request.getContextPath() + "/admin/admin_create.jsp?success=1");
//                return;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.sendRedirect(request.getContextPath() + "/admin/admin_create.jsp?error=" + ex.getMessage());
//        }
//    }
//
//    //xóa khoảng trắng
//    private String param(HttpServletRequest req, String name) {
//        String v = req.getParameter(name); //lấy giá trị từ form
//        return v != null ? v.trim() : null;
//    }
//
//    //kiểm tra chuỗi rỗng
//    private boolean isBlank(String s) {
//        return s == null || s.trim().isEmpty();
//    }
//}