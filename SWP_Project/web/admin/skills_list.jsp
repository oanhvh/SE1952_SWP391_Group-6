<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Skills" %>
<%@ page import="dao.SkillsDAO" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Skills Management</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
  Skills skill = (Skills) request.getAttribute("skill");
  String mode = (String) request.getAttribute("mode");
  if (mode == null) mode = "create";
%>
<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Skills</h3>
    <div>
      <a href="<%= request.getContextPath() %>/admin/listAccount.jsp" class="btn btn-outline-secondary">Close</a>
    </div>
  </div>

  <% String msg = request.getParameter("msg"); if (msg != null) { %>
    <div class="alert alert-success">
      <%= ("Created".equals(msg) ? "Created successfully" : ("Updated".equals(msg) ? "Updated successfully" : ("Deleted".equals(msg) ? "Deleted successfully" : msg))) %>
    </div>
  <% } %>
  <% String error = (String) request.getAttribute("error"); if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
  <% } %>

  <div class="row g-3 mb-3">
    <div class="col-md-5">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title mb-3"><%= "edit".equalsIgnoreCase(mode) ? "Edit skill" : "Create new skill" %></h5>
          <form method="post" action="<%= request.getContextPath() %>/admin/skills">
            <% if ("edit".equalsIgnoreCase(mode) && skill != null) { %>
              <input type="hidden" name="skillID" value="<%= skill.getSkillID() %>" />
            <% } %>
            <div class="mb-3">
              <label for="skillName" class="form-label">Name</label>
              <input type="text" id="skillName" name="skillName" class="form-control" required value="<%= (skill!=null && skill.getSkillName()!=null) ? skill.getSkillName() : "" %>" />
            </div>
            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea id="description" name="description" class="form-control" rows="3"><%= (skill!=null && skill.getDescription()!=null) ? skill.getDescription() : "" %></textarea>
            </div>
            <div class="d-flex gap-2">
              <button type="submit" class="btn btn-primary"><%= "edit".equalsIgnoreCase(mode) ? "Update" : "Create" %></button>
              <% if ("edit".equalsIgnoreCase(mode)) { %>
                <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/admin/skills">Cancel</a>
              <% } %>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="col-md-7">
      <div class="card h-100">
        <div class="card-body p-0">
          <div class="table-responsive">
            <table class="table table-hover mb-0">
              <thead class="table-light">
              <tr>
                <th style="width:100px">ID</th>
                <th>Name</th>
                <th>Description</th>
                <th style="width:160px">Actions</th>
              </tr>
              </thead>
              <tbody>
              <%
                List<Skills> skills = (List<Skills>) request.getAttribute("skills");
                if (skills == null) {
                  SkillsDAO dao = new SkillsDAO();
                  skills = dao.getAllSkills();
                }
                if (skills == null || skills.isEmpty()) {
              %>
                <tr><td colspan="4" class="text-center text-muted py-4">No data</td></tr>
              <%
                } else {
                  for (Skills s : skills) {
              %>
                <tr>
                  <td><%= s.getSkillID() %></td>
                  <td><%= s.getSkillName() %></td>
                  <td><%= s.getDescription() == null ? "" : s.getDescription() %></td>
                  <td>
                    <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/admin/skills?action=edit&id=<%= s.getSkillID() %>">Edit</a>
                    <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/admin/skills?action=delete&id=<%= s.getSkillID() %>" onclick="return confirm('Delete this skill?');">Delete</a>
                  </td>
                </tr>
              <%
                  }
                }
              %>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
