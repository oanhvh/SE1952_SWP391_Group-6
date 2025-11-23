/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.DBUtils;
import dao.ManagerDAO;
import dao.NotificationsDAO;
import dao.StaffDAO;
import entity.Category;
import entity.Event;
import entity.Manager;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import service.EventService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "EventManagerController", urlPatterns = {"/manager/event"})
public class EventManagerController extends HttpServlet {

    private EventService eventService;
    private StaffDAO staffDAO;
    private CategoryDAO categoryDAO;
    private ManagerDAO managerDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        staffDAO = new StaffDAO();
        categoryDAO = new CategoryDAO();
        managerDAO = new ManagerDAO();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "detail":
                showDetail(request, response);
                break;
            default:
                listEvents(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/manager/event?action=list");
            return;
        }

        switch (action) {
            case "approve":
                approveEvent(request, response);
                break;
            case "deny":
                denyEvent(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/manager/event?action=list");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // Lấy managerId từ session; nếu chưa có thì tra từ DB dựa vào authUser và lưu lại (Hàm đảm bảo mọi hoạt động đều gắn với đúng Manager hiện tại) (Thanhcocodo)
    private Integer resolveManagerId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session is missing");
            return null;
        }

        Integer managerId = (Integer) session.getAttribute("managerId");
        if (managerId != null && managerId > 0) {
            return managerId;
        }

        Users auth = (Users) session.getAttribute("authUser");
        if (auth == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return null;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            ManagerDAO mdao = new ManagerDAO();
            managerId = mdao.getManagerIdByUserId(conn, auth.getUserID());
            if (managerId == null || managerId <= 0) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager profile not found");
                return null;
            }
            session.setAttribute("managerId", managerId);
            return managerId;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to resolve managerId");
            return null;
        }
    }
       
    private void listEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer managerId = resolveManagerId(request, response);
        if (managerId == null) {
            return;
        }

        List<Event> eventList = eventService.getEventsByManagerAndStatus(managerId, "Pending");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/manager/listEvent.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
        if (event != null) {
            Category category = categoryDAO.getCategoryById(event.getCategoryID());
            String categoryName = (category != null) ? category.getCategoryName() : "Unknown";

            String staffName = staffDAO.getUserNameByStaffId(event.getCreatedByStaffID());

            String managerName = null;
            String managerContact = null;
            String managerAddress = null;
            if (event.getManagerID() > 0) {
                Manager manager = managerDAO.getManagerById(event.getManagerID());
                if (manager != null) {
                    managerName = manager.getManagerName();
                    managerContact = manager.getContactInfo();
                    managerAddress = manager.getAddress();
                }
            }

            request.setAttribute("event", event);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("staffName", staffName);
            request.setAttribute("managerName", managerName);
            request.setAttribute("managerContact", managerContact);
            request.setAttribute("managerAddress", managerAddress);
        }

        request.getRequestDispatcher("/manager/viewEvent.jsp").forward(request, response);
    }

    private void approveEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/manager/event?action=list&error=Event not found");
            return;
        }

        eventService.updateEventStatus(id, "Active");

        // Gửi thông báo cho Staff khi event được duyệt
        if (event.getCreatedByStaffID() != null) {
            int staffId = event.getCreatedByStaffID();
            Integer managerId = (Integer) request.getSession().getAttribute("managerId");

            if (managerId != null && managerId > 0) {
                NotificationsDAO notificationsDAO = new NotificationsDAO();
                String type = "EventApproved";
                String title = "Event Approved";
                String message = "Your event has been approved and is now active.";

                notificationsDAO.addNotificationForStaff(
                        staffId,
                        managerId,
                        type,
                        title,
                        message,
                        id
                );
            }
        }

        response.sendRedirect(request.getContextPath() + "/manager/event?action=list");
    }

    private void denyEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String reason = request.getParameter("reason");

            Event event = eventService.getEventById(id);
            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/manager/event?action=list&error=Event not found");
                return;
            }

            // Nếu đã từng bị deny trước đó (denyCount >= 1) → coi như xoá hẳn khỏi REVIEW LIST
            if (event.getDenyCount() >= 1) {
                // Đảm bảo không còn nằm trong Pending
                eventService.updateEventStatus(id, "Cancelled");

                // Thử xóa cứng, nếu bị FK chặn thì chỉ log lỗi
                try {
                    eventService.deleteEvent(id);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (event.getCreatedByStaffID() != null) {
                    int staffId = event.getCreatedByStaffID();
                    Integer managerId = (Integer) request.getSession().getAttribute("managerId");

                    if (managerId != null && managerId > 0) {
                        NotificationsDAO notificationsDAO = new NotificationsDAO();
                        String finalMsg = (reason != null && !reason.trim().isEmpty())
                                ? "Your event has been permanently deleted. Reason: " + reason
                                : "Your event has been permanently deleted after multiple denials.";

                        notificationsDAO.addNotificationForStaff(
                                staffId,
                                managerId,
                                "EventDenied",
                                "Event Deleted",
                                finalMsg,
                                id
                        );
                    }
                }

                response.sendRedirect(request.getContextPath() + "/manager/event?action=list&success=Event deleted successfully");
                return;
            }

            // LẦN 1: tăng denyCount + chuyển sang Cancelled
            eventService.denyOnceAndCancel(id);

            if (event.getCreatedByStaffID() != null) {
                int staffId = event.getCreatedByStaffID();
                Integer managerId = (Integer) request.getSession().getAttribute("managerId");

                if (managerId == null || managerId <= 0) {
                    response.sendRedirect(request.getContextPath() + "/manager/event?action=list&error=Manager not found");
                    return;
                }

                NotificationsDAO notificationsDAO = new NotificationsDAO();
                String message = (reason != null && !reason.trim().isEmpty())
                        ? "Your event has been denied. Reason: " + reason
                        : "Your event has been denied. Please review and resubmit.";

                notificationsDAO.addNotificationForStaff(
                        staffId,
                        managerId,
                        "EventDenied",
                        "Event Denied",
                        message,
                        id
                );
            }

            response.sendRedirect(request.getContextPath() + "/manager/event?action=list&success=Event denied successfully");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/manager/event?action=list&error=Invalid event ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manager/event?action=list&error=An error occurred while processing your request");
        }
    }
}
