/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DBUtils;
import dao.ManagerDAO;
import entity.Category;
import entity.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import service.CategoryService;

/**
 *
 * @author DucNM
 */
@WebServlet(name = "CategoryController", urlPatterns = {"/manager/category"})
public class CategoryController extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "add":
                addCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // Lấy managerId từ session; nếu chưa có thì tra từ DB dựa vào authUser và lưu lại
    private Integer resolveManagerId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session is missing");
            return null;
        }

        Integer managerId = (Integer) session.getAttribute("managerId");
        if (managerId != null && managerId > 0) {
            return managerId;
        }

        Users auth = (Users) session.getAttribute("authUser");
        if (auth == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return null;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            ManagerDAO mdao = new ManagerDAO();
            managerId = mdao.getManagerIdByUserId(conn, auth.getUserID());
            if (managerId == null || managerId <= 0) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager profile not found");
                return null;
            }
            session.setAttribute("managerId", managerId);
            return managerId;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to resolve managerId");
            return null;
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer managerId = resolveManagerId(request, response);
        if (managerId == null) return;

        request.setAttribute("categories", categoryService.getCategoriesForManager(managerId));
        request.getRequestDispatcher("/manager/listCategory.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/manager/addCategory.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/manager/category?action=list");
            return;
        }
        int categoryID = Integer.parseInt(idParam);
        Category category = categoryService.getCategoryById(categoryID);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/manager/editCategory.jsp").forward(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("categoryName");

        Category category = new Category();
        category.setCategoryName(name);
        Integer managerId = resolveManagerId(request, response);
        if (managerId == null) return;

        String error = categoryService.addCategoryForManager(category, managerId);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("categoryName", name);
            request.getRequestDispatcher("/manager/addCategory.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/manager/category?action=list");
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Integer managerId = resolveManagerId(request, response);
        if (managerId == null) return;

        categoryService.deleteCategoryForManager(id, managerId);
        response.sendRedirect(request.getContextPath() + "/manager/category?action=list");
    }
}
