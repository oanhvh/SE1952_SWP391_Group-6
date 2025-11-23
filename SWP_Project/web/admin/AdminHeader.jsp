<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header_admin_create.css" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- ✅ Header -->
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm px-3">
    <div class="container-fluid">
        <!-- Left side (brand or toggle button if needed) -->
        <button class="btn btn-outline-secondary d-lg-none" type="button" data-bs-toggle="offcanvas"
                data-bs-target="#sidebar" aria-controls="sidebar">
            <i class="bi bi-list"></i>
        </button>

        <!-- Right side -->
        <div class="dropdown ms-auto">
            <button class="btn btn-light border rounded-pill d-flex align-items-center gap-2" type="button"
                    id="userMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person-circle fs-5"></i>
                <span class="fw-semibold">Hello, <c:out value="${sessionScope.user.fullName}" default="Admin"/></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenuButton">
                <li><a class="dropdown-item" href="<%= request.getContextPath() %>/AdminProfileController">Profile</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="<%= request.getContextPath() %>/admin/change_password.jsp">Change password</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="<%= request.getContextPath() %>/logout" onclick="return confirm('Confirm logout?');">Sign out</a></li>
            </ul>
        </div>
    </div>
</nav>
<script>
  (function(){
    var btn = document.getElementById('userMenuButton');
    if (!btn) return;
    var menu = document.querySelector('#userMenuButton + .dropdown-menu');
    // Prefer Bootstrap if available
    if (window.bootstrap && bootstrap.Dropdown) {
      var dd = bootstrap.Dropdown.getOrCreateInstance(btn);
      btn.addEventListener('click', function(e){
        e.preventDefault();
        dd.toggle();
      });
    } else if (menu) {
      // Fallback: manual toggle
      btn.addEventListener('click', function(e){
        e.preventDefault();
        var shown = menu.classList.contains('show');
        if (!shown) {
          menu.classList.add('show');
          btn.setAttribute('aria-expanded','true');
          // position near button
          menu.style.display = 'block';
        } else {
          menu.classList.remove('show');
          btn.setAttribute('aria-expanded','false');
          menu.style.display = '';
        }
      });
      document.addEventListener('click', function(ev){
        if (!btn.contains(ev.target) && !menu.contains(ev.target)) {
          menu.classList.remove('show');
          btn.setAttribute('aria-expanded','false');
          menu.style.display = '';
        }
      });
    }
  })();
</script>
