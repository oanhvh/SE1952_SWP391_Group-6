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
import java.util.Map;
import java.util.HashMap;
import service.RegisterService;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Register.jsp").forward(request, response);
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

        Map<String, String> errors = new java.util.HashMap<>();
        boolean success = registerService.processRegistration(role, username, password, fullName, email, phone, dobStr, employeeCode, errors);
        if (success) {
            successForward(request, response, "Registration successful. Please proceed to login.");
        } else {
            request.setAttribute("errors", errors);
            Map<String, String> form = new HashMap<>();
            form.put("role", role != null ? role : "");
            form.put("username", username != null ? username : "");
            form.put("fullName", fullName != null ? fullName : "");
            form.put("email", email != null ? email : "");
            form.put("phone", phone != null ? phone : "");
            form.put("employeeCode", employeeCode != null ? employeeCode : "");
            form.put("dateOfBirth", dobStr != null ? dobStr : "");
            request.setAttribute("form", form);
            errorForward(request, response, "Registration failed. Please check the errors.");
        }
    }
    
    //loại bỏ khoảng trắng thừa
    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v != null ? v.trim() : null;
    }

    private void errorForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/Register.jsp").forward(request, response);
    }

    private void successForward(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException, ServletException {
        request.setAttribute("success", message);
        request.getRequestDispatcher("/register_success.jsp").forward(request, response);
    }
}
