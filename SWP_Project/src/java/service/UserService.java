package service;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");
        List<Users> userList;

        if ((name != null && !name.trim().isEmpty()) ||
                (role != null && !role.trim().isEmpty()) ||
                (phone != null && !phone.trim().isEmpty())) {
            userList = userDao.searchUser(name, role, phone);
        } else {
            userList = userDao.getAllUsers();
        }

        request.setAttribute("userList", userList);
        request.getRequestDispatcher("listAccount.jsp").forward(request, response);
    }

    public void getUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");
        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                Users user = userDao.getUserById(userId);
                if (user != null) {
                    user.setPasswordHash(null);
                    request.setAttribute("user", user);
                }
            } catch (NumberFormatException e) {
                // Handle invalid id
            }
        }
        request.getRequestDispatcher("viewAccountDetail.jsp").forward(request, response);
    }

    public void displayEditInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Invalid username.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }

        UserDao userDao = new UserDao();
        Users user = userDao.getUserbyUsername(username);

        if (user == null) {
            request.setAttribute("error", "User not found.");
        } else {
            request.setAttribute("user", user);
        }

        request.getRequestDispatcher("editProfile.jsp").forward(request, response);
    }

    public void editInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        UserDao userDao = new UserDao();
        Users user = userDao.getUserbyUsername(username);

        if (user == null) {
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUpdatedAt(LocalDateTime.now());

        boolean success = userDao.updateProfileByUsername(user);

        if (success) {
            // Reload user info after update
            Users updatedUser = userDao.getUserbyUsername(username);
            request.setAttribute("user", updatedUser);
            request.setAttribute("success", "Update profile success.");
        } else {
            request.setAttribute("user", user);
            request.setAttribute("error", "There are something wrong when update profile. Please try again later.");
        }

        request.getRequestDispatcher("editProfile.jsp").forward(request, response);

    }

    public void addNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try {
            UserDao userDao = new UserDao();

            if (userDao.isUsernameExisted(username)) {
                request.setAttribute("errorMessage", "Username already exists!");
                request.getRequestDispatcher("AddAccount.jsp").forward(request, response);
                return;
            }

            Users user = new Users();
            user.setUsername(username);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setRole(role);
            user.setStatus(status);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            int result = userDao.addUser(user);

            if (result > 0) {
                request.setAttribute("successMessage", "Account created successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to create account. Please try again later.");
            }

            request.getRequestDispatcher("AddAccount.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while creating the account.");
            request.getRequestDispatcher("AddAccount.jsp").forward(request, response);

        }
    }
}