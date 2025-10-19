package service;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

    public int addUser(Users user) {
        return userDao.addUser(user);
    }
}