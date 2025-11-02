/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EventDAO;
import entity.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author DucNM
 */
public class EventService {

    private EventDAO eventDAO;

    public EventService() {
        eventDAO = new EventDAO();
    }

    // =============================
    // CRUD
    // =============================
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Event getEventById(int eventID) {
        if (eventID <= 0) {
            throw new IllegalArgumentException("Invalid Event ID");
        }
        return eventDAO.getEventById(eventID);
    }

    public void addEvent(Event event) {
        validateEvent(event, true);
        eventDAO.addEvent(event);
    }

    public void updateEvent(Event event) {
        if (event == null || event.getEventID() <= 0) {
            throw new IllegalArgumentException("Invalid event data");
        }
        validateEvent(event, false);
        eventDAO.updateEvent(event);
    }

    public void deleteEvent(int eventID) {
        if (eventID <= 0) {
            throw new IllegalArgumentException("Invalid Event ID");
        }
        eventDAO.deleteEvent(eventID);
    }

    public void updateEventStatus(int eventID, String status) {
        if (eventID <= 0) {
            throw new IllegalArgumentException("Invalid Event ID");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        eventDAO.updateEventStatus(eventID, status);
    }

    public List<Event> getEventsByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        return eventDAO.getEventsByStatus(status);
    }

    // =============================
    // VALIDATION
    // =============================
     private void validateEvent(Event event, boolean isNew) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        if (event.getManagerID() <= 0) {
            throw new IllegalArgumentException("Manager ID must be valid");
        }

        if (event.getEventName() == null || event.getEventName().isBlank()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        if (event.getDescription() == null || event.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (event.getLocation() == null || event.getLocation().isBlank()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }

        if (event.getStartDate() == null || event.getEndDate() == null) {
            throw new IllegalArgumentException("Start and End dates are required");
        }

        if (event.getEndDate().isBefore(event.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        if (event.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (event.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }

        if (event.getCategoryID() <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }

        if (event.getImage() == null || event.getImage().isBlank()) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }

        if (event.getStatus() == null || event.getStatus().isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        if (isNew && event.getCreatedAt() == null) {
            event.setCreatedAt(LocalDateTime.now());
        }
    }
}
