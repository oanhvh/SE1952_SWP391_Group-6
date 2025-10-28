/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;

/**
 *
 * @author DucNM
 */
@WebServlet(name="CategoryController", urlPatterns={"/category"})
public class CategoryController extends HttpServlet {
   
    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                addCategory(request, response);
                break;
            case "update":
                updateCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

     private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categories", categoryService.getAllCategories());
        request.getRequestDispatcher("listCategory.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addCategory.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryID = Integer.parseInt(request.getParameter("id"));
        Category category = categoryService.getCategoryById(categoryID);
        request.setAttribute("category", category);
        request.getRequestDispatcher("editCategory.jsp").forward(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("categoryName");

        Category category = new Category();
        category.setCategoryName(name);

        String error = categoryService.addCategory(category);
        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("addCategory.jsp").forward(request, response);
        } else {
            response.sendRedirect("CategoryController?action=list");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("categoryID"));
        String name = request.getParameter("categoryName");

        Category category = new Category();
        category.setCategoryID(id);
        category.setCategoryName(name);

        String error = categoryService.updateCategory(category);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("category", category);
            request.getRequestDispatcher("editCategory.jsp").forward(request, response);
        } else {
            response.sendRedirect("CategoryController?action=list");
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryService.deleteCategory(id);
        response.sendRedirect("CategoryController?action=list");
    }
}
