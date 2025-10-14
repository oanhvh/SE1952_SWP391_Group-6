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
 * @author Admin
 */
public class SkillsDAO extends DBUtils {

    private Skills extractSkill(ResultSet rs) throws Exception {
        Skills skill = new Skills();
        skill.setSkillID(rs.getInt("SkillID"));
        skill.setSkillName(rs.getString("SkillName"));
        skill.setDescription(rs.getString("Description"));
        return skill;
    }

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

    public void addSkill(Skills skill) {
        String sql = "INSERT INTO Skills (SkillID, SkillName, Description) "
                + "VALUES (?, ?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, skill.getSkillID());
            pstmt.setString(2, skill.getSkillName());
            pstmt.setString(3, skill.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateSkill(Skills skill) {
        String sql = "UPDATE Skills SET SkillID = ?, SkillName = ?, Description) = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, skill.getSkillID());
            pstmt.setString(2, skill.getSkillName());
            pstmt.setString(3, skill.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteSkill(int skillID) {
        String sql = "DELETE FROM Skills WHERE SkillID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, skillID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
