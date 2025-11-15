/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DBUtils;
import dao.UserDao;
import entity.Users;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NHThanh
 */
public class CreateAdminService {

    private final UserDao userDao;

    public CreateAdminService() {
        this.userDao = new UserDao();
    }

    public CreateAdminService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Map<String, String> validateCreateAdmin(String username, String password, String fullName, String email, String phone) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(username)) {
            errors.put("username", "Username is required");
        } else if (userDao.isUsernameExisted(username)) {
            errors.put("username", "Username already exists");
        }

        if (isBlank(password)) {
            errors.put("password", "Password is required");
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            errors.put("password", "Minimum 8 characters, including uppercase, lowercase and numbers");
        }

        if (isBlank(fullName)) {
            errors.put("fullName", "FullName is required");
        }

        if (isBlank(email)) {
            errors.put("email", "Email is required");
        } else if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            errors.put("email", "Email is invalid");
        } else if (userDao.isEmailExisted(email)) {
            errors.put("email", "Email already exists");
        }

        if (isBlank(phone)) {
            errors.put("phone", "Phone is required");
        } else if (!phone.matches("^(0\\d{9})$")) {
            errors.put("phone", "Phone must be 10 digits starting with 0");
        } else if (userDao.isPhoneExisted(phone)) {
            errors.put("phone", "Phone already exists");
        }

        return errors;
    }

    public int createAdmin(Connection conn, Users u) throws Exception {
        return userDao.createUser(conn, u, true);
    }

    public boolean registerAdmin(String username, String password, String fullName, String email, String phone, LocalDate dob, Map<String, String> errors) {
        Map<String, String> v = validateCreateAdmin(username, password, fullName, email, phone);
        if (!v.isEmpty()) {
            errors.putAll(v);
            return false;
        }
        try (Connection conn = DBUtils.getConnection1()) {
            conn.setAutoCommit(false);
            try {
                Users u = new Users();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setRole("Admin");
                u.setStatus("Active");
                u.setFullName(fullName);
                u.setEmail(email);
                u.setPhone(phone);
                u.setDateOfBirth(dob);
                u.setLoginProvider("Local");
                u.setIsEmailVerified(false);
                u.setIsPhoneVerified(false);

                userDao.createUser(conn, u, true);
                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("system", "Unexpected error during admin creation");
            return false;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
