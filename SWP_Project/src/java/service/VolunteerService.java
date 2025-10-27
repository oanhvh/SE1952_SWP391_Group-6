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
        String status = request.getParameter("status");
        String availability = request.getParameter("availability");

        List<Volunteer> volunteerList;
        if ((name != null && !name.trim().isEmpty()) ||
                (status != null && !status.trim().isEmpty()) ||
                (availability != null && !availability.trim().isEmpty())) {
            volunteerList = volunteerDao.searchVolunteers(name, status, availability);
        } else {
            volunteerList = volunteerDao.getAllVolunteers();
        }

        request.setAttribute("volunteerList", volunteerList);
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
}
