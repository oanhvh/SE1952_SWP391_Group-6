/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author admin
 */


import dao.UserDao;
import dao.VolunteerSkillsDAO;
import dao.SkillsDAO;
import entity.Skills;
import entity.Users;

import java.time.LocalDate;
import java.util.List;

public class ProfileService {

    private final UserDao userDao = new UserDao();
    private final SkillsDAO skillsDAO = new SkillsDAO();
    private final VolunteerSkillsDAO volunteerSkillsDAO = new VolunteerSkillsDAO();

    // Lấy thông tin user
    public Users getUserProfile(int userID) {
        return userDao.getUserById(userID);
    }

    // Lấy danh sách kỹ năng của user
    public List<Skills> getUserSkills(int userID) {
        return userDao.getSkillsByUserID(userID);
    }

    // Lấy tất cả kỹ năng
    public List<Skills> getAllSkills() {
        return skillsDAO.getAllSkills();
    }

    // Cập nhật thông tin user
    public boolean updateProfile(Users user, String fullName, String email, String phone,
                                 String avatar, LocalDate dob) {
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setDateOfBirth(dob);
        user.setUpdatedAt(java.time.LocalDateTime.now());

        return userDao.updateProfile(user);
    }

    // Cập nhật kỹ năng cho user
    public void updateSkills(int userID, String[] skillIDs) {
        volunteerSkillsDAO.updateSkillsForUser(userID, skillIDs);
    }
}

