package controller;

import dao.DBUtils;
import dao.OrganizationDAO;
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
    private final OrganizationDAO orgDAO = new OrganizationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = param(request, "type"); // Admin or Organization
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");

        if (isBlank(type) || isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Vui lòng nhập đủ thông tin bắt buộc");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
            return;
        }

        if (userDao.isUsernameExisted(username)) {
            request.setAttribute("error", "Username đã tồn tại");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);

            if ("Admin".equalsIgnoreCase(type)) {
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password); // hash in DAO
                u.setRole("Admin");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                userDao.createUser(conn, u, true);
                conn.commit();
                request.setAttribute("success", "Tạo tài khoản Admin thành công");
                request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                return;
            }

            if ("Organization".equalsIgnoreCase(type)) {
                String orgName = param(request, "orgName");
                String description = param(request, "orgDescription");
                String contactInfo = param(request, "orgContact");
                String address = param(request, "orgAddress");

                if (isBlank(orgName)) {
                    request.setAttribute("error", "Vui lòng nhập tên tổ chức");
                    request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                    return;
                }

                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Organization");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                int userId = userDao.createUser(conn, u, true);
                orgDAO.createOrganization(conn, userId, orgName, description, contactInfo, address, null);

                conn.commit();
                request.setAttribute("success", "Tạo tài khoản Organization thành công");
                request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
                return;
            }

            request.setAttribute("error", "Loại tài khoản không hợp lệ");
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Tạo tài khoản thất bại: " + ex.getMessage());
            request.getRequestDispatcher("/admin/users_new.jsp").forward(request, response);
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v != null ? v.trim() : null;
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
