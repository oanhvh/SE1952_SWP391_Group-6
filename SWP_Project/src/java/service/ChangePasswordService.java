/*
 * Service to handle change password logic.
 */
package service;

import dao.DBUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class ChangePasswordService {

    public boolean changePassword(int userId, String currentPwd, String newPwd, String confirmPwd, Map<String, String> errors) {
        if (isBlank(currentPwd) || isBlank(newPwd) || isBlank(confirmPwd)) {
            errors.put("error", "Please enter all required information");
            return false;
        }      

        if (!newPwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            errors.put("error", "Password must be at least 8 characters and include uppercase, lowercase, and a number");
            return false;
        }

        if (newPwd.equals(currentPwd)) {
            errors.put("error", "The new password must not be the same as the current password");
            return false;
        }
        
        if (!newPwd.equals(confirmPwd)) {
            errors.put("error", "The confirmation password does not match");
            return false;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            String sqlSelect = "SELECT PasswordHash FROM Users WHERE UserID = ?";
            String storedHash = null;
            try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        storedHash = rs.getString(1);
                    }
                }
            }
            if (storedHash == null) {
                errors.put("error", "The account does not exist");
                return false;
            }
            
            //Kiểm tra mật khẩu nhập có khớp với trong database không
            String currentHash = sha256(currentPwd);
            if (!currentHash.equalsIgnoreCase(storedHash)) {
                errors.put("error", "The current password is incorrect");
                return false;
            }
            
            //Hash mật khẩu mới
            String newHash = sha256(newPwd);
            
            String sqlUpdate = "UPDATE Users SET PasswordHash = ?, UpdatedAt = SYSUTCDATETIME() WHERE UserID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, newHash);
                ps.setInt(2, userId);
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    errors.put("error", "Password change failed, please try again");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "An error occurred, please try again");
            return false;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
