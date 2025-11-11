/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Skills;
import entity.Users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Duc
 */
public class UserDao extends DBUtils {
//    Connection conn = null;
//    PreparedStatement pstmt = null;
//    ResultSet rs = null;

    private Users extractUser(ResultSet rs) throws Exception {
        Users user = new Users();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPasswordHash(rs.getString("passwordHash"));
        user.setRole(rs.getString("Role"));
        user.setStatus(rs.getString("Status"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setDateOfBirth(rs.getDate("DateOfBirth") != null ? rs.getDate("DateOfBirth").toLocalDate() : null);
        user.setPhone(rs.getString("Phone"));
        user.setAvatar(rs.getString("Avatar"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null);
        user.setUpdatedAt(rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null);

        try {
            user.setGoogleID(rs.getString("GoogleID"));
        } catch (Exception ignore) {
        }
        try {
            user.setFacebookID(rs.getString("FacebookID"));
        } catch (Exception ignore) {
        }
        try {
            user.setLoginProvider(rs.getString("LoginProvider"));
        } catch (Exception ignore) {
        }
        try {
            user.setIsEmailVerified(rs.getBoolean("IsEmailVerified"));
        } catch (Exception ignore) {
        }
        try {
            user.setIsPhoneVerified(rs.getBoolean("IsPhoneVerified"));
        } catch (Exception ignore) {
        }
        return user;
    }

    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public Users getUserbyUsername(String Username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        Users user = null;

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isUsernameExisted(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }

    public Users upsertUserByGoogle(String googleId, String email, String fullName, String avatar, boolean emailVerified) throws SQLException {
        try (Connection cn = DBUtils.getConnection1()) {
            cn.setAutoCommit(false);
            try {
                // User đã từng đăng nhập bằng Google
                Users u = findByGoogleId(cn, googleId);
                if (u != null) {
                    updateLoginMeta(cn, u.getUserID(), "Google", emailVerified, fullName, avatar);
                    cn.commit();
                    return getUserById(u.getUserID());
                }

                // User có tài khoản gmail, link với Google đăng nhập bình thường
                if (email != null && !email.trim().isEmpty()) {
                    u = findByEmail(cn, email);
                    if (u != null) {
                        linkGoogleToUser(cn, u.getUserID(), googleId, emailVerified, fullName, avatar);
                        cn.commit();
                        return getUserById(u.getUserID());
                    }
                }

                // Tạo mới user Google (role mặc định Volunteer,)
                int newId = insertGoogleUser(cn, googleId, email, fullName, avatar, emailVerified);
                try {
                    dao.VolunteerDAO volunteerDAO = new dao.VolunteerDAO();
                    volunteerDAO.createVolunteer(cn, newId);
                } catch (Exception e) {
                    // Nếu tạo Volunteer thất bại, log nhưng không rollback vì user đã được tạo
                    System.err.println("Warning: Failed to create Volunteer record for Google user: " + e.getMessage());
                }
                cn.commit();
                return getUserById(newId);
            } catch (Exception ex) {
                cn.rollback();
                if (ex instanceof SQLException) {
                    throw (SQLException) ex;
                }
                throw new SQLException(ex);
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    //Tìm trong database xem có user nào đã đăng ký với Google ID này chưa?
    private Users findByGoogleId(Connection cn, String googleId) throws SQLException {
        String sql = "SELECT TOP 1 * FROM Users WHERE GoogleID = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, googleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    try {
                        return extractUser(rs);
                    } catch (Exception e) {
                        throw new SQLException(e);
                    }
                }
            }
        }
        return null;
    }

    //Tìm trong database xem có user nào đã đăng ký với email này chưa?
    private Users findByEmail(Connection cn, String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        String sql = "SELECT TOP 1 * FROM Users WHERE Email = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    try {
                        return extractUser(rs);
                    } catch (Exception e) {
                        throw new SQLException(e);
                    }
                }
            }
        }
        return null;
    }

    //User đã có tài khoản bằng email, nay muốn thêm đăng nhập bằng Google vào tài khoản đó
    private void linkGoogleToUser(Connection cn, int userId, String googleId, boolean emailVerified, String fullName, String avatar) throws SQLException {
        String sql = "UPDATE Users SET GoogleID = ?, LoginProvider = 'Google', "
                + "IsEmailVerified = CASE WHEN ? = 1 THEN 1 ELSE IsEmailVerified END, "
                + "FullName = COALESCE(?, FullName), Avatar = COALESCE(?, Avatar), UpdatedAt = SYSUTCDATETIME() "
                + "WHERE UserID = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, googleId);
            ps.setInt(2, emailVerified ? 1 : 0);
            ps.setString(3, fullName);
            ps.setString(4, avatar);
            ps.setInt(5, userId);
            ps.executeUpdate();
        }
    }

    //Khi user đăng nhập lại bằng Google/Facebook, cập nhật thông tin mới nhất từ nhà cung cấp
    private void updateLoginMeta(Connection cn, int userId, String provider, boolean emailVerified, String fullName, String avatar) throws SQLException {
        String sql = "UPDATE Users SET LoginProvider = ?, "
                + "IsEmailVerified = CASE WHEN ? = 1 THEN 1 ELSE IsEmailVerified END, "
                + "FullName = COALESCE(?, FullName), Avatar = COALESCE(?, Avatar), UpdatedAt = SYSUTCDATETIME() "
                + "WHERE UserID = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, provider);
            ps.setInt(2, emailVerified ? 1 : 0);
            ps.setString(3, fullName);
            ps.setString(4, avatar);
            ps.setInt(5, userId);
            ps.executeUpdate();
        }
    }

    //Tạo tài khoản mới hoàn toàn cho user đăng nhập bằng Google lần đầu
    private int insertGoogleUser(Connection cn, String googleId, String email, String fullName, String avatar, boolean emailVerified) throws SQLException {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, DateOfBirth, Phone, Avatar, "
                + "GoogleID, FacebookID, LoginProvider, IsEmailVerified, IsPhoneVerified, CreatedAt, UpdatedAt) "
                + "VALUES (?, NULL, 'Volunteer', 'Active', ?, ?, NULL, NULL, ?, ?, NULL, 'Google', ?, 0, SYSUTCDATETIME(), SYSUTCDATETIME()); "
                + "SELECT SCOPE_IDENTITY();";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            // Tạo username duy nhất
            String baseUsername = (email != null && !email.isEmpty())
                    ? email
                    : ("gg_" + googleId.substring(0, Math.min(8, googleId.length())));

            // Kiểm tra username đã tồn tại chưa, nếu có thì thêm suffix để unique
            String username = baseUsername;
            int suffix = 1;
            while (isUsernameExistedInConnection(cn, username)) {
                username = baseUsername + "_" + suffix;
                suffix++;
                // Giới hạn độ dài username (nếu cần)
                if (username.length() > 45) {
                    username = "gg_" + System.currentTimeMillis() % 100000;
                }
            }

            ps.setString(1, username);
            ps.setString(2, fullName != null ? fullName : "");
            ps.setString(3, email); // Email có thể NULL
            ps.setString(4, avatar);
            ps.setString(5, googleId);
            ps.setInt(6, emailVerified ? 1 : 0);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    // Helper method để kiểm tra username trong cùng connection (cho transaction)
    private boolean isUsernameExistedInConnection(Connection cn, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // tạo một user mới trong bảng Users
    public int createUser(Connection conn, Users user, boolean hashPassword) throws Exception {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, DateOfBirth, Phone, Avatar, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSUTCDATETIME(), SYSUTCDATETIME())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            String pwd = user.getPasswordHash();
            if (hashPassword && pwd != null) {
                pwd = sha256(pwd);
            }
            pstmt.setString(2, pwd);
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());
            pstmt.setDate(7, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            pstmt.setString(8, user.getPhone());
            pstmt.setString(9, user.getAvatar());
            int affected = pstmt.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Creating user failed, no rows affected.");
            }
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("Creating user failed, no ID obtained.");
        }
    }

    public int addUser(Users user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, Status, FullName, Email, DateOfBirth, Phone, Avatar, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());
            if (user.getDateOfBirth() != null) {
                pstmt.setDate(7, java.sql.Date.valueOf(user.getDateOfBirth()));
            } else {
                pstmt.setNull(7, java.sql.Types.DATE);
            }
            pstmt.setString(8, user.getPhone());
            pstmt.setString(9, user.getAvatar());
            pstmt.setTimestamp(10, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            pstmt.setTimestamp(11, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateUser(Users user) {
        String sql = "UPDATE Users SET PasswordHash = ?, Role = ?, Status = ?, FullName = ?, "
                + "Email = ?, DateOfBirth = ?, Phone = ?, Avatar = ?, UpdatedAt = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPasswordHash());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getStatus());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getEmail());
            pstmt.setDate(6, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            pstmt.setString(7, user.getPhone());
            pstmt.setString(8, user.getAvatar());
            pstmt.setTimestamp(9, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            pstmt.setInt(10, user.getUserID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserStatus(int UserID, int Status) {
        String sql = "UPDATE Users SET Status = ? WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Status);
            pstmt.setInt(2, UserID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ghi lại log thời gian hoạt động gần nhất, xét từ ngay khi đăng nhập vào
    public boolean updateLastLogin(int userId) {
        String sql = "UPDATE Users SET UpdatedAt = SYSUTCDATETIME() WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(int userId) {
        String sql = "SELECT Role FROM Users WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public List<Users> searchUser(String name, String role, String phone) {
        List<Users> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1");
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND fullName LIKE ?");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND role LIKE ?");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            sql.append(" AND phone LIKE ?");
        }
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (name != null && !name.trim().isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(index++, "%" + role + "%");
            }
            if (phone != null && !phone.trim().isEmpty()) {
                ps.setString(index, "%" + phone + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Users getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(Users user) {
        String sql = "UPDATE Users SET FullName = ?, Email = ?, Phone = ?, DateOfBirth = ?, Avatar = ?, FacebookID = ?, GoogleID = ?, UpdatedAt = SYSUTCDATETIME() WHERE UserID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            if (user.getDateOfBirth() != null) {
                ps.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setString(5, user.getAvatar());

            // FacebookID (param 6)
            if (user.getFacebookID() != null && !user.getFacebookID().trim().isEmpty()) {
                ps.setString(6, user.getFacebookID());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            // GoogleID (param 7)
            if (user.getGoogleID() != null && !user.getGoogleID().trim().isEmpty()) {
                ps.setString(7, user.getGoogleID());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }

            ps.setInt(8, user.getUserID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public boolean updateProfileByUsername(Users user) {
        StringBuilder sql = new StringBuilder(
                "UPDATE Users SET FullName = ?, Email = ?, Phone = ?, UpdatedAt = ?"
        );

        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            sql.append(", Avatar = ?");
        }
        sql.append(" WHERE Username = ?");

        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setString(index++, user.getFullName());
            ps.setString(index++, user.getEmail());
            ps.setString(index++, user.getPhone());
            ps.setTimestamp(index++, Timestamp.valueOf(user.getUpdatedAt()));

            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                ps.setString(index++, user.getAvatar());
            }

            ps.setString(index, user.getUsername());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserIdByUsername(String username) {
        String sql = "SELECT UserID FROM Users WHERE Username = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Skills> getSkillsByUserID(int userID) {
        List<Skills> list = new ArrayList<>();

        String sql =
                "SELECT "
                        + "s.SkillID, "
                        + "s.SkillName, "
                        + "s.Description "
                        + "FROM VolunteerSkills vs "
                        + "JOIN Volunteer v ON vs.VolunteerID = v.VolunteerID "
                        + "JOIN Skills s ON vs.SkillID = s.SkillID "
                        + "WHERE v.UserID = ?";


        try (Connection con = DBUtils.getConnection1();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Skills skill = new Skills();
                skill.setSkillID(rs.getInt("SkillID"));
                skill.setSkillName(rs.getString("SkillName"));
                skill.setDescription(rs.getString("Description"));

                list.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isPhoneExisted(String phone) {
        String sql = "SELECT COUNT(*) FROM Users WHERE phone = ?";
        try (Connection conn = DBUtils.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
