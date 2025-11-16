/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author NHThanh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCodesDAO {

    public static class CodeInfo {

        public final int codeId;
        public final int managerId;
        public final String codeValue;
        public final boolean isUsed;
        public final Timestamp createdAt;
        public final Integer createdByUserId;
        public final String creatorName;

        public CodeInfo(int codeId, int managerId, String codeValue, boolean isUsed, Timestamp createdAt, Integer createdByUserId, String creatorName) {
            this.codeId = codeId;
            this.managerId = managerId;
            this.codeValue = codeValue;
            this.isUsed = isUsed;
            this.createdAt = createdAt;
            this.createdByUserId = createdByUserId;
            this.creatorName = creatorName;
        }
    }

    //phân trang danh sách code theo ID đã tạo 
    public List<CodeInfo> listCodesByManager(Connection conn, int managerId, int page, int pageSize) throws Exception {
        if (page <= 0) {
            page = 1;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }
        int offset = (page - 1) * pageSize;
        String sql = "SELECT ec.CodeID, ec.ManagerID, ec.CodeValue, ec.IsUsed, ec.CreatedAt, ec.CreatedByUserID, COALESCE(u.FullName, u.Username) AS CreatorName "
                + "FROM EmployeeCodes ec "
                + "LEFT JOIN Users u ON u.UserID = ec.CreatedByUserID " //Kết nối bảng EmployeeCodes với bảng Users để lấy tên người tạo code
                + "WHERE ec.ManagerID = ? "
                + "ORDER BY ec.CodeID DESC " //AND ec.IsUsed = 1  //Code mới nhất lên đầu
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);    // ManagerID
            ps.setInt(2, offset);       // Số bản ghi bỏ qua
            ps.setInt(3, pageSize);     // Số bản ghi lấy
            List<CodeInfo> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CodeInfo(
                            rs.getInt("CodeID"),
                            rs.getInt("ManagerID"),
                            rs.getString("CodeValue"),
                            rs.getBoolean("IsUsed"),
                            rs.getTimestamp("CreatedAt"),
                            (Integer) rs.getObject("CreatedByUserID"),
                            rs.getString("CreatorName")
                    ));
                }
            }
            return list;
        }
    }
    
//    //Liệt kê tất cả code của tất cả Manager (phân trang)
//    public List<CodeInfo> listAllCodes(Connection conn, int page, int pageSize) throws Exception {
//        if (page <= 0) {
//            page = 1;
//        }
//        if (pageSize <= 0) {
//            pageSize = 20;
//        }
//        int offset = (page - 1) * pageSize;
//        String sql = "SELECT ec.CodeID, ec.ManagerID, ec.CodeValue, ec.IsUsed, ec.CreatedAt, ec.CreatedByUserID, COALESCE(u.FullName, u.Username) AS CreatorName "
//                + "FROM EmployeeCodes ec "
//                + "LEFT JOIN Users u ON u.UserID = ec.CreatedByUserID "
//                + "ORDER BY ec.CodeID DESC "
//                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, offset);
//            ps.setInt(2, pageSize);
//            List<CodeInfo> list = new ArrayList<>();
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    list.add(new CodeInfo(
//                            rs.getInt("CodeID"),
//                            rs.getInt("ManagerID"),
//                            rs.getString("CodeValue"),
//                            rs.getBoolean("IsUsed"),
//                            rs.getTimestamp("CreatedAt"),
//                            (Integer) rs.getObject("CreatedByUserID"),
//                            rs.getString("CreatorName")
//                    ));
//                }
//            }
//            return list;
//        }
//    }
//    
    //Xóa code, nhưng chỉ khi code thuộc về Manager này
    public int deleteCodeByManager(Connection conn, int managerId, int codeId) throws Exception {
        String sql = "DELETE FROM EmployeeCodes WHERE CodeID = ? AND ManagerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeId);
            ps.setInt(2, managerId);
            return ps.executeUpdate();
        }
    }

    //Tạo code mới
    public int createCode(Connection conn, int managerId, String codeValue, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO EmployeeCodes(ManagerID, CodeValue, IsUsed, CreatedAt, CreatedByUserID) VALUES(?, ?, 0, SYSUTCDATETIME(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //tạo codeID
            ps.setInt(1, managerId);
            ps.setString(2, codeValue); //tạo code
            if (createdByUserId == null) {
                ps.setNull(3, java.sql.Types.INTEGER); // Null nếu không có user
            } else {
                ps.setInt(3, createdByUserId);  // UserID người tạo code
            }
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Create code failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("No CodeID returned");
        }
    }

    //Đếm tổng số code theo Manager
    public int getCodesCountByManager(Connection conn, int managerId) throws Exception {
        String sql = "SELECT COUNT(*) FROM EmployeeCodes WHERE ManagerID = ?"; //AND IsUsed = 1
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    //Kiểm tra code có hợp lệ để đăng ký Staff
    public CodeInfo getValidCodeInfo(Connection conn, String codeValue) throws Exception {
        String sql = "SELECT CodeID, ManagerID, CodeValue, IsUsed, CreatedAt FROM EmployeeCodes "
                + "WHERE CodeValue = ? AND IsUsed = 0 "
                + "AND CreatedAt >= DATEADD(HOUR, -24, SYSUTCDATETIME())"; //hết hạn sau 24h kể từ thời điểm tạo
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codeValue);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CodeInfo(
                            rs.getInt("CodeID"),
                            rs.getInt("ManagerID"),
                            rs.getString("CodeValue"),
                            rs.getBoolean("IsUsed"),
                            rs.getTimestamp("CreatedAt"),
                            null,
                            null
                    );
                }
            }
        }
        return null;
    }

    //Đánh dấu mã đã dùng   
    public void markCodeUsed(Connection conn, int codeId) throws Exception {
        String sql = "UPDATE EmployeeCodes SET IsUsed = 1 WHERE CodeID = ?"; //đổi trạng thái thành đã dùng
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeId);
            ps.executeUpdate();
        }
    }
}
