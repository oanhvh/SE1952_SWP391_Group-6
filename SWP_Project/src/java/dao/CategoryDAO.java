/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
/**
 *
 * @author DucNM
 */
public class CategoryDAO extends DBUtils {

    private Category extractCategory(ResultSet rs) throws Exception {
        Category category = new Category();
        category.setCategoryID(rs.getInt("CategoryID"));
        category.setCategoryName(rs.getString("CategoryName"));
        return category;
    }

    // Lấy tất cả category (cũ) – vẫn giữ để tránh phá vỡ chỗ khác
    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Category";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Category category = extractCategory(rs);
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    // Lấy category theo ManagerID (dùng cho màn manager)
    public List<Category> getCategoriesByManager(int managerId) {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Category WHERE ManagerID = ? ORDER BY CategoryName";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, managerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Category category = extractCategory(rs);
                    categoryList.add(category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    // Thêm category cho một manager cụ thể
    public void addCategory(Category category, int managerId) {
        String sql = "INSERT INTO Category (CategoryName, ManagerID) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setInt(2, managerId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE Category SET CategoryName = ? WHERE CategoryID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setInt(2, category.getCategoryID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryID) {
        String sql = "DELETE FROM Category WHERE CategoryID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá category theo ID và ManagerID để đảm bảo đúng tenant
    public boolean deleteCategoryForManager(int categoryID, int managerId) {
        String sql = "DELETE FROM Category WHERE CategoryID = ? AND ManagerID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryID);
            pstmt.setInt(2, managerId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Category getCategoryById(int categoryId) {
        Category category = null;
        String sql = "SELECT CategoryID, CategoryName FROM Category WHERE CategoryID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = extractCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    // Kiểm tra trùng tên category trong phạm vi một manager
    public boolean existsByNameForManager(String categoryName, int managerId) {
        String sql = "SELECT 1 FROM Category WHERE ManagerID = ? AND CategoryName = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            ps.setString(2, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
