/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ManagerDAO;
import dao.StaffDAO;
import entity.Category;
import entity.Event;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import service.EventService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "DeniedEventController", urlPatterns = {"/staff/denEvent"})
public class DeniedEventController extends HttpServlet {

    private EventService eventService;
    private CategoryDAO categoryDAO;
    private StaffDAO staffDAO;
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
            action = "";
        }
        switch (action) {
            case "detail":
                showDenDetail(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listDenEvents(request, response);
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
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "update":
                updateEvent(request, response);
                break;
            default:
                response.sendRedirect("/staff/denEvent?action=list");
                break;
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

    private void listDenEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Event> eventList = eventService.getEventsByStatus("Cancelled");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/staff/listDenEvent.jsp").forward(request, response);
    }

    private void showDenDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
//        request.setAttribute("event", event);
        if (event != null) {
            Category category = categoryDAO.getCategoryById(event.getCategoryID());
            String categoryName = (category != null) ? category.getCategoryName() : "Unknown";

            String staffName = staffDAO.getUserNameByStaffId(event.getCreatedByStaffID());
            String managerName = managerDAO.getFullNameByManagerId(event.getManagerID());

            request.setAttribute("event", event);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("staffName", staffName);
            request.setAttribute("managerName", managerName);
        }
        request.getRequestDispatcher("/staff/viewDenEvent.jsp").forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
        List<Category> categoryList = categoryDAO.getAllCategory();

        String formattedCreatedAt = "";
        if (event.getCreatedAt() != null) {
            formattedCreatedAt = event.getCreatedAt().format(FORMATTER);
        }

        request.setAttribute("event", event);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("formattedCreatedAt", formattedCreatedAt);
        request.getRequestDispatcher("/staff/updateDenEvent.jsp").forward(request, response);
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Event event = null;
        List<Category> categoryList = categoryDAO.getAllCategory();
        request.setAttribute("categoryList", categoryList);

        try {
            int eventId = Integer.parseInt(request.getParameter("eventID"));
            Event existingEvent = eventService.getEventById(eventId);

            if (existingEvent == null) {
                throw new IllegalArgumentException("Event not found");
            }

            // Build event từ request
            event = buildEventFromRequest(request, true);

            // GIỮ LẠI các giá trị không thay đổi từ DB
            event.setCreatedByStaffID(existingEvent.getCreatedByStaffID());
            event.setManagerID(existingEvent.getManagerID());

            // Nếu createdAt không có trong request, giữ lại giá trị cũ
            if (event.getCreatedAt() == null) {
                event.setCreatedAt(existingEvent.getCreatedAt());
            }

            boolean changed = false;
            if (!event.getEventName().equals(existingEvent.getEventName())
                    || !event.getDescription().equals(existingEvent.getDescription())
                    || !event.getLocation().equals(existingEvent.getLocation())
                    || !event.getStartDate().equals(existingEvent.getStartDate())
                    || !event.getEndDate().equals(existingEvent.getEndDate())
                    || event.getCapacity() != existingEvent.getCapacity()
                    || event.getCategoryID() != existingEvent.getCategoryID()
                    || !event.getImage().equals(existingEvent.getImage())) {
                changed = true;
            }

            if (changed) {
                event.setStatus("Pending"); // có thay đổi → Pending
            } else {
                event.setStatus(existingEvent.getStatus()); // giữ nguyên
            }

            eventService.updateEvent(event);
            response.sendRedirect(request.getContextPath() + "/staff/denEvent?action=list");
        } catch (IllegalArgumentException e) {
            // Nếu build thất bại, lấy event cũ từ DB
            if (event == null || event.getEventID() == 0) {
                try {
                    int eventId = Integer.parseInt(request.getParameter("eventID"));
                    event = eventService.getEventById(eventId);
                } catch (Exception ex) {
                    event = new Event();
                }
            }

            // Format lại createdAt để hiển thị
            String formattedCreatedAt = "";
            if (event != null && event.getCreatedAt() != null) {
                formattedCreatedAt = event.getCreatedAt().format(FORMATTER);
            }

            request.setAttribute("event", event);
            request.setAttribute("formattedCreatedAt", formattedCreatedAt);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/staff/updateDenEvent.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();

            // Tương tự xử lý cho exception khác
            if (event == null || event.getEventID() == 0) {
                try {
                    int eventId = Integer.parseInt(request.getParameter("eventID"));
                    event = eventService.getEventById(eventId);
                } catch (Exception ex) {
                    event = new Event();
                }
            }

            String formattedCreatedAt = "";
            if (event != null && event.getCreatedAt() != null) {
                formattedCreatedAt = event.getCreatedAt().format(FORMATTER);
            }

            request.setAttribute("event", event);
            request.setAttribute("formattedCreatedAt", formattedCreatedAt);
            request.setAttribute("error", "Unexpected error while updating event: " + e.getMessage());
            request.getRequestDispatcher("/staff/updateDenEvent.jsp").forward(request, response);
        }
    }

    private Event buildEventFromRequest(HttpServletRequest request, boolean isUpdate) throws ServletException, IOException {
        Event event = new Event();
        if (isUpdate) {
            event.setEventID(Integer.parseInt(request.getParameter("eventID")));
        }

        event.setEventName(request.getParameter("eventName"));
        event.setDescription(request.getParameter("description"));
        event.setLocation(request.getParameter("location"));

        String startRaw = request.getParameter("startDate");
        String endRaw = request.getParameter("endDate");
        if (startRaw == null || startRaw.isBlank()) {
            Part p = request.getPart("startDate");
            if (p != null) {
                byte[] bytes = p.getInputStream().readAllBytes();
                startRaw = new String(bytes, java.nio.charset.StandardCharsets.UTF_8).trim();
            }
        }
        if (endRaw == null || endRaw.isBlank()) {
            Part p = request.getPart("endDate");
            if (p != null) {
                byte[] bytes = p.getInputStream().readAllBytes();
                endRaw = new String(bytes, java.nio.charset.StandardCharsets.UTF_8).trim();
            }
        }
        if (startRaw == null || startRaw.isBlank()) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (endRaw == null || endRaw.isBlank()) {
            throw new IllegalArgumentException("End date is required");
        }
        LocalDateTime startDt;
        LocalDateTime endDt;
        if (startRaw.contains("T")) {
            startDt = LocalDateTime.parse(startRaw);
        } else {
            startDt = LocalDateTime.parse(startRaw, FORMATTER);
        }
        if (endRaw.contains("T")) {
            endDt = LocalDateTime.parse(endRaw);
        } else {
            endDt = LocalDateTime.parse(endRaw, FORMATTER);
        }
        event.setStartDate(startDt);
        event.setEndDate(endDt);
        event.setStatus(request.getParameter("status"));
//        event.setStatus("Pending");
        event.setCapacity(Integer.parseInt(request.getParameter("capacity")));
//        event.setImage(request.getParameter("image"));
        event.setCategoryID(Integer.parseInt(request.getParameter("categoryID")));

        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

            String timeStamp = String.valueOf(System.currentTimeMillis());
            String fileExtension = "";
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = fileName.substring(dotIndex);
                fileName = fileName.substring(0, dotIndex) + "_" + timeStamp + fileExtension;
            } else {
                fileName = fileName + "_" + timeStamp;
            }

            String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String savePath = uploadPath + File.separator + fileName;
            filePart.write(savePath);

            event.setImage("images/" + fileName);
        } else if (isUpdate) {
            String oldImage = request.getParameter("oldImage");
            if (oldImage != null && !oldImage.isEmpty()) {
                event.setImage(oldImage);
            }
        }

        if (isUpdate && request.getParameter("createdAt") != null && !request.getParameter("createdAt").isBlank()) {
            String createdRaw = request.getParameter("createdAt");
            if (createdRaw.contains("T")) {
                event.setCreatedAt(LocalDateTime.parse(createdRaw));
            } else {
                event.setCreatedAt(LocalDateTime.parse(createdRaw, FORMATTER));
            }
        }
        return event;
    }
}
