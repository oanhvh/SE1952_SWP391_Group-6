package service;

import dao.VolunteerDAO;
import entity.Volunteer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class VolunteerService {
    private final VolunteerDAO volunteerDao = new VolunteerDAO();

    public void getAllVolunteers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String status = request.getParameter("status");

        List<Volunteer> volunteers;

        if ((name != null && !name.trim().isEmpty()) ||
                (phone != null && !phone.trim().isEmpty()) ||
                (email != null && !email.trim().isEmpty()) ||
                (status != null && !status.trim().isEmpty())) {
            volunteers = volunteerDao.searchVolunteers(name, phone, email, status);
        } else {
            volunteers = volunteerDao.getAllVolunteers();
        }

        request.setAttribute("volunteerList", volunteers);
        request.getRequestDispatcher("volunteerList.jsp").forward(request, response);
    }

    public void getVolunteerDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("volunteerID");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Volunteer volunteer = volunteerDao.getVolunteerById(id);
                if (volunteer != null) {
                    request.setAttribute("volunteer", volunteer);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("viewVolunteerDetail.jsp").forward(request, response);
    }

    public void updateVolunteerStatus(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        String volunteerIdParam = request.getParameter("volunteerID");
        String status = request.getParameter("status");
        boolean success = false;

        if (volunteerIdParam != null && status != null) {
            try {
                int volunteerID = Integer.parseInt(volunteerIdParam);
                success = volunteerDao.updateVolunteerStatus(volunteerID, status);

                if (success) {
                    request.setAttribute("message", "Volunteer status updated successfully.");
                } else {
                    request.setAttribute("error", "Failed to update volunteer status.");
                }

                // Reload the updated volunteer details
                Volunteer volunteer = volunteerDao.getVolunteerById(volunteerID);
                request.setAttribute("volunteer", volunteer);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid volunteer ID format.");
            }
        } else {
            request.setAttribute("error", "Missing volunteer ID or status.");
        }

         request.getRequestDispatcher("viewVolunteerDetail.jsp").forward(request, response);
    }
}
