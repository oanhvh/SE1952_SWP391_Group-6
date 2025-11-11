package controller;

import dao.VolunteerApplicationsDAO;
import dao.StaffDAO;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StaffApplicationReviewController", urlPatterns = {"/staff/applications/review"})
public class StaffApplicationReviewController extends HttpServlet {

    private final VolunteerApplicationsDAO applicationsDAO = new VolunteerApplicationsDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Users user = (Users) session.getAttribute("authUser");
        String role = (String) session.getAttribute("role");
        if (role == null || !("Staff".equalsIgnoreCase(role))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String appIdStr = req.getParameter("applicationId");
        String action = req.getParameter("action"); // approve | reject
        String staffComment = req.getParameter("staffComment");

        //chuyển đổi String -> int (ID không phải số báo lỗi)
        int applicationId;
        try {
            applicationId = Integer.parseInt(appIdStr);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid applicationId");
            return;
        }

        //xác nhận approve hay reject
        boolean approve;
        if ("approve".equalsIgnoreCase(action)) {
            approve = true;
        } else if ("reject".equalsIgnoreCase(action)) {
            approve = false;
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        // Map UserID -> StaffID to satisfy FK ApprovedByStaffID
        StaffDAO staffDAO = new StaffDAO();
        Integer staffId = staffDAO.getStaffIdByUserId(user.getUserID());
        if (staffId == null) {
            resp.sendRedirect(req.getContextPath() + "/staff/applications?status=Pending&error=no_staff_id");
            return;
        }

        //Gọi DAO để cập nhật trạng thái đơn
        boolean ok = applicationsDAO.reviewApplication(applicationId, staffId, approve, staffComment);

        //Chuyển hướng về danh sách + thông báo
        String next = req.getContextPath() + "/staff/applications?status=Pending";
        next += ok ? "&msg=" + (approve ? "approved" : "rejected") : "&error=update_failed";
        resp.sendRedirect(next);
    }
}
