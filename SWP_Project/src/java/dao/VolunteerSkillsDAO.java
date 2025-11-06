/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import entity.Skills;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class VolunteerSkillsDAO {
    public List<Skills> getSkillsByUserID(int userID) {
    List<Skills> list = new ArrayList<>();
    String sql = """
        SELECT s.SkillID, s.SkillName, s.Description
        FROM VolunteerSkills vs
        JOIN Volunteer v ON vs.VolunteerID = v.VolunteerID
        JOIN Skills s ON vs.SkillID = s.SkillID
        WHERE v.UserID = ?
    """;

    try (Connection con = DBUtils.getConnection1();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Skills s = new Skills();
            s.setSkillID(rs.getInt("SkillID"));
            s.setSkillName(rs.getString("SkillName"));
            s.setDescription(rs.getString("Description"));
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


public void updateSkillsForUser(int userID, String[] selectedSkillIDs) {
    String deleteSQL = """
        DELETE FROM VolunteerSkills 
        WHERE VolunteerID = (SELECT VolunteerID FROM Volunteer WHERE UserID = ?)
    """;
    String insertSQL = """
        INSERT INTO VolunteerSkills (VolunteerID, SkillID, ProficiencyLevel)
        VALUES ((SELECT VolunteerID FROM Volunteer WHERE UserID = ?), ?, 'Intermediate')
    """;

    try (Connection con = DBUtils.getConnection1()) {
        // Xóa kỹ năng cũ
        try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
            ps.setInt(1, userID);
            ps.executeUpdate();
        }

        // Thêm kỹ năng mới
        if (selectedSkillIDs != null) {
            for (String skillID : selectedSkillIDs) {
                try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                    ps.setInt(1, userID);
                    ps.setInt(2, Integer.parseInt(skillID));
                    ps.executeUpdate();
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
