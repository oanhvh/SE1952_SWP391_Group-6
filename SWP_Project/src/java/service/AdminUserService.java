/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDao;
import entity.Users;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NHThanh
 */
public class AdminUserService {

    private final UserDao userDao;

    public AdminUserService() {
        this.userDao = new UserDao();
    }

    public AdminUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Map<String, String> validateCreateAdmin(String username,
            String password,
            String fullName,
            String email,
            String phone) {
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
        }

        if (isBlank(phone)) {
            errors.put("phone", "Phone is required");
        } else if (!phone.matches("^(0\\d{9})$")) {
            errors.put("phone", "Phone must be 10 digits starting with 0");
        }

        return errors;
    }

    public int createAdmin(Connection conn, Users u) throws Exception {
        return userDao.createUser(conn, u, true);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
