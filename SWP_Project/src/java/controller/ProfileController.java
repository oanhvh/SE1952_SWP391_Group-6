package controller;

import dao.UserDao;
import dao.SkillsDAO;
import dao.VolunteerSkillsDAO;
import entity.Skills;
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
import java.util.List;
import service.ProfileService;

@WebServlet(name = "ProfileController", urlPatterns = {
    "/ProfileController",
    "/manager/profile",
    "/staff/profile",
    "/volunteer/profile"
})
public class ProfileController extends HttpServlet {

    private final ProfileService profileService = new ProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("authUser") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String scope = resolveScope(request, role);

        request.setAttribute("user", user);

        // Lấy kỹ năng hiện tại
        request.setAttribute("skills", profileService.getUserSkills(user.getUserID()));

        if ("edit".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/" + scope + "/edit_profile.jsp").forward(request, response);

        } else if ("editSkills".equalsIgnoreCase(action)) {
            // Lấy tất cả skill
            request.setAttribute("allSkills", profileService.getAllSkills());
            request.setAttribute("userSkills", profileService.getUserSkills(user.getUserID()));
            request.getRequestDispatcher("/" + scope + "/update_skills.jsp").forward(request, response);

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

        String action = request.getParameter("action");

        if ("updateProfile".equalsIgnoreCase(action)) {
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

            boolean success = profileService.updateProfile(user, fullName, email, phone, avatar, dob);

            request.setAttribute("user", user);
            request.setAttribute("skills", profileService.getUserSkills(user.getUserID()));

            if (success) {
                session.setAttribute("authUser", user);
                request.setAttribute("success", "Profile updated successfully!");
                forwardToProfile(request, response, user, role);
            } else {
                request.setAttribute("error", "Update failed. Please try again.");
                forwardToEdit(request, response, user, role);
            }

        } else if ("updateSkills".equalsIgnoreCase(action)) {
            String[] selectedSkillIDs = request.getParameterValues("skillIDs");
            profileService.updateSkills(user.getUserID(), selectedSkillIDs);

            request.setAttribute("user", user);
            request.setAttribute("skills", profileService.getUserSkills(user.getUserID()));
            request.setAttribute("success", "Skills updated successfully!");
            forwardToProfile(request, response, user, role);
        }
    }

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

    private String resolveScope(HttpServletRequest request, String role) {
        String path = request.getServletPath();
        if (path.startsWith("/manager/") || "Manager".equalsIgnoreCase(role)) return "manager";
        if (path.startsWith("/staff/") || "Staff".equalsIgnoreCase(role)) return "staff";
        if (path.startsWith("/volunteer/") || "Volunteer".equalsIgnoreCase(role)) return "volunteer";
        return "volunteer";
    }
}
