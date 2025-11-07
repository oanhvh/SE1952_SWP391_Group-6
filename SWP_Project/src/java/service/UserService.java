package service;

import dao.ManagerDAO;
import dao.UserDao;
import entity.Manager;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static dao.UserDao.sha256;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import static service.CodeValidatorService.isNotAdmin;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }
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
        request.getRequestDispatcher("admin/listAccount.jsp").forward(request, response);
    }

    public void getUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }
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
        request.getRequestDispatcher("admin/viewAccountDetail.jsp").forward(request, response);
    }

    public void displayEditInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }
        String username = request.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Invalid username.");
            request.getRequestDispatcher("admin/editProfile.jsp").forward(request, response);
            return;
        }

        UserDao userDao = new UserDao();
        Users user = userDao.getUserbyUsername(username);

        if (user == null) {
            request.setAttribute("error", "User not found.");
        } else {
            request.setAttribute("user", user);
        }

        request.getRequestDispatcher("admin/editProfile.jsp").forward(request, response);
    }

    public void editInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        System.out.println(username);
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Part avatarPart = request.getPart("avatarFile");

        String avatarPath = null;
        if (avatarPart != null && avatarPart.getSize() > 0) {
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "avatars";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
            String filePath = uploadPath + File.separator + fileName;
            avatarPart.write(filePath);
            avatarPath = "images/" + fileName;
        }

        UserDao userDao = new UserDao();
        Users user = userDao.getUserbyUsername(username);

        if (user == null) {
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("admin/editProfile.jsp").forward(request, response);
            return;
        }

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUpdatedAt(LocalDateTime.now());
        if (avatarPath != null) {
            user.setAvatar(avatarPath);
        }

        boolean success = userDao.updateProfileByUsername(user);

        if (success) {
            Users updatedUser = userDao.getUserbyUsername(username);
            request.setAttribute("user", updatedUser);
            request.setAttribute("success", "Profile updated successfully.");
        } else {
            request.setAttribute("user", user);
            request.setAttribute("error", "Failed to update profile. Please try again later.");
        }

        request.getRequestDispatcher("admin/editProfile.jsp").forward(request, response);
    }

    public void addNewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        String managerName = request.getParameter("managerName").trim();
        String contactInfo = request.getParameter("contactInfo").trim();
        String address = request.getParameter("address").trim();

        try {
            UserDao userDao = new UserDao();
            ManagerDAO managerDao = new ManagerDAO();

            if (userDao.isUsernameExisted(username)) {
                request.setAttribute("errorMessage", "Username already exists!");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }

            LocalDate dateOfBirth = null;
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            }

            password = sha256(password);

            Users user = new Users();
            user.setUsername(username);
            user.setPasswordHash(password);
            user.setRole(role);
            user.setStatus(status);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setDateOfBirth(dateOfBirth);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            int addUserResult = userDao.addUser(user);
            if (addUserResult <= 0) {
                request.setAttribute("errorMessage", "Failed to create user account.");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }

            int userId = userDao.getUserIdByUsername(username);
            if (userId <= 0) {
                request.setAttribute("errorMessage", "Failed to retrieve new user ID.");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }
            user.setUserID(userId);

            // Step 3: Insert Manager
            Manager manager = new Manager();
            manager.setUser(user);
            manager.setManagerName(managerName);
            manager.setContactInfo(contactInfo);
            manager.setAddress(address);
            manager.setRegistrationDate(LocalDate.now());

            Users adminUser = (Users) request.getSession().getAttribute("loggedUser");
            manager.setCreatedBy(adminUser);

            int managerResult = managerDao.addManager(manager);

            if (managerResult > 0) {
                request.setAttribute("successMessage", "Manager account created successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to create manager record.");
            }

            request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while creating the account.");
            request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
        }
    }


}