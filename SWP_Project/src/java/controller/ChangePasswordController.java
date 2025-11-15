/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author NHThanh
 */
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import service.ChangePasswordService;

@WebServlet(name = "ChangePasswordController", urlPatterns = {
    "/manager/change-password",
    "/staff/change-password",
    "/volunteer/change-password",
    "/admin/change-password",})
public class ChangePasswordController extends HttpServlet {

    private final ChangePasswordService changePasswordService = new ChangePasswordService();

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

        Map<String, String> errors = new HashMap<>();
        boolean ok = changePasswordService.changePassword(auth.getUserID(), currentPwd, newPwd, confirmPwd, errors);
        if (!ok) {
            setErrorAndForward(request, response, errors.getOrDefault("error", "An error occurred, please try again"));
            return;
        }
        request.setAttribute("success", "Password changed successfully.");

        String scope = resolveScope(request);
        forwardToScopeJsp(request, response, scope);
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }

    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("error", msg);
        String scope = resolveScope(request);
        forwardToScopeJsp(request, response, scope);
    }

    private static String resolveScope(HttpServletRequest request) {
        String p = request.getServletPath();
        if (p.startsWith("/admin/")) {
            return "admin";
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
        request.getRequestDispatcher(candidate).forward(request, response);
    }
}
