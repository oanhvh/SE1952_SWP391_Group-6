package controller;

import dao.ParticipationDAO;
import entity.Participation;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/participations")
public class ParticipationListServlet extends HttpServlet {

    private final ParticipationDAO dao = new ParticipationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageIndex = 1, pageSize = 5;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) pageIndex = Integer.parseInt(pageParam);
        } catch (NumberFormatException ignored) {}

        // ===== LẤY DỮ LIỆU CHO DROPDOWN =====
        request.setAttribute("eventNames", dao.getAllEventNames());
        request.setAttribute("statuses", dao.getAllStatuses());
        request.setAttribute("approverNames", dao.getAllApproverNames());

        // ===== LẤY THAM SỐ SEARCH =====
        String volunteerName = request.getParameter("volunteerName");
        String eventName = request.getParameter("eventName");
        String status = request.getParameter("status");
        String approverName = request.getParameter("approverName");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        List<Participation> participations;

        boolean hasFilter =
                (volunteerName != null && !volunteerName.isEmpty()) ||
                (eventName != null && !eventName.isEmpty()) ||
                (status != null && !status.isEmpty()) ||
                (approverName != null && !approverName.isEmpty()) ||
                ((fromDate != null && !fromDate.isEmpty()) && (toDate != null && !toDate.isEmpty()));

        if (hasFilter) {
            participations = dao.searchByFilters(volunteerName, eventName, status, approverName, fromDate, toDate);
        } else {
            participations = dao.getAllParticipations(pageIndex, pageSize);
        }

        int totalRecords = dao.countAllParticipations();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("participations", participations);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/views/participation/participation_list.jsp").forward(request, response);
    }
}
