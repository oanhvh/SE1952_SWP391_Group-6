package dao;

import entity.Organization;
import entity.Users;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO {

    // 📄 Lấy danh sách tổ chức có phân trang
    public List<Organization> getOrganizationsByPage(int page, int pageSize) {
        List<Organization> list = new ArrayList<>();
        String sql = """
                     SELECT * FROM (
                        SELECT ROW_NUMBER() OVER (ORDER BY OrgID DESC) AS RowNum,
                               o.OrgID, o.OrgName, o.Description, o.ContactInfo, o.Address, o.RegistrationDate,
                               o.Category,
                               o.UserID, u.FullName AS UserFullName,
                               o.CreatedByUserID, u2.FullName AS CreatedByFullName
                        FROM Organization o
                        LEFT JOIN Users u ON o.UserID = u.UserID
                        LEFT JOIN Users u2 ON o.CreatedByUserID = u2.UserID
                     ) AS Temp
                     WHERE RowNum BETWEEN ? AND ?
                     """;
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int start = (page - 1) * pageSize + 1;
            int end = page * pageSize;

            ps.setInt(1, start);
            ps.setInt(2, end);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setFullName(rs.getString("UserFullName"));

                Users createdBy = new Users();
                createdBy.setUserID(rs.getInt("CreatedByUserID"));
                createdBy.setFullName(rs.getString("CreatedByFullName"));

                Organization org = new Organization(
                        rs.getInt("OrgID"),
                        user,
                        rs.getString("OrgName"),
                        rs.getString("Description"),
                        rs.getString("ContactInfo"),
                        rs.getString("Address"),
                        rs.getDate("RegistrationDate") != null
                                ? rs.getDate("RegistrationDate").toLocalDate()
                                : null,
                        createdBy,
                        rs.getString("Category") // ✅ cột mới
                );
                list.add(org);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 📊 Tổng số tổ chức
    public int getTotalOrganizations() {
        String sql = "SELECT COUNT(*) FROM Organization";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Organization> searchOrganizations(String keyword, String categoryFilter) {
    List<Organization> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("""
        SELECT o.OrgID, o.OrgName, o.Description, o.ContactInfo, o.Address, o.RegistrationDate,
               o.Category,
               o.UserID, u.FullName AS UserFullName,
               o.CreatedByUserID, u2.FullName AS CreatedByFullName
        FROM Organization o
        LEFT JOIN Users u ON o.UserID = u.UserID
        LEFT JOIN Users u2 ON o.CreatedByUserID = u2.UserID
        WHERE 1=1
    """);

    if (keyword != null && !keyword.isBlank()) {
        sql.append("""
            AND (o.OrgName LIKE ? OR o.Description LIKE ? OR o.ContactInfo LIKE ? OR o.Address LIKE ?)
        """);
    }
    if (categoryFilter != null && !categoryFilter.isBlank()) {
        sql.append(" AND o.Category LIKE ? ");
    }

    sql.append(" ORDER BY o.OrgID DESC");

    try (Connection conn = DBUtils.getConnection1();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int idx = 1;
        if (keyword != null && !keyword.isBlank()) {
            String like = "%" + keyword + "%";
            ps.setString(idx++, like);
            ps.setString(idx++, like);
            ps.setString(idx++, like);
            ps.setString(idx++, like);
        }
        if (categoryFilter != null && !categoryFilter.isBlank()) {
            ps.setString(idx++, "%" + categoryFilter + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Users user = new Users();
            user.setUserID(rs.getInt("UserID"));
            user.setFullName(rs.getString("UserFullName"));

            Users createdBy = new Users();
            createdBy.setUserID(rs.getInt("CreatedByUserID"));
            createdBy.setFullName(rs.getString("CreatedByFullName"));

            Organization org = new Organization(
                    rs.getInt("OrgID"),
                    user,
                    rs.getString("OrgName"),
                    rs.getString("Description"),
                    rs.getString("ContactInfo"),
                    rs.getString("Address"),
                    rs.getDate("RegistrationDate") != null
                            ? rs.getDate("RegistrationDate").toLocalDate()
                            : null,
                    createdBy,
                    rs.getString("Category")
            );
            list.add(org);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    //  Lấy chi tiết một tổ chức theo ID
    public Organization getOrganizationByID(int id) {
        Organization org = null;
        String sql = """
                     SELECT o.OrgID, o.OrgName, o.Description, o.ContactInfo, o.Address, o.RegistrationDate,
                            o.Category,
                            o.UserID, u.FullName AS UserFullName,
                            o.CreatedByUserID, u2.FullName AS CreatedByFullName
                     FROM Organization o
                     LEFT JOIN Users u ON o.UserID = u.UserID
                     LEFT JOIN Users u2 ON o.CreatedByUserID = u2.UserID
                     WHERE o.OrgID = ?
                     """;
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setFullName(rs.getString("UserFullName"));

                Users createdBy = new Users();
                createdBy.setUserID(rs.getInt("CreatedByUserID"));
                createdBy.setFullName(rs.getString("CreatedByFullName"));

                org = new Organization(
                        rs.getInt("OrgID"),
                        user,
                        rs.getString("OrgName"),
                        rs.getString("Description"),
                        rs.getString("ContactInfo"),
                        rs.getString("Address"),
                        rs.getDate("RegistrationDate") != null
                                ? rs.getDate("RegistrationDate").toLocalDate()
                                : null,
                        createdBy,
                        rs.getString("Category")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org;
    }

    //  Thêm tổ chức mới
    public boolean addOrganization(Organization org) {
        String sql = """
                     INSERT INTO Organization (UserID, OrgName, Description, ContactInfo, Address,
                                                RegistrationDate, CreatedByUserID, Category)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                     """;
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, org.getUser() != null ? org.getUser().getUserID() : null, java.sql.Types.INTEGER);
            ps.setString(2, org.getOrgName());
            ps.setString(3, org.getDescription());
            ps.setString(4, org.getContactInfo());
            ps.setString(5, org.getAddress());
            ps.setDate(6, org.getRegistrationDate() != null ? Date.valueOf(org.getRegistrationDate()) : null);
            ps.setObject(7, org.getCreatedByUser() != null ? org.getCreatedByUser().getUserID() : null, java.sql.Types.INTEGER);
            ps.setString(8, org.getCategory());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🧪 Test
    public static void main(String[] args) {
        OrganizationDAO dao = new OrganizationDAO();

        Users createdBy = new Users();
        createdBy.setUserID(1);

        Organization org = new Organization();
        org.setOrgName("Tổ chức thử nghiệm ChatGPT");
        org.setDescription("Đây là tổ chức test DAO addOrganization()");
        org.setContactInfo("0123456789");
        org.setAddress("Hà Nội");
        org.setRegistrationDate(LocalDate.now());
        org.setCreatedByUser(createdBy);
        org.setCategory("Tổ chức thử nghiệm / kỹ thuật"); // ✅ thêm category

        boolean added = dao.addOrganization(org);
        System.out.println("Add organization result: " + added);

        int page = 1, pageSize = 10;
        List<Organization> orgs = dao.getOrganizationsByPage(page, pageSize);
        System.out.println("\n=== Page " + page + " ===");
        for (Organization o : orgs) {
            System.out.println(o.getOrgID() + " - " + o.getOrgName() + " (" + o.getCategory() + ")");
        }
    }
}
