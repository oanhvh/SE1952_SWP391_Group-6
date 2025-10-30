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

    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM Category";
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

    public void addCategory(Category category) {
        String sql = "INSERT INTO Category (CategoryName) VALUES (?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
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
}
