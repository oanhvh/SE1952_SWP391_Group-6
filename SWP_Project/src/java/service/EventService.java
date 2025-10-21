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
    // VALIDATION
    // =============================
    public Map<String, String> validateEvent(Event event) {
        Map<String, String> errors = new HashMap<>();

        if (event.getEventName() == null || event.getEventName().trim().isEmpty()) {
            errors.put("eventName", "Tên sự kiện không được để trống");
        } else if (event.getEventName().length() > 200) {
            errors.put("eventName", "Tên sự kiện quá dài (tối đa 200 ký tự)");
        }

        if (event.getLocation() == null || event.getLocation().trim().isEmpty()) {
            errors.put("location", "Địa điểm không được để trống");
        }

        if (event.getStartDate() == null || event.getEndDate() == null) {
            errors.put("date", "Vui lòng nhập đầy đủ thời gian bắt đầu và kết thúc");
        } else if (event.getEndDate().isBefore(event.getStartDate())) {
            errors.put("date", "Thời gian kết thúc phải sau thời gian bắt đầu");
        } else if (event.getStartDate().isBefore(LocalDateTime.now())) {
            errors.put("date", "Thời gian bắt đầu không được trong quá khứ");
        }

        Event existing = eventDAO.getEventByNameAndDateAndLocation(
                event.getEventName(), event.getStartDate(), event.getLocation());
        if (existing != null && (event.getEventID() == 0 || existing.getEventID() != event.getEventID())) {
            errors.put("eventName", "Sự kiện trùng với một sự kiện đã tồn tại (tên, ngày và địa điểm).");
        }

        if (event.getCapacity() <= 0) {
            errors.put("capacity", "Sức chứa phải lớn hơn 0");
        }

        if (event.getStatus() == null || event.getStatus().trim().isEmpty()) {
            errors.put("status", "Trạng thái không được để trống");
        } else {
            List<String> validStatuses = Arrays.asList("Public", "Private");
            if (!validStatuses.contains(event.getStatus())) {
                errors.put("status", "Trạng thái không hợp lệ");
            }
        }

        if (event.getImage() != null && !event.getImage().trim().isEmpty()) {
            String lower = event.getImage().toLowerCase();
            if (!(lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png"))) {
                errors.put("image", "Ảnh chỉ chấp nhận định dạng JPG, JPEG hoặc PNG");
            }
        }

        if (event.getCategoryID() <= 0) {
            errors.put("category", "Vui lòng chọn thể loại hợp lệ");
        }

        if (event.getOrg() == null || event.getOrg().getOrgID() <= 0) {
            errors.put("org", "Tổ chức không hợp lệ");
        }

        if (event.getCreatedByStaff() == null || event.getCreatedByStaff().getStaffID() <= 0) {
            errors.put("staff", "Nhân viên tạo sự kiện không hợp lệ");
        }

        return errors;
    }

    // =============================
    // CRUD
    // =============================
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Event getEventByID(int id) {
        return eventDAO.getEventByID(id);
    }

    public boolean addEvent(Event event, Map<String, String> errors) {
        errors.putAll(validateEvent(event));
        if (errors.isEmpty()) {
            eventDAO.addEvent(event);
            return true;
        }
        return false;
    }
//    public void addEvent(Event event) {
//        eventDAO.addEvent(event);
//    }

    public boolean updateEvent(Event event, Map<String, String> errors) {
        errors.putAll(validateEvent(event));
        if (errors.isEmpty()) {
            eventDAO.updateEvent(event);
            return true;
        }
        return false;
    }
//    public void updateEvent(Event event) {
//        eventDAO.updateEvent(event);
//    }

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
