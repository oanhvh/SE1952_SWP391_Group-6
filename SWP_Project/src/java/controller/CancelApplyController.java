package controller;

import dao.VolunteerApplicationsDAO;
import entity.Users;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CancelApplyController")
public class CancelApplyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
        boolean canceled = dao.cancelApplication(applicationId);

        if (canceled) {
            session.setAttribute("cancelMsg", "✅ Hủy apply thành công!");
        } else {
            session.setAttribute("cancelMsg", "⚠️ Chỉ có thể hủy khi trạng thái là 'Pending'.");
        }

        response.sendRedirect("ApplyEventController");
    }
}
