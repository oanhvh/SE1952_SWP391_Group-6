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
    
    //kiểm tra mã hợp lệ
    public EmployeeCodesDAO.CodeInfo validateEmployeeCode(Connection conn, String employeeCode) throws Exception {
        if (employeeCode == null || employeeCode.trim().isEmpty()) {
            return null;
        }
        String normalized = employeeCode.trim();
        return codesDAO.getValidCodeInfo(conn, normalized);
    }
}
    