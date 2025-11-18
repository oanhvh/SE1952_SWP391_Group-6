/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerApplicationsDAO;
import dao.VolunteerDAO;
import entity.VolunteerApplications;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author DucNM
 */
public class VolunteerApplicationsService {

    private VolunteerApplicationsDAO volunteerAppDAO = new VolunteerApplicationsDAO();
    private VolunteerDAO volunteerDAO = new VolunteerDAO();

    // =============================
    // CRUD
    // =============================
    public List<VolunteerApplications> getAllVolunteerApplications() {
        return volunteerAppDAO.getAllVolunteerApplications();
    }

    public void addVolunteerApplication(VolunteerApplications app) {
        validateApplicationForAdd(app);
        volunteerAppDAO.addVolunteerApplication(app);
    }

    public void updateVolunteerApplication(VolunteerApplications app) {
        validateApplicationForUpdate(app);
        volunteerAppDAO.updateVolunteerApplication(app);
    }

    public void deleteVolunteerApplication(int applicationID) {
        validateID(applicationID, "Invalid ApplicationID.");
        volunteerAppDAO.deleteVolunteerApplication(applicationID);
    }

    public List<VolunteerApplications> getVolunteerApplicationsByStatus(String status) {
        validateStatus(status);
        return volunteerAppDAO.getVolunteerApplicationsByStatus(status);
    }

    public List<VolunteerApplications> getApplicationsByVolunteer(int volunteerID) {
        validateID(volunteerID, "Invalid VolunteerID.");
        return volunteerAppDAO.getApplicationsByVolunteer(volunteerID);
    }

    public void updateStatus(int applicationID, String status, Integer approvedByStaffID) {
        validateID(applicationID, "Invalid ApplicationID.");
        validateDecisionStatus(status);
        volunteerAppDAO.updateStatus(applicationID, status, approvedByStaffID);
    }
    
    public List<VolunteerApplications> getApplicationsByUserId(int userId) {
        return volunteerAppDAO.getApplicationsByUserId(userId);
    }

    public boolean hasAppliedForEvent(int userId, int eventId) {
        return volunteerAppDAO.hasAppliedForEvent(userId, eventId);
    }

    public boolean applyForEvent(int userId, int eventId, String motivation, String experience) {
        return volunteerAppDAO.applyEventByUserId(userId, eventId, motivation, experience);
    }
    
    public String cancelApplication(int applicationId, String cancelReason) {
        try {
            String result = volunteerAppDAO.cancelApplication(applicationId, cancelReason);

            switch (result) {
                case "PENDING_CANCELLED":
                    return "✅ Cancel application (pending).";
                case "APPROVED_CANCELLED":
                    return "✅ Cancel application (approved).";
                case "REJECTED":
                    return "⚠️ Application rejected.";
                case "NOT_FOUND":
                    return "❌ Application not found.";
                default:
                    return "❌ Cannot cancel application.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error while cancelling application.";
        }
    }
    
 public List<VolunteerApplications> getTodayEventsByUserId(int userId) throws Exception {
        return volunteerAppDAO.getTodayEventsByUserId(userId);
    }
    
    // Lấy tất cả ứng dụng đã hoàn thành của user
    public List<VolunteerApplications> getCompletedApplicationsByUserId(int userId) throws Exception {
        return volunteerAppDAO.getCompletedApplicationsByUserId(userId);
    }
    
    public List<VolunteerApplications> filterByEventName(List<VolunteerApplications> applications, String eventName) {
    if (eventName == null || eventName.isEmpty()) return applications;

    return applications.stream()
            .filter(app -> app.getEvent().getEventName().toLowerCase().contains(eventName.toLowerCase()))
            .collect(Collectors.toList());
    }  
    
    // Lọc theo địa điểm
    public List<VolunteerApplications> filterByLocation(List<VolunteerApplications> applications, String location) {
        if (location == null || location.isEmpty()) return applications;

        return applications.stream()
                .filter(app -> app.getEvent().getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    // Lọc theo khoảng ngày
    public List<VolunteerApplications> filterByDate(List<VolunteerApplications> applications,
                                                    LocalDate startDateFilter,
                                                    LocalDate endDateFilter) {
        if (startDateFilter == null && endDateFilter == null) return applications;

        return applications.stream()
                .filter(app -> {
                    LocalDate eventDate = app.getEvent().getStartDate().toLocalDate();
                    if (startDateFilter != null && eventDate.isBefore(startDateFilter)) return false;
                    if (endDateFilter != null && eventDate.isAfter(endDateFilter)) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    public List<Integer> getAppliedEventIds(int userId) {
        return volunteerAppDAO.getAppliedEventIdsByUser(userId);
    }
    
    
    // =============================
    // VALIDATION
    // =============================
    private void validateApplicationForAdd(VolunteerApplications app) {
        if (app == null) {
            throw new IllegalArgumentException("Invalid information.");
        }
        validateID(app.getVolunteerID(), "Invalid VolunteerID.");
        validateID(app.getEventID(), "Invalid EventID.");
//        if (app.getStatus() == null || app.getStatus().isEmpty()) {
            app.setStatus("Pending");
//        }
        if (app.getApplicationDate() == null) {
            app.setApplicationDate(LocalDateTime.now());
        }
    }

    private void validateApplicationForUpdate(VolunteerApplications app) {
        if (app == null || app.getApplicationID() <= 0) {
            throw new IllegalArgumentException("Invalid ApplicationID.");
        }
    }

    private void validateStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Invalid status.");
        }
    }

    private void validateDecisionStatus(String status) {
        if (!"Approved".equalsIgnoreCase(status) && !"Rejected".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Status must be Approved or Rejected.");
        }
    }

    private void validateID(int id, String message) {
        if (id <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
