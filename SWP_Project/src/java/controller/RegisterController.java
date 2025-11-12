/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author NHThanh
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import service.RegisterService;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/AccountRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = param(request, "role");
        String username = param(request, "username");
        String password = param(request, "password");
        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");
        String employeeCode = param(request, "employeeCode");
        String dobStr = param(request, "dateOfBirth");

        LocalDate dob = null;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            errorForward(request, response, "Invalid date format (YYYY-MM-DD)");
            return;
        }

        Map<String, String> errors = registerService.validateRegistration(
                username, password, role, fullName, email, phone, dobStr, employeeCode);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            errorForward(request, response, "Please correct the errors and try again.");
            return;
        }

        boolean success = registerService.registerUser(
                role, username, password, fullName, email, phone, dob, employeeCode, errors);

        if (success) {
            successForward(request, response, "Registration successful. Please proceed to login.");
        } else {
            request.setAttribute("errors", errors);
            errorForward(request, response, "Registration failed. Please check the errors.");
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v != null ? v.trim() : null;
    }

    private void errorForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/AccountRegister.jsp").forward(request, response);
    }

    private void successForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException, ServletException {
        request.setAttribute("success", message);
        request.getRequestDispatcher("/register_success.jsp").forward(request, response);
    }
}
