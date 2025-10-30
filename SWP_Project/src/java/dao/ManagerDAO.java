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
import entity.Manager;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {

    public int createManager(Connection conn, int userId, String managerName, String contactInfo, String address, Integer createdByUserId) throws Exception {
        String sql = "INSERT INTO Manager(UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID) "
                + "VALUES(?, ?, ?, ?, CAST(SYSUTCDATETIME() AS DATE), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            if (userId <= 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, userId);
            }
            ps.setString(2, managerName);
            ps.setString(3, contactInfo);
            ps.setString(4, address);
            if (createdByUserId == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, createdByUserId);
            }
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Create manager failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("No ManagerID returned");
        }
    }

    //tìm managerID dựa theo userID
    public Integer getManagerIdByUserId(Connection conn, int userId) throws Exception {
        String sql = "SELECT ManagerID FROM Manager WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ManagerID");
                }
            }
        }
        return null;
    }

    public List<Manager> getManagersByPage(int page) {
        List<Manager> list = new ArrayList<>();
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        String sql = """
            SELECT ManagerID, UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID
            FROM Manager
            ORDER BY ManagerID
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Manager m = extractManagerFromResultSet(rs);
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalManagerCount() {
        String sql = "SELECT COUNT(*) FROM Manager";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalPages(int pageSize) {
        int total = getTotalManagerCount();
        return (int) Math.ceil((double) total / pageSize);
    }

    public Manager getManagerById(int id) {
        String sql = """
            SELECT ManagerID, UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID
            FROM Manager
            WHERE ManagerID = ?
            """;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractManagerFromResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertManager(Manager m) {
        String sql = """
            INSERT INTO Manager (UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (m.getUserId() != null) {
                ps.setInt(1, m.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, m.getManagerName());
            ps.setString(3, m.getContactInfo());
            ps.setString(4, m.getAddress());

            if (m.getRegistrationDate() != null) {
                ps.setDate(5, Date.valueOf(m.getRegistrationDate()));
            } else {
                ps.setDate(5, Date.valueOf(LocalDate.now()));
            }

            if (m.getCreatedByUserId() != null) {
                ps.setInt(6, m.getCreatedByUserId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateManager(Manager m) {
        String sql = """
            UPDATE Manager
            SET ManagerName = ?, ContactInfo = ?, Address = ?, RegistrationDate = ?
            WHERE ManagerID = ?
            """;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getManagerName());
            ps.setString(2, m.getContactInfo());
            ps.setString(3, m.getAddress());
            ps.setDate(4, Date.valueOf(
                    m.getRegistrationDate() != null ? m.getRegistrationDate() : LocalDate.now()
            ));
            ps.setInt(5, m.getManagerId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteManager(int id) {
        String sql = "DELETE FROM Manager WHERE ManagerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Manager extractManagerFromResultSet(ResultSet rs) throws SQLException {
        Manager m = new Manager();
        m.setManagerId(rs.getInt("ManagerID"));
        m.setUserId(rs.getObject("UserID") != null ? rs.getInt("UserID") : null);
        m.setManagerName(rs.getString("ManagerName"));
        m.setContactInfo(rs.getString("ContactInfo"));
        m.setAddress(rs.getString("Address"));

        Date regDate = rs.getDate("RegistrationDate");
        if (regDate != null) {
            m.setRegistrationDate(regDate.toLocalDate());
        }

        m.setCreatedByUserId(rs.getObject("CreatedByUserID") != null ? rs.getInt("CreatedByUserID") : null);

        return m;
    }

    public static void main(String[] args) {
        ManagerDAO dao = new ManagerDAO();

        System.out.println("===== TEST DANH SÁCH PHÂN TRANG =====");
        List<Manager> list = dao.getManagersByPage(1);
        for (Manager m : list) {
            System.out.println(m);
        }

        System.out.println("===== TEST THÊM MỚI =====");
        Manager newM = new Manager(null, "Tổ chức Trẻ Xanh", "trexanh@gmail.com",
                "Hà Nội", LocalDate.now(), null);
        System.out.println("Insert: " + dao.insertManager(newM));

        System.out.println("===== TEST CẬP NHẬT =====");
        Manager updateM = new Manager(1, null, "Green Earth Org - Updated", "greenearth@mail.com",
                "Hà Nội - Updated", LocalDate.now(), null);
        System.out.println("Update: " + dao.updateManager(updateM));

        System.out.println("===== TEST XOÁ =====");
        System.out.println("Delete ID=2: " + dao.deleteManager(2));

        System.out.println("===== TEST CHI TIẾT =====");
        System.out.println(dao.getManagerById(1));
    }

    public List<Manager> searchManagersByName(String keyword, int page) {
        List<Manager> list = new ArrayList<>();
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        String sql = """
        SELECT ManagerID, UserID, ManagerName, ContactInfo, Address, RegistrationDate, CreatedByUserID
        FROM Manager
        WHERE ManagerName LIKE ?
        ORDER BY ManagerID
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractManagerFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM Manager WHERE ManagerName LIKE ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
