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
import dao.EmployeeCodesDAO;
import dao.StaffDAO;
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
    private final EmployeeCodesDAO codesDAO = new EmployeeCodesDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final CodeValidatorService codeService = new CodeValidatorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/AccountRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = param(request, "role");
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");
        String employeeCode = param(request, "employeeCode");
        String dobStr = param(request, "dateOfBirth");

        if (isBlank(username) || isBlank(password) || isBlank(role)) {
            errorForward(request, response, "Please enter required information");
            return;
        }

        if (userDao.isUsernameExisted(username)) {
            errorForward(request, response, "Username already exists");
            return;
        }

        if (!isStrongPassword(password)) {
            errorForward(request, response, "Password must be at least 8 characters and include uppercase, lowercase, and a number");
            return;
        }

        java.time.LocalDate dob = null;
        try {
            if (isBlank(dobStr)) {
                errorForward(request, response, "Date of Birth is required");
                return;
            }
            dob = java.time.LocalDate.parse(dobStr);
        } catch (Exception ex) {
            errorForward(request, response, "Invalid Date of Birth format");
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);

            //tạo tài khoản volunteer + user || staff + user
            if ("Volunteer".equalsIgnoreCase(role)) {
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Volunteer");
                u.setStatus("Pending");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);
                u.setDateOfBirth(dob);
                u.setLoginProvider("Local");
                u.setIsEmailVerified(false);
                u.setIsPhoneVerified(false);

                int userId = userDao.createUser(conn, u, true);

                volunteerDAO.createVolunteer(conn, userId); //tạo hồ sơ liên kết với user

                conn.commit();
                successForward(request, response, "Registration successful. Please proceed to the login page to sign in.");
                return;

            } else if ("Staff".equalsIgnoreCase(role)) {
                EmployeeCodesDAO.CodeInfo codeInfo = codeService.validateEmployeeCode(conn, employeeCode);
                if (codeInfo == null) {
                    conn.rollback();
                    errorForward(request, response, "Invalid employee code");
                    return;
                }

                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Staff");
                u.setStatus("Pending");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);
                u.setDateOfBirth(dob);
                u.setLoginProvider("Local");
                u.setIsEmailVerified(false);
                u.setIsPhoneVerified(false);

                int userId = userDao.createUser(conn, u, true);

                staffDAO.createStaff(conn, userId, codeInfo.managerId, codeInfo.codeId, true);
                codesDAO.markCodeUsed(conn, codeInfo.codeId); //đánh dấu mã đã dùng

                conn.commit();
                successForward(request, response, "Registration successful. Please proceed to the login page to sign in.");
                return;
            }

            errorForward(request, response, "Role not supported for public registration");
        } catch (Exception ex) {
            ex.printStackTrace();
            errorForward(request, response, "Registration failed. Please try again.");
        }
    }

    //xóa khoảng trắng
    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v != null ? v.trim() : null;
    }

    //kiểm tra chuỗi rỗng
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void errorForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/AccountRegister.jsp").forward(request, response);
    }

    private void successForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException, ServletException {
        request.setAttribute("success", message);
        try {
            request.getRequestDispatcher("/register_success.jsp").forward(request, response);
        } catch (ServletException ex) {
            throw ex;
        }
    }

    private boolean isStrongPassword(String pwd) {
        if (pwd == null) {
            return false;
        }
        return pwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}
