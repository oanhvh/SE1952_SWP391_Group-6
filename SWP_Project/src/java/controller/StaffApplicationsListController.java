package controller;

import dao.VolunteerApplicationsDAO;
import dao.StaffDAO;
import entity.ApplicationReviewRow;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffApplicationsListController", urlPatterns = {"/staff/applications"})
public class StaffApplicationsListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object roleObj = (session != null) ? session.getAttribute("role") : null;
        String role = roleObj != null ? roleObj.toString() : null;
        if (role == null || !("Staff".equalsIgnoreCase(role))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        Users user = (Users) session.getAttribute("authUser");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //lấy tham số lọc trạng thái, mặc định là Pending
        String status = request.getParameter("status");
        if (status == null || status.isBlank()) {
            status = "Pending";
        }

        //xử lý và truyền dữ liệu từ Controller sang JSP
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        StaffDAO staffDAO = new StaffDAO();
        Integer managerId = staffDAO.getManagerIdByUserId(user.getUserID());
        if (managerId == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No manager scope");
            return;
        }
        List<ApplicationReviewRow> rows = dao.getApplicationsForReviewByStatus(status, managerId);
        request.setAttribute("rows", rows);
        request.setAttribute("filterStatus", status);
        request.getRequestDispatcher("/staff/applications_list.jsp").forward(request, response);
    }
}
