package controller;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author admin
 */
public class ListAccountController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve search parameters from JSP
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");

        UserDao userDao = new UserDao();
        List<Users> userList;

        // If any search parameter is provided, perform search
        if ((name != null && !name.trim().isEmpty()) ||
                (role != null && !role.trim().isEmpty()) ||
                (phone != null && !phone.trim().isEmpty())) {

            // Suppose you already have this method implemented in your DAO
            userList = userDao.searchUser(name, role, phone);

        } else {
            // Otherwise, load all users
            userList = userDao.getAllUsers();
        }

        // Set data for JSP
        request.setAttribute("userList", userList);

        // Forward to JSP page
        request.getRequestDispatcher("listAccount.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward POST to GET for simplicity
        doGet(request, response);
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

}
