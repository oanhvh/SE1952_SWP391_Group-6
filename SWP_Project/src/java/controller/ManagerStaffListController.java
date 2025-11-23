/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DBUtils;
import dao.ManagerDAO;
import dao.StaffDAO;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author NHThanh
 */
@WebServlet(name = "ManagerStaffListController", urlPatterns = {"/manager/staffs"})
public class ManagerStaffListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object roleObj = session != null ? session.getAttribute("role") : null;
        String role = roleObj != null ? roleObj.toString() : null;
        if (role == null || !"Manager".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        Users user = (Users) session.getAttribute("authUser");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String name = trim(request.getParameter("name"));
        String phone = trim(request.getParameter("phone"));
        String status = trim(request.getParameter("status"));
        
        // Từ user đăng nhập → lấy managerId
        try {
            ManagerDAO mdao = new ManagerDAO();
            Integer managerId;
            try (Connection conn = DBUtils.getConnection1()) {
                managerId = mdao.getManagerIdByUserId(conn, user.getUserID());
            }
            if (managerId == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No manager scope");
                return;
            }

            StaffDAO sdao = new StaffDAO();
            //Tìm kiếm và lọc staff
            List<Users> staffs = sdao.getStaffUsersByManager(managerId, name, phone, status);
            request.setAttribute("staffs", staffs);
            request.setAttribute("filterName", name);
            request.setAttribute("filterPhone", phone);
            request.setAttribute("filterStatus", status);
            request.getRequestDispatcher("/manager/staff_list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }
}
