/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class VolunteerSkills {

    private int volunteerSkillID;
    private Volunteer volunteer;
    private Skills skill;
    private String proficiencyLevel;

    public VolunteerSkills() {
    }

    public VolunteerSkills(int volunteerSkillID, Volunteer volunteer, Skills skill, String proficiencyLevel) {
        this.volunteerSkillID = volunteerSkillID;
        this.volunteer = volunteer;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getVolunteerSkillID() {
        return volunteerSkillID;
    }

    public void setVolunteerSkillID(int volunteerSkillID) {
        this.volunteerSkillID = volunteerSkillID;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Skills getSkill() {
        return skill;
    }

    public void setSkill(Skills skill) {
        this.skill = skill;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
