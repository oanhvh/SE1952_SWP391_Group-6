/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entity.ManagerEventView;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import service.ManagerEventService;

/**
 *
 * @author NHThanh
 */

@WebServlet(name = "ManagerEventListController", urlPatterns = {"/manager/events"})
public class ManagerEventListController extends HttpServlet {

    private final ManagerEventService service = new ManagerEventService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;
        // Kiểm tra xem user có phải Manager không ?
        if (role == null || !"Manager".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        Users user = (Users) session.getAttribute("authUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String name = trim(request.getParameter("name"));
        String status = trim(request.getParameter("status"));
        String startFrom = trim(request.getParameter("startFrom"));
        String startTo = trim(request.getParameter("startTo"));
        
        //Gọi ManagerEventService để lấy dữ liệu
        List<ManagerEventView> events = service.getEventsForManagerUser(
                user.getUserID(), name, status, startFrom, startTo);
        request.setAttribute("events", events);
        request.setAttribute("filterName", name);
        request.setAttribute("filterStatus", status);
        request.setAttribute("filterStartFrom", startFrom);
        request.setAttribute("filterStartTo", startTo);

        request.getRequestDispatcher("/manager/event_list.jsp").forward(request, response);
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }
}
