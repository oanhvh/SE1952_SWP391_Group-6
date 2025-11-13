package controller;

import entity.Users;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import service.VolunteerApplicationsService;

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
            String cancelReason = request.getParameter("cancelReason");

            VolunteerApplicationsService service = new VolunteerApplicationsService();
            String message = service.cancelApplication(applicationId, cancelReason);

            session.setAttribute("cancelMsg", message);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cancelMsg", "❌ Error while processing request.");
        }

        response.sendRedirect("ApplyEventController");
    }
}
