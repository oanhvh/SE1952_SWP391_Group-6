/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DBUtils;
import dao.OrgEmployeeCodesDAO;
import dao.OrgStaffDAO;
import dao.UserDao;
import dao.VolunteerDAO;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import service.CodeValidatorService;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();
    private final OrgEmployeeCodesDAO codesDAO = new OrgEmployeeCodesDAO();
    private final OrgStaffDAO orgStaffDAO = new OrgStaffDAO();
    private final CodeValidatorService codeService = new CodeValidatorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = param(request, "role"); // expected: Volunteer or OrgStaff
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");
        String employeeCode = param(request, "employeeCode"); // only for OrgStaff

        if (isBlank(username) || isBlank(password) || isBlank(role)) {
            errorForward(request, response, "Vui lòng nhập đầy đủ thông tin bắt buộc");
            return;
        }

        if (userDao.isUsernameExisted(username)) {
            errorForward(request, response, "Username đã tồn tại");
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);

            if ("Volunteer".equalsIgnoreCase(role)) {
                // Create user
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password); // will be hashed in DAO
                u.setRole("Volunteer");
                u.setStatus("Pending");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                int userId = userDao.createUser(conn, u, true);

                // Create volunteer
                volunteerDAO.createVolunteer(conn, userId);

                conn.commit();
                successRedirect(response, request, "Đăng ký thành công. Vui lòng đăng nhập.");
                return;

            } else if ("OrgStaff".equalsIgnoreCase(role)) {
                // Validate employee code
                OrgEmployeeCodesDAO.CodeInfo codeInfo = codeService.validateEmployeeCode(conn, employeeCode);
                if (codeInfo == null) {
                    conn.rollback();
                    errorForward(request, response, "Mã nhân viên không hợp lệ");
                    return;
                }

                // Create user
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password); // will be hashed in DAO
                u.setRole("OrgStaff");
                u.setStatus("Pending");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);

                int userId = userDao.createUser(conn, u, true);

                // Create org staff and mark code used
                orgStaffDAO.createOrgStaff(conn, userId, codeInfo.orgId, codeInfo.codeId, true);
                codesDAO.markCodeUsed(conn, codeInfo.codeId);

                conn.commit();
                successRedirect(response, request, "Đăng ký thành công. Vui lòng đăng nhập.");
                return;
            }

            // Not supported role for public registration
            errorForward(request, response, "Vai trò không được hỗ trợ để tự đăng ký");
        } catch (Exception ex) {
            ex.printStackTrace();
            errorForward(request, response, "Đăng ký thất bại. Vui lòng thử lại.");
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v != null ? v.trim() : null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void errorForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private void successRedirect(HttpServletResponse response, HttpServletRequest request, String message)
            throws IOException {
        // attach a simple query param message; ideally use flash scope
        String msg = java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8);
        response.sendRedirect(request.getContextPath() + "/login?msg=" + msg);
    }
}
