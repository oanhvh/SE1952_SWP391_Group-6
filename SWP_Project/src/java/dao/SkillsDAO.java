/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Skills;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHThanh
 */
public class SkillsDAO extends DBUtils {

    public List<Skills> getAllSkills() {
        List<Skills> skillList = new ArrayList<>();
        String sql = "SELECT * FROM Skills";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Skills skill = extractSkill(rs);
                skillList.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillList;
    }

    public int addSkill(Skills skill) {
        String sql = "INSERT INTO Skills (SkillName, Description) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, skill.getSkillName());
            pstmt.setString(2, skill.getDescription());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateSkill(Skills skill) {
        String sql = "UPDATE Skills SET SkillName = ?, Description = ? WHERE SkillID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, skill.getSkillName());
            pstmt.setString(2, skill.getDescription());
            pstmt.setInt(3, skill.getSkillID());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteSkill(int skillID) {
        String sql = "DELETE FROM Skills WHERE SkillID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, skillID);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //Lấy thông tin một kỹ năng cụ thể theo ID
    public Skills getById(int id) {
        String sql = "SELECT * FROM Skills WHERE SkillID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractSkill(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Skills extractSkill(ResultSet rs) throws Exception {
        Skills skill = new Skills();
        skill.setSkillID(rs.getInt("SkillID"));
        skill.setSkillName(rs.getString("SkillName"));
        skill.setDescription(rs.getString("Description"));
        return skill;
    }

    // kiểm tra tồn tại
    public boolean existsByName(String skillName) {
        String sql = "SELECT COUNT(*) FROM Skills WHERE LOWER(SkillName) = LOWER(?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, skillName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // kiểm tra trùng lặp tên kỹ năng khi chỉnh sửa 
    public boolean existsByNameExcludingId(String skillName, int excludeSkillId) {
        String sql = "SELECT COUNT(*) FROM Skills WHERE LOWER(SkillName) = LOWER(?) AND SkillID <> ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, skillName);
            pstmt.setInt(2, excludeSkillId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
