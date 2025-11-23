/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EventDAO;
import dao.ManagerDAO;
import dao.DBUtils;
import entity.ManagerEventView;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author NHThanh
 */
public class ManagerEventService {

    private final EventDAO eventDAO = new EventDAO();
    private final ManagerDAO managerDAO = new ManagerDAO();

    //Nhận thông tin từ Controller
    public List<ManagerEventView> getEventsForManagerUser(int managerUserId,
            String nameKeyword,
            String status,
            String startFromStr,
            String startToStr) {
        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = managerDAO.getManagerIdByUserId(conn, managerUserId);
            if (managerId == null) {
                return Collections.emptyList();
            }

            LocalDate startFrom = parseDate(startFromStr);
            LocalDate startTo = parseDate(startToStr);

            return eventDAO.getEventsByManager(managerId, nameKeyword, status, startFrom, startTo);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //Sử lí nếu ngày nhập không hợp lệ
    private LocalDate parseDate(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(s.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
