<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="d-flex flex-column flex-shrink-0 p-3 bg-light"
     style="width: 250px; height: 100vh; border-right: 1px solid #ddd;">

    <!-- ✅ Replaced text with image -->
    <a href="<%= request.getContextPath() %>/ListAccount" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
        <img src="<%= request.getContextPath() %>/images/logo.png" alt="Admin Logo" width="180" class="img-fluid">
    </a>

    <ul class="nav nav-pills flex-column mb-auto">

        <li class="nav-item">
            <a class="nav-link text-dark d-flex justify-content-between align-items-center"
               data-bs-toggle="collapse" href="#accountManagerCollapse" role="button"
               aria-expanded="false" aria-controls="accountManagerCollapse">
                <span><i class="bi bi-people"></i> Account Manager</span>
                <i class="bi bi-chevron-down"></i>
            </a>
            <div class="collapse ps-3" id="accountManagerCollapse">
                <ul class="nav flex-column mt-2">
                    <li><a class="nav-link text-dark" href="<%= request.getContextPath() %>/ListAccount">View Account List</a></li>
                    <li><a class="nav-link text-dark" href="<%= request.getContextPath() %>/AddAccountController">Create Manager</a></li>
                </ul>
            </div>
        </li>
        <li>
            <a class="nav-link text-dark" href="<%= request.getContextPath() %>/ListVolunteer">
                <i class="bi bi-house">View Volunteer</i>
            </a>
        </li>
        <li>
            <a class="nav-link text-dark" href="<%= request.getContextPath() %>/ListManagerAccount">
                <i class="bi bi-house">View Manager</i>
            </a>
        </li>
        <li>
            <a class="nav-link text-dark" href="<%= request.getContextPath() %>/admin/skills_list.jsp">
                <i class="bi bi-house">List Skills</i>
            </a>
        </li>
        <li>
            <a class="nav-link text-dark" href="<%= request.getContextPath() %>/admin/admin_create.jsp">
                <i class="bi bi-house">Create Admin</i>
            </a>
        </li>
    </ul>
</div>
