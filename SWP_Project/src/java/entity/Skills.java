/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NHThanh
 */
public class Skills {

    private int skillID;
    private String skillName;
    private String description;

    public Skills() {
    }

    public Skills(int skillID, String skillName, String description) {
        this.skillID = skillID;
        this.skillName = skillName;
        this.description = description;
    }

    public int getSkillID() {
        return skillID;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
