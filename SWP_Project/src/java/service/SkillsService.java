/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.SkillsDAO;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author NHThanh
 */
public class SkillsService {
    private final SkillsDAO skillsDAO = new SkillsDAO();

    public Map<String, String> validateCreate(String name, String description) {
        Map<String, String> errors = new HashMap<>();
        if (isBlank(name)) {
            errors.put("skillName", "Skill name is required");
        } else if (skillsDAO.existsByName(name.trim())) {
            errors.put("skillName", "Skill name already exists");
        }
        return errors;
    }

    public Map<String, String> validateUpdate(int skillId, String name, String description) {
        Map<String, String> errors = new HashMap<>();
        if (isBlank(name)) {
            errors.put("skillName", "Skill name is required");
        } else if (skillsDAO.existsByNameExcludingId(name.trim(), skillId)) {
            errors.put("skillName", "Skill name already exists");
        }
        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
