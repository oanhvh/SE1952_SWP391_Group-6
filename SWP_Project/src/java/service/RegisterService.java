/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DBUtils;
import dao.EmployeeCodesDAO;
import dao.StaffDAO;
import dao.UserDao;
import dao.VolunteerDAO;
import entity.Users;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NHThanh
 */
public class RegisterService {

    private final UserDao userDao = new UserDao();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();
    private final EmployeeCodesDAO codesDAO = new EmployeeCodesDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final CodeValidatorService codeService = new CodeValidatorService();

    public Map<String, String> validateRegistration(String username, String password, String role,
            String fullName, String email, String phone,
            String dateOfBirth, String employeeCode) {

        Map<String, String> errors = new HashMap<>();

        if (isBlank(username)) {
            errors.put("username", "Username is required");
        } else if (userDao.isUsernameExisted(username)) {
            errors.put("username", "Username already exists");
        }

        if (isBlank(password)) {
            errors.put("password", "Password is required");
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            errors.put("password", "Password must be at least 8 characters and include uppercase, lowercase, and a number");
        }

        if (isBlank(role)) {
            errors.put("role", "Role is required");
        } else if (!("Volunteer".equalsIgnoreCase(role) || "Staff".equalsIgnoreCase(role))) {
            errors.put("role", "Invalid role");
        }

        if (isBlank(fullName)) {
            errors.put("fullName", "Full name is required");
        }

        if (isBlank(email)) {
            errors.put("email", "Email is required");
        } else if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            errors.put("email", "Invalid email format");
        }

        if (isBlank(phone)) {
            errors.put("phone", "Phone is required");
        } else if (!phone.matches("^(0\\d{9})$")) {
            errors.put("phone", "Phone must be 10 digits starting with 0");
        }

        if (isBlank(dateOfBirth)) {
            errors.put("dateOfBirth", "Date of Birth is required");
        } else {
            try {
                LocalDate dob = LocalDate.parse(dateOfBirth);
                if (dob.isAfter(LocalDate.now())) {
                    errors.put("dateOfBirth", "Invalid date of birth");
                } else if (dob.isBefore(LocalDate.now().minusYears(130))) {
                    errors.put("dateOfBirth", "Invalid date of birth");
                }
            } catch (Exception ex) {
                errors.put("dateOfBirth", "Invalid date format (YYYY-MM-DD)");
            }
        }

        if ("Staff".equalsIgnoreCase(role) && isBlank(employeeCode)) {
            errors.put("employeeCode", "Employee code is required for staff registration");
        }

        return errors;
    }

    public boolean registerUser(String role, String username, String password,
            String fullName, String email, String phone,
            LocalDate dob, String employeeCode,
            Map<String, String> errors) {
        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);

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
                volunteerDAO.createVolunteer(conn, userId);

                conn.commit();
                return true;

            } else if ("Staff".equalsIgnoreCase(role)) {
                EmployeeCodesDAO.CodeInfo codeInfo = codeService.validateEmployeeCode(conn, employeeCode);
                if (codeInfo == null) {
                    errors.put("employeeCode", "Invalid employee code");
                    conn.rollback();
                    return false;
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
                codesDAO.markCodeUsed(conn, codeInfo.codeId);

                conn.commit();
                return true;
            }

            errors.put("role", "Unsupported role");
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                // rollback nếu có lỗi
                if (!errors.containsKey("system")) {
                    errors.put("system", "Unexpected error during registration");
                }
            } catch (Exception ignore) {
            }
            return false;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
