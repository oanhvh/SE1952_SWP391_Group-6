package service;

import dao.ManagerDAO;
import dao.UserDao;
import entity.Manager;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static dao.UserDao.sha256;
import static service.CodeValidatorService.isNotAdmin;

public class UserService {

    private final UserDao userDao = new UserDao();
    private final ManagerDAO managerDao = new ManagerDAO();

    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");
        List<Users> userList;

        if ((name != null && !name.trim().isEmpty())
                || (role != null && !role.trim().isEmpty())
                || (phone != null && !phone.trim().isEmpty())) {
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
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Part avatarPart = request.getPart("avatarFile");

        String avatarPath = setImage(request, avatarPart);

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

            // ✅ Validate username uniqueness
            if (userDao.isUsernameExisted(username)) {
                request.setAttribute("error", "Username already exists!");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }
            if (userDao.isEmailExisted(email)) {
                request.setAttribute("error", "Email already exists!");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }
            if (validatePhone(request, response, phone, userDao, "admin/AddAccount.jsp")) {
                return;
            }

            // ✅ Validate email format
            if (email == null || email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                request.setAttribute("error", "Invalid email address!");
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
                request.setAttribute("error", "Failed to create user account.");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }

            int userId = userDao.getUserIdByUsername(username);
            if (userId <= 0) {
                request.setAttribute("error", "Failed to retrieve new user ID.");
                request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
                return;
            }
            user.setUserID(userId);

            // ✅ Create manager record
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
                request.setAttribute("success", "Manager account created and verification email sent successfully!");
            } else {
                request.setAttribute("error", "Failed to create manager record.");
            }

            request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while creating the account.");
            request.getRequestDispatcher("admin/AddAccount.jsp").forward(request, response);
        }
    }

    public void getManagerList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        String managerName = request.getParameter("managerName");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");

        List<Manager> managerList;

        // If user typed any search field, filter data
        if ((managerName != null && !managerName.trim().isEmpty())
                || (phone != null && !phone.trim().isEmpty())
                || (status != null && !status.trim().isEmpty())) {
            managerList = managerDao.searchManagers(managerName, phone, status);
        } else {
            managerList = managerDao.getAllManagers();
        }

        request.setAttribute("managerList", managerList);
        request.getRequestDispatcher("admin/listManagerAccount.jsp").forward(request, response);
    }

    public void getManagerByUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isNotAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        String userIdParam = request.getParameter("userId");
        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                Manager manager = managerDao.getManagerByUserId(userId);
                if (manager != null) {
                    request.setAttribute("manager", manager);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("admin/viewManagerDetail.jsp").forward(request, response);
    }

    public void getAdminProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        Users loggedUser = (Users) request.getSession().getAttribute("authUser");
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Ensure only admin can access this page
        if (!"Admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        try {
            String username = loggedUser.getUsername();
            Users adminUser = userDao.getUserbyUsername(username);

            if (adminUser != null) {
                request.setAttribute("user", adminUser);
            } else {
                request.setAttribute("errorMessage", "Unable to load admin profile information.");
            }

            // Forward to JSP
            request.getRequestDispatcher("admin/admin_profile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred while loading profile.");
            request.getRequestDispatcher("admin/admin_profile.jsp").forward(request, response);
        }
    }

    public void updateAdminProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Users loggedUser = (Users) request.getSession().getAttribute("authUser");
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"Admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        try {
            request.setCharacterEncoding("UTF-8");

            String username = loggedUser.getUsername();
            Users adminUser = userDao.getUserbyUsername(username);

            if (adminUser == null) {
                request.setAttribute("error", "Admin not found.");
                request.getRequestDispatcher("admin/admin_profile.jsp").forward(request, response);
                return;
            }

            // --- Get parameters from form ---
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String facebookID = request.getParameter("facebookID");
            String googleID = request.getParameter("googleID");
            Part avatarPart = request.getPart("avatar");

            // --- Validate phone only if it was changed ---
            if (phone != null && !phone.equals(adminUser.getPhone())) {
                if (validatePhone(request, response, phone, userDao, "admin/admin_profile.jsp")) {
                    return; // Stop and forward back if invalid (already handled by validatePhone)
                }
            }

            String avatarPath = setImage(request, avatarPart);
            if (avatarPath != null) {
                adminUser.setAvatar(avatarPath);
            }

            adminUser.setFullName(fullName);
            adminUser.setPhone(phone);
            adminUser.setFacebookID(facebookID);
            adminUser.setGoogleID(googleID);

            if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                adminUser.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }

            boolean updated = userDao.updateProfile(adminUser);
            if (updated) {
                request.setAttribute("success", "Profile updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update profile. Please try again.");
            }

            Users updatedUser = userDao.getUserbyUsername(username);
            request.setAttribute("user", updatedUser);

            request.getRequestDispatcher("admin/admin_profile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred while updating profile.");
            request.getRequestDispatcher("admin/admin_profile.jsp").forward(request, response);
        }
    }

    private static String setImage(HttpServletRequest request, Part avatarPart) throws IOException {
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
        return avatarPath;
    }

    private static boolean validatePhone(HttpServletRequest request,
            HttpServletResponse response,
            String phone,
            UserDao userDao,
            String page) throws ServletException, IOException {
        // ✅ Validate phone
        if (phone == null || phone.isEmpty()) {
            request.setAttribute("error", "Phone number cannot be empty!");
            request.getRequestDispatcher(page).forward(request, response);
            return true;
        }

        if (!phone.matches("^0\\d{9}$")) {
            request.setAttribute("error", "Invalid phone number format! Must start with 0 and be 10 digits.");
            request.getRequestDispatcher(page).forward(request, response);
            return true;
        }

        if (userDao.isPhoneExisted(phone)) {
            request.setAttribute("error", "Phone number already registered!");
            request.getRequestDispatcher(page).forward(request, response);
            return true;
        }
        return false;
    }
}
