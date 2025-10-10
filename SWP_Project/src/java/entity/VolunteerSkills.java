/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class VolunteerSkills {

    private int volunteerSkillID;
    private int volunteerID;
    private int skillID;
    private String proficiencyLevel;

    public VolunteerSkills() {
    }

    public VolunteerSkills(int volunteerSkillID, int volunteerID, int skillID, String proficiencyLevel) {
        this.volunteerSkillID = volunteerSkillID;
        this.volunteerID = volunteerID;
        this.skillID = skillID;
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getVolunteerSkillID() {
        return volunteerSkillID;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public int getSkillID() {
        return skillID;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setVolunteerSkillID(int volunteerSkillID) {
        this.volunteerSkillID = volunteerSkillID;
    }

    public void setVolunteerID(int volunteerID) {
        this.volunteerID = volunteerID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

}
