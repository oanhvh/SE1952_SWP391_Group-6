/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EventDAO;
import entity.Event;
import java.util.List;

/**
 *
 * @author Duc
 */
public class EventService {
    private EventDAO eventDAO;

    public EventService() {
        eventDAO = new EventDAO();
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Event getEventByID(int id) {
        return eventDAO.getEventByID(id);
    }

    public void addEvent(Event event) {
        eventDAO.addEvent(event);
    }

    public void updateEvent(Event event) {
        eventDAO.updateEvent(event);
    }

    public void deleteEvent(int id) {
        eventDAO.deleteEvent(id);
    }

    public void updateEventStatus(int id, String status) {
        eventDAO.updateEventStatus(id, status);
    }

    public List<Event> getEventsByStatus(String status) {
        return eventDAO.getEventsByStatus(status);
    }
}
