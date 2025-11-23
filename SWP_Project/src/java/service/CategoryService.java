/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CategoryDAO;
import entity.Category;
import java.util.List;

/**
 *
 * @author DucNM
 */
public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    // =============================
    // CRUD
    // =============================
    // Lấy category cho một manager cụ thể
    public List<Category> getCategoriesForManager(int managerId) {
        return categoryDAO.getCategoriesByManager(managerId);
    }

    public Category getCategoryById(int categoryID) {
        List<Category> all = categoryDAO.getAllCategory();
        for (Category c : all) {
            if (c.getCategoryID() == categoryID) {
                return c;
            }
        }
        return null;
    }

    public String addCategoryForManager(Category category, int managerId) {
        String error = validateCategory(category);
        if (error != null) {
            return error;
        }

        // Kiểm tra trùng tên trong phạm vi một manager
        if (categoryDAO.existsByNameForManager(category.getCategoryName(), managerId)) {
            return "Category name already exists.";
        }

        categoryDAO.addCategory(category, managerId);
        return null;
    }

    public String updateCategory(Category category) {
        String error = validateCategory(category);
        if (error != null) {
            return error;
        }

        Category existing = getCategoryById(category.getCategoryID());
        if (existing == null) {
            return "Category not found.";
        }

        categoryDAO.updateCategory(category);
        return null;
    }

    public String deleteCategoryForManager(int categoryID, int managerId) {
        Category existing = getCategoryById(categoryID);
        if (existing == null) {
            return "Category not found.";
        }
        boolean ok = categoryDAO.deleteCategoryForManager(categoryID, managerId);
        if (!ok) {
            return "Category not found or does not belong to this manager.";
        }
        return null;
    }

    // =============================
    // VALIDATION
    // =============================
    private String validateCategory(Category category) {
        if (category == null) {
            return "Category data is missing.";
        }
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return "Category name is required.";
        }
        if (category.getCategoryName().length() > 200) {
            return "Category name cannot exceed 200 characters.";
        }
        return null;
    }
}
