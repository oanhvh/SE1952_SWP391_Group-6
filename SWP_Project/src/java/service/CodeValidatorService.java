/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EmployeeCodesDAO;
import java.sql.Connection;

/**
 *
 * @author NHThanh
 */
public class CodeValidatorService {
    private final EmployeeCodesDAO codesDAO = new EmployeeCodesDAO();

    public EmployeeCodesDAO.CodeInfo validateEmployeeCode(Connection conn, String employeeCode) throws Exception {
        if (employeeCode == null || employeeCode.trim().isEmpty()) return null;
        return codesDAO.getValidCodeInfo(conn, employeeCode.trim());
    }
}
