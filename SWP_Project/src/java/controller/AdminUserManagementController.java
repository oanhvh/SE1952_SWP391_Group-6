/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author NHThanh
 */
import dao.DBUtils;
import dao.ManagerDAO;
import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "AdminUserManagementController", urlPatterns = {"/admin/users/new"})
public class AdminUserManagementController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final ManagerDAO managerDAO = new ManagerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = param(request, "type");
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");

        if (isBlank(type) || isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Please enter required information");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
            return;
        }

        if (userDao.isUsernameExisted(username)) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);
            //tạo Admin
            if ("Admin".equalsIgnoreCase(type)) {
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Admin");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                userDao.createUser(conn, u, true);
                conn.commit();
                request.setAttribute("success", "Admin account created successfully");
                request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                return;
            }

            //thêm thông tin so với admin do yêu cầu từ Long
            if ("Manager".equalsIgnoreCase(type)) {
                String managerName = param(request, "managerName");
                String contactInfo = param(request, "managerContact");
                String address = param(request, "managerAddress");

                if (isBlank(managerName)) {
                    request.setAttribute("error", "Please enter manager name");
                    request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                    return;
                }
                //Tạo Manager
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Manager");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                int userId = userDao.createUser(conn, u, true);

                managerDAO.createManager(conn, userId, managerName, contactInfo, address, null);

                conn.commit();
                request.setAttribute("success", "Manager account created successfully");
                request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                return;
            }
            request.setAttribute("error", "Invalid account type");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Failed to create account: " + ex.getMessage());
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
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
