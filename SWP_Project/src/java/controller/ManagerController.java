package controller;

import dao.ManagerDAO;
import entity.Manager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager")
public class ManagerController extends HttpServlet {

    private ManagerDAO managerDAO;

    @Override
    public void init() throws ServletException {
        managerDAO = new ManagerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listManagers(request, response);
                break;

            case "detail":
                showManagerDetail(request, response);
                break;

            case "add":
                showAddForm(request, response);
                break;

            case "delete":
                deleteManager(request, response);
                break;

            case "search":
                searchManagers(request, response);
                break;

            default:
                listManagers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "insert":
                insertManager(request, response);
                break;

            case "update":
                updateManager(request, response);
                break;

            default:
                response.sendRedirect("manager?action=list");
                break;
        }
    }

    private void searchManagers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int pageSize = 5;
        List<Manager> list = managerDAO.searchManagersByName(keyword, page);
        int totalResults = managerDAO.getTotalSearchCount(keyword);
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        request.setAttribute("listManagers", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("isSearch", true);

        RequestDispatcher rd = request.getRequestDispatcher("/views/managerList.jsp");
        rd.forward(request, response);
    }

    private void listManagers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int pageSize = 5;
        List<Manager> list = managerDAO.getManagersByPage(page);
        int totalPages = managerDAO.getTotalPages(pageSize);

        request.setAttribute("listManagers", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = request.getRequestDispatcher("/views/managerList.jsp");
        rd.forward(request, response);
    }

    private void showManagerDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Manager m = managerDAO.getManagerById(id);
                request.setAttribute("manager", m);
                RequestDispatcher rd = request.getRequestDispatcher("/views/managerDetail.jsp");
                rd.forward(request, response);
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("manager?action=list");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/views/managerAdd.jsp");
        rd.forward(request, response);
    }

    private void insertManager(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("managerName");
        String contact = request.getParameter("contactInfo");
        String address = request.getParameter("address");

        Manager newManager = new Manager();
        newManager.setManagerName(name);
        newManager.setContactInfo(contact);
        newManager.setAddress(address);

        boolean inserted = managerDAO.insertManager(newManager);

        if (inserted) {
            response.sendRedirect("manager?action=list");
        } else {
            request.setAttribute("error", "Không thể thêm manager mới!");
            RequestDispatcher rd = request.getRequestDispatcher("/views/managerAdd.jsp");
            rd.forward(request, response);
        }
    }

    private void updateManager(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("managerId"));
        String name = request.getParameter("managerName");
        String contact = request.getParameter("contactInfo");
        String address = request.getParameter("address");

        Manager m = new Manager();
        m.setManagerId(id);
        m.setManagerName(name);
        m.setContactInfo(contact);
        m.setAddress(address);

        boolean updated = managerDAO.updateManager(m);

        if (updated) {
            response.sendRedirect("manager?action=list");
        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
            request.setAttribute("manager", m);
            RequestDispatcher rd = request.getRequestDispatcher("/views/managerEdit.jsp");
            rd.forward(request, response);
        }
    }

    private void deleteManager(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                managerDAO.deleteManager(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("manager?action=list");
    }
}
