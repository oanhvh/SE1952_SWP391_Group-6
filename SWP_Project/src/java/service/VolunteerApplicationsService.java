/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerApplicationsDao;
import dao.VolunteerDAO;
import entity.VolunteerApplications;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author DucNM
 */
public class VolunteerApplicationsService {

    private VolunteerApplicationsDao volunteerAppDAO = new VolunteerApplicationsDao();
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
