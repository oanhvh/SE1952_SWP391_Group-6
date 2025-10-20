package OrganizationController;

import dao.OrganizationDAO;
import entity.Organization;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "OrganizationServlet", urlPatterns = {"/organization"})
public class OrganizationServlet extends HttpServlet {

    private final OrganizationDAO organizationDAO = new OrganizationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            // 📄 Danh sách tổ chức (phân trang)
            case "list" -> {
                int page = 1;
                int pageSize = 10;

                try {
                    if (request.getParameter("page") != null) {
                        page = Integer.parseInt(request.getParameter("page"));
                        if (page < 1) page = 1;
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                int total = organizationDAO.getTotalOrganizations();
                int totalPages = (int) Math.ceil(total / (double) pageSize);
                if (page > totalPages && totalPages > 0) page = totalPages;

                List<Organization> orgList = organizationDAO.getOrganizationsByPage(page, pageSize);

                request.setAttribute("orgList", orgList);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/views/organization/organizationList.jsp").forward(request, response);
            }

            // 📋 Chi tiết tổ chức
            case "details" -> {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Organization org = organizationDAO.getOrganizationByID(id);
                    request.setAttribute("org", org);
                    request.getRequestDispatcher("/views/organization/organizationDetail.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendRedirect("organization?action=list");
                }
            }

            case "search" -> {
    String keyword = request.getParameter("keyword");
    String categoryFilter = request.getParameter("categoryFilter");
    List<Organization> results = organizationDAO.searchOrganizations(keyword, categoryFilter);

    request.setAttribute("orgList", results);
    request.setAttribute("keyword", keyword);
    request.setAttribute("categoryFilter", categoryFilter);
    request.getRequestDispatcher("/views/organization/organizationList.jsp").forward(request, response);
}


            // ➕ Form thêm mới
            case "add" -> {
                request.getRequestDispatcher("/views/organization/organizationAdd.jsp").forward(request, response);
            }

            default -> response.sendRedirect("organization?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {

            // ➕ Thêm tổ chức mới
            case "add" -> {
                try {
                    request.setCharacterEncoding("UTF-8");

                    String orgName = request.getParameter("orgName");
                    String description = request.getParameter("description");
                    String contactInfo = request.getParameter("contactInfo");
                    String address = request.getParameter("address");
                    String category = request.getParameter("category"); // ✅ thêm category

                    Organization org = new Organization();
                    org.setOrgName(orgName);
                    org.setDescription(description);
                    org.setContactInfo(contactInfo);
                    org.setAddress(address);
                    org.setRegistrationDate(LocalDate.now());
                    org.setCategory(category); // ✅ gán category

                    // Giả lập người tạo là Admin (ID = 1)
                    Users createdBy = new Users();
                    createdBy.setUserID(1);
                    org.setCreatedByUser(createdBy);

                    boolean success = organizationDAO.addOrganization(org);

                    if (success) {
                        response.sendRedirect("organization?action=list");
                    } else {
                        request.setAttribute("error", "❌ Thêm tổ chức thất bại!");
                        request.getRequestDispatcher("/views/organization/organizationAdd.jsp").forward(request, response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "❌ Đã xảy ra lỗi khi thêm tổ chức!");
                    request.getRequestDispatcher("/views/organization/organizationAdd.jsp").forward(request, response);
                }
            }

            default -> response.sendRedirect("organization?action=list");
        }
    }
}
