/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DBUtils;
import dao.ManagerDAO;
import dao.StaffDAO;
import dao.UserDao;
import java.sql.Connection;

/**
 *
 * @author NHThanh
 */
public class StaffService {

    public static class UpdateStatusResult {

        public final boolean success;
        public final String message;

        public UpdateStatusResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static UpdateStatusResult ok() {
            return new UpdateStatusResult(true, null);
        }

        public static UpdateStatusResult fail(String msg) {
            return new UpdateStatusResult(false, msg);
        }
    }

    public UpdateStatusResult updateStaffStatus(int managerUserId, int targetUserId, String newStatus) {
        if (!"Active".equals(newStatus) && !"Inactive".equals(newStatus)) {
            return UpdateStatusResult.fail("Invalid status");
        }
        try (Connection conn = DBUtils.getConnection1()) {
            ManagerDAO mdao = new ManagerDAO();
            Integer managerIdOfCurrent = mdao.getManagerIdByUserId(conn, managerUserId);
            if (managerIdOfCurrent == null) {
                return UpdateStatusResult.fail("No manager scope");
            }
            StaffDAO sdao = new StaffDAO();
            Integer managerIdOfTarget = sdao.getManagerIdByUserId(targetUserId);
            if (managerIdOfTarget == null || !managerIdOfCurrent.equals(managerIdOfTarget)) {
                return UpdateStatusResult.fail("Target not in your scope");
            }
            UserDao udao = new UserDao();
            boolean ok = udao.updateUserStatus(conn, targetUserId, newStatus);
            return ok ? UpdateStatusResult.ok() : UpdateStatusResult.fail("Update failed");
        } catch (Exception ex) {
            return UpdateStatusResult.fail("Server error");
        }
    }
}
