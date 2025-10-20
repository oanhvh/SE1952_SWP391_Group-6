/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Categories;
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

    private Categories extractCategory(ResultSet rs) throws Exception {
        Categories category = new Categories();
        category.setCategoryID(rs.getInt("CategoryID"));
        category.setCategoryID(rs.getInt("EventID"));
        category.setCategoryName(rs.getString("CategoryName"));
        return category;
    }

    public List<Categories> getAllCategories() {
        List<Categories> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Categories category = extractCategory(rs);
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public void addCategory(Categories category) {
        String sql = "INSERT INTO Category (EventID, CategoryName) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, category.getEventID());
            pstmt.setString(2, category.getCategoryName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Categories category) {
        String sql = "UPDATE Category SET EventID = ?, CategoryName = ? WHERE CategoryID = ?";
        try (Connection conn = DBUtils.getConnection1(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, category.getEventID());
            pstmt.setString(2, category.getCategoryName());
            pstmt.setInt(3, category.getCategoryID());
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
