/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.StaffDAO;
import entity.Category;
import entity.Event;
import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.EventService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.Users;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
/**
 *
 * @author DucNM
 */
@WebServlet(name = "EventController", urlPatterns = {"/staff/event"})
public class EventController extends HttpServlet {

    private EventService eventService;
    private StaffDAO staffDAO;
    private CategoryDAO categoryDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        eventService = new EventService();
        staffDAO = new StaffDAO();
        categoryDAO = new CategoryDAO();
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
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteEvent(request, response);
                break;
            case "status":
                updateStatus(request, response);
                break;
            case "create":
                showCreateForm(request, response);
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
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "add":
                addEvent(request, response);
                break;
            case "update":
                updateEvent(request, response);
                break;
            default:
                response.sendRedirect("/staff/event?action=list");
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

    private void listEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        List<Event> eventList = eventService.getAllEvents();
        List<Event> eventList = eventService.getEventsByStatus("Active");
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher("/staff/listEvent.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = eventService.getEventById(id);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/staff/viewEvent.jsp").forward(request, response);
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
        request.getRequestDispatcher("/staff/updateEvent.jsp").forward(request, response);
    }

    private void addEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);

        // Fallback: derive userId from authUser if not explicitly set
        Integer userIdAttr = null;
        Object uidObj = session.getAttribute("userId");
        if (uidObj instanceof Integer) {
            userIdAttr = (Integer) uidObj;
        } else if (uidObj instanceof String) {
            try {
                userIdAttr = Integer.valueOf((String) uidObj);
            } catch (NumberFormatException ignore) {
            }
        }
        if (userIdAttr == null) {
            Users authUser = (Users) session.getAttribute("authUser");
            if (authUser != null) {
                session.setAttribute("userId", authUser.getUserID());
                userIdAttr = authUser.getUserID();
            }
        }
        if (userIdAttr == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Event event = null;
        try {
            int userId = userIdAttr;
            Integer staffId = staffDAO.getStaffIdByUserId(userId);
            Integer managerId = staffDAO.getManagerIdByUserId(userId);

            if (managerId == null || managerId <= 0) {
                throw new IllegalArgumentException("Your Staff record has no Manager assigned. Please contact Manager to be assigned before creating events.");
            }

            event = buildEventFromRequest(request, false);
            event.setCreatedByStaffID(staffId);
            event.setManagerID(managerId);
            event.setCreatedAt(LocalDateTime.now());

            eventService.addEvent(event);
            response.sendRedirect(request.getContextPath() + "/staff/event?action=list");

        } catch (IllegalArgumentException e) {
            List<Category> categoryList = categoryDAO.getAllCategory();
            request.setAttribute("categoryList", categoryList);
            if (event == null) {
                try {
                    event = buildEventFromRequest(request, false);
                } catch (Exception ignore) {
                }
            }
            request.setAttribute("event", event);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/staff/createEvent.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            List<Category> categoryList = categoryDAO.getAllCategory();
            request.setAttribute("categoryList", categoryList);
            if (event == null) {
                try {
                    event = buildEventFromRequest(request, false);
                } catch (Exception ignore) {
                }
            }
            request.setAttribute("event", event);
            request.setAttribute("error", "Create failed: " + e.getMessage());
            request.getRequestDispatcher("/staff/createEvent.jsp").forward(request, response);
        }
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            Event event = buildEventFromRequest(request, true);
            eventService.updateEvent(event);
            response.sendRedirect(request.getContextPath() + "/staff/event?action=list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/staff/updateEvent.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error while updating event");
            request.getRequestDispatcher("/staff/updateEvent.jsp").forward(request, response);
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

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
//        eventService.deleteEvent(id);
        eventService.updateEventStatus(id, "Inactive");
        response.sendRedirect(request.getContextPath() + "/staff/event?action=list");
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        eventService.updateEventStatus(id, status);
        response.sendRedirect(request.getContextPath() + "/staff/event?action=list");
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categoryList = categoryDAO.getAllCategory();
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/staff/createEvent.jsp").forward(request, response);
    }
}
