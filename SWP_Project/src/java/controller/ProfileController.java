package controller;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
@WebServlet(name = "ProfileController", urlPatterns = {
    "/ProfileController",
    "/manager/profile",
    "/staff/profile",
    "/volunteer/profile"
})
public class ProfileController extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("authUser") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        // Nếu chưa đăng nhập → quay lại login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String scope = resolveScope(request, role); // Xác định thư mục JSP theo vai trò

        request.setAttribute("user", user);

        if ("edit".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/" + scope + "/edit_profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/" + scope + "/profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("authUser") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Nhận dữ liệu từ form chỉnh sửa
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String avatar = request.getParameter("avatar");
        String dobStr = request.getParameter("dateOfBirth");

        LocalDate dob = null;
        if (dobStr != null && !dobStr.trim().isEmpty()) {
            try {
                dob = LocalDate.parse(dobStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                request.setAttribute("error", "Invalid date format (yyyy-MM-dd)");
                forwardToEdit(request, response, user, role);
                return;
            }
        }

        // Cập nhật thông tin người dùng
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setDateOfBirth(dob);
        user.setUpdatedAt(java.time.LocalDateTime.now());

        boolean success = userDao.updateProfile(user);

        if (success) {
            session.setAttribute("authUser", user);
            request.setAttribute("user", user);
            request.setAttribute("success", "Profile updated successfully!");
            forwardToProfile(request, response, user, role);
        } else {
            request.setAttribute("user", user);
            request.setAttribute("error", "Update failed. Please try again.");
            forwardToEdit(request, response, user, role);
        }
    }

    // ===================== Helper Methods =====================

    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, Users user, String role)
            throws ServletException, IOException {
        String scope = resolveScope(request, role);
        request.getRequestDispatcher("/" + scope + "/profile.jsp").forward(request, response);
    }

    private void forwardToEdit(HttpServletRequest request, HttpServletResponse response, Users user, String role)
            throws ServletException, IOException {
        String scope = resolveScope(request, role);
        request.getRequestDispatcher("/" + scope + "/edit_profile.jsp").forward(request, response);
    }

    /**
     * Xác định thư mục JSP theo đường dẫn hoặc role trong session
     */
    private String resolveScope(HttpServletRequest request, String role) {
        String path = request.getServletPath();
        if (path.startsWith("/manager/") || "Manager".equalsIgnoreCase(role)) return "manager";
        if (path.startsWith("/staff/") || "Staff".equalsIgnoreCase(role)) return "staff";
        if (path.startsWith("/volunteer/") || "Volunteer".equalsIgnoreCase(role)) return "volunteer";
        return "volunteer"; // fallback mặc định
    }
}
