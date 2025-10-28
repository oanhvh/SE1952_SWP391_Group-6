/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerApplicationsDAO;
import dao.VolunteerDAO;
import entity.VolunteerApplications;
import java.time.LocalDateTime;
import java.util.List;

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
        validateID(applicationID, "Application ID không hợp lệ để xóa.");
        volunteerAppDAO.deleteVolunteerApplication(applicationID);
    }

    public List<VolunteerApplications> getVolunteerApplicationsByStatus(String status) {
        validateStatus(status);
        return volunteerAppDAO.getVolunteerApplicationsByStatus(status);
    }

    public List<VolunteerApplications> getApplicationsByVolunteer(int volunteerID) {
        validateID(volunteerID, "Volunteer ID không hợp lệ.");
        return volunteerAppDAO.getApplicationsByVolunteer(volunteerID);
    }

    public void updateStatus(int applicationID, String status, Integer approvedByStaffID) {
        validateID(applicationID, "Application ID không hợp lệ.");
        validateDecisionStatus(status);
        volunteerAppDAO.updateStatus(applicationID, status, approvedByStaffID);
    }

    // =============================
    // VALIDATION
    // =============================
    private void validateApplicationForAdd(VolunteerApplications app) {
        if (app == null) {
            throw new IllegalArgumentException("Dữ liệu ứng tuyển không hợp lệ.");
        }
        validateID(app.getVolunteerID(), "Volunteer ID không hợp lệ.");
        validateID(app.getEventID(), "Event ID không hợp lệ.");
//        if (app.getStatus() == null || app.getStatus().isEmpty()) {
            app.setStatus("Pending");
//        }
        if (app.getApplicationDate() == null) {
            app.setApplicationDate(LocalDateTime.now());
        }
    }

    private void validateApplicationForUpdate(VolunteerApplications app) {
        if (app == null || app.getApplicationID() <= 0) {
            throw new IllegalArgumentException("Application ID không hợp lệ khi cập nhật.");
        }
    }

    private void validateStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }
    }

    private void validateDecisionStatus(String status) {
        if (!"Approved".equalsIgnoreCase(status) && !"Rejected".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Trạng thái phải là Approved hoặc Rejected.");
        }
    }

    private void validateID(int id, String message) {
        if (id <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
