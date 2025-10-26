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
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ChangePasswordController", urlPatterns = {
    "/manager/change-password",
    "/staff/change-password",
    "/volunteer/change-password",
    "/account/change-password"
})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users auth = (session != null) ? (Users) session.getAttribute("authUser") : null;
        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String scope = resolveScope(request);
        if (!isScopeAllowed(scope, session)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        forwardToScopeJsp(request, response, scope);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users auth = (session != null) ? (Users) session.getAttribute("authUser") : null;
        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String currentPwd = trimOrNull(request.getParameter("currentPassword"));
        String newPwd = trimOrNull(request.getParameter("newPassword"));
        String confirmPwd = trimOrNull(request.getParameter("confirmPassword"));

        if (isBlank(currentPwd) || isBlank(newPwd) || isBlank(confirmPwd)) {
            setErrorAndForward(request, response, "Please enter all required information");
            return;
        }
        if (!newPwd.equals(confirmPwd)) {
            setErrorAndForward(request, response, "The confirmation password does not match");
            return;
        }
        if (!isStrongPassword(newPwd)) {
            setErrorAndForward(request, response, "The new password is not strong enough (minimum 8 characters, including uppercase letters, lowercase letters, and numbers)");
            return;
        }
        if (newPwd.equals(currentPwd)) {
            setErrorAndForward(request, response, "The new password must not be the same as the current password");
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            // Lấy hash đang lưu và so khớp với currentPwd
            String sqlSelect = "SELECT PasswordHash FROM Users WHERE UserID = ?";
            String storedHash = null;
            try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
                ps.setInt(1, auth.getUserID());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        storedHash = rs.getString(1);
                    }
                }
            }
            if (storedHash == null) {
                setErrorAndForward(request, response, "The account does not exist");
                return;
            }

            String currentHash = sha256(currentPwd);
            if (!currentHash.equalsIgnoreCase(storedHash)) {
                setErrorAndForward(request, response, "The current password is incorrect");
                return;
            }

            String newHash = sha256(newPwd);
            if (newHash.equalsIgnoreCase(storedHash)) {
                setErrorAndForward(request, response, "The new password must not be the same as the current password");
                return;
            }
            String sqlUpdate = "UPDATE Users SET PasswordHash = ?, UpdatedAt = SYSUTCDATETIME() WHERE UserID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, newHash);
                ps.setInt(2, auth.getUserID());
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    setErrorAndForward(request, response, "Password change failed, please try again");
                    return;
                }
            }

            request.setAttribute("success", "Password changed successfully.");
            String scope = resolveScope(request);
            forwardToScopeJsp(request, response, scope);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorAndForward(request, response, "An error occurred, please try again");
        }
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean isStrongPassword(String s) {
        if (s == null || s.length() < 8) {
            return false;
        }
        boolean hasLower = false, hasUpper = false, hasDigit = false;
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLower && hasUpper && hasDigit;
    }

    private static String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("error", msg);
        String scope = resolveScope(request);
        forwardToScopeJsp(request, response, scope);

    }

    private static String resolveScope(HttpServletRequest request) {
        String p = request.getServletPath();
        if (p == null) {
            return "account";
        }
        if (p.startsWith("/manager/")) {
            return "manager";
        }
        if (p.startsWith("/staff/")) {
            return "staff";
        }
        if (p.startsWith("/volunteer/")) {
            return "volunteer";
        }
        return "account";
    }

    private void forwardToScopeJsp(HttpServletRequest request, HttpServletResponse response, String scope)
            throws ServletException, IOException {
        String candidate = "/" + scope + "/change_password.jsp";
        try {
            if (getServletContext().getResource(candidate) != null) {
                request.getRequestDispatcher(candidate).forward(request, response);
                return;
            }
        } catch (Exception ignored) {
        }
        // fallback order: manager -> account
        try {
            if (getServletContext().getResource("/manager/change_password.jsp") != null) {
                request.getRequestDispatcher("/manager/change_password.jsp").forward(request, response);
                return;
            }
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/account/change_password.jsp").forward(request, response);
    }

    private static boolean isScopeAllowed(String scope, HttpSession session) {
        if (session == null) {
            return false;
        }
        String role = (String) session.getAttribute("role");
        if (role == null) {
            return false;
        }
        switch (scope) {
            case "manager":
                return role.equalsIgnoreCase("Manager");
            case "staff":
                return role.equalsIgnoreCase("Staff") || role.equalsIgnoreCase("OrgStaff");
            case "volunteer":
                return role.equalsIgnoreCase("Volunteer");
            default:
                return true;
        }
    }
}
