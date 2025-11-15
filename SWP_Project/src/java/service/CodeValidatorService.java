/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EmployeeCodesDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;

/**
 *
 * @author NHThanh
 */
public class CodeValidatorService {

    private final EmployeeCodesDAO codesDAO = new EmployeeCodesDAO();
    
    //kiểm tra mã hợp lệ (Thanhcocodo)
    public EmployeeCodesDAO.CodeInfo validateEmployeeCode(Connection conn, String employeeCode) throws Exception {
        if (employeeCode == null || employeeCode.trim().isEmpty()) {
            return null;
        }
        String normalized = employeeCode.trim();
        return codesDAO.getValidCodeInfo(conn, normalized);
    }

    public static boolean isNotAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return true;

        String role = (String) session.getAttribute("role");
        return role == null || !role.equalsIgnoreCase("Admin");
    }
}
    