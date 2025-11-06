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

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Users authUser = (Users) session.getAttribute("authUser");

        if (authUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            String cancelReason = request.getParameter("cancelReason"); // có thể null nếu Pending

            VolunteerApplicationsDAO dao = new VolunteerApplicationsDAO();
            String result = dao.cancelApplication(applicationId, cancelReason);

            switch (result) {
                case "PENDING_CANCELLED" -> 
                    session.setAttribute("cancelMsg", "✅ Cancel application");
                case "APPROVED_CANCELLED" -> 
                    session.setAttribute("cancelMsg", "✅ Cancel application.");
                case "REJECTED" -> 
                    session.setAttribute("cancelMsg", "⚠️ Rejected.");
                case "NOT_FOUND" -> 
                    session.setAttribute("cancelMsg", "❌ Can not found");
                default -> 
                    session.setAttribute("cancelMsg", "❌ Can not cancel");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cancelMsg", "❌ Error.");
        }

        response.sendRedirect("ApplyEventController");
    }
}
