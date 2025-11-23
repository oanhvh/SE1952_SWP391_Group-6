<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) session.getAttribute("authUser");
    String role = (String) session.getAttribute("role");
    String displayName = (user != null && user.getFullName() != null && !user.getFullName().isEmpty()) ? user.getFullName() : (user != null ? user.getUsername() : "");
    if (displayName == null) displayName = "";
    if (role == null) role = "";
%>
<!-- header section start -->
<div class="header_section"></div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/staff/index_1.jsp"><img src="<%= request.getContextPath() %>/images/logo.png"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active"><a class="nav-link" href="<%= request.getContextPath() %>/staff/index_1.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/staff/about.jsp">About</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/staff/donate.jsp">Donate</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/staff/news.jsp">News</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/staff/contact.jsp">Contact Us</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/staff/mission.jsp">Our Mission</a></li>
        </ul>
        <div class="ml-auto d-flex align-items-center">
            <div class="dropdown mr-3" style="margin-right:12px;">
                <button id="staffNotiBell" class="btn btn-light position-relative" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Notifications">
                    <span style="font-size:18px;">🔔</span>
                    <span id="staffNotiBadge" class="badge badge-danger" style="position:absolute; top:-5px; right:-5px; display:none;">0</span>
                </button>
                <div class="dropdown-menu dropdown-menu-right p-0" aria-labelledby="staffNotiBell" style="width:360px; max-height:420px; overflow:auto;">
                    <div class="d-flex justify-content-between align-items-center p-2 border-bottom">
                        <strong>Notifications</strong>
                        <a class="small" href="<%= request.getContextPath() %>/notifications/mark-all">Mark all as read</a>
                    </div>
                    <div id="staffNotiList"></div>
                    <script>
                        (function () {
                            var ctx = '<%= request.getContextPath() %>';
                            var bell = document.getElementById('staffNotiBell');
                            var badge = document.getElementById('staffNotiBadge');
                            var list = document.getElementById('staffNotiList');

                            function htmlItem(n) {
                                var readCls = n.read ? '' : 'font-weight-bold';
                                var time = n.createdAt ? new Date(n.createdAt).toLocaleString() : '';
                                return '<div class="p-2 ' + readCls + '" style="cursor:default;">'
                                        + '<div>' + escapeHtml(n.title || '') + '</div>'
                                        + '<div class="text-muted small">' + escapeHtml(n.message || '') + '</div>'
                                        + '<div class="text-muted small">' + time + '</div>'
                                        + '</div>';
                            }

                            function render(items) {
                                if (!list) return;
                                if (!items || items.length === 0) {
                                    list.innerHTML = '<div class="p-3 text-center text-muted">No notifications</div>';
                                    return;
                                }
                                list.innerHTML = items.map(htmlItem).join('');
                            }

                            function updateBadge(unread) {
                                if (!badge) return;
                                if (unread > 0) {
                                    badge.style.display = 'inline-block';
                                    badge.textContent = unread;
                                } else {
                                    badge.style.display = 'none';
                                }
                            }

                            function fetchUnread() {
                                fetch(ctx + '/notifications/unread', {credentials: 'same-origin'})
                                        .then(function (r) { if (!r.ok) throw 0; return r.json(); })
                                        .then(function (d) { updateBadge(d.unread || 0); })
                                        .catch(function () {});
                            }

                            function fetchList() {
                                if (list) list.innerHTML = '<div class="p-3 text-center text-muted">Loading...</div>';
                                fetch(ctx + '/notifications/api', {credentials: 'same-origin'})
                                        .then(function (r) {
                                            if (!r.ok) throw new Error('HTTP ' + r.status);
                                            return r.json();
                                        })
                                        .then(function (d) {
                                            updateBadge(d.unread || 0);
                                            render(d.items || []);
                                        })
                                        .catch(function () {
                                            if (list)
                                                list.innerHTML = '<div class="p-3 text-center text-danger">Failed to load. <a href="' + ctx + '/notifications' + '">View all</a></div>';
                                        });
                            }

                            if (bell) {
                                bell.addEventListener('click', function () { fetchList(); });
                                try {
                                    bell.addEventListener('shown.bs.dropdown', function () { fetchList(); });
                                } catch (e) {}
                            }

                            try {
                                document.addEventListener('DOMContentLoaded', function () {
                                    fetchUnread();
                                    fetchList();
                                });
                            } catch (e) {
                                fetchUnread();
                                fetchList();
                            }

                            setInterval(fetchUnread, 15000);

                            function escapeHtml(s) {
                                return String(s).replace(/[&<>"']/g, function (c) {
                                    var map = {'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', '\'': '&#39;'};
                                    return map[c] || c;
                                });
                            }
                        })();
                    </script>
                    <div class="border-top p-2 text-center"><a href="<%= request.getContextPath() %>/notifications">View all</a></div>
                </div>
            </div>
            <div class="dropdown">
                <button class="btn btn-light dropdown-toggle" type="button" id="userMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span style="font-weight:600;"><%= displayName %></span>
                    <span class="text-muted">(<%= role %>)</span>
                </button>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/staff/profile">My account</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/staff/change_password.jsp">Change password</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/logout" onclick="return confirm('Confirm logout?');">Sign out</a>
                </div>
            </div>
        </div>
    </div>
</nav>

<button id="sidebarToggle" class="sidebar-toggle">☰</button>
<div class="sidebar" id="managerSidebar">
    <ul>
        <li><a href="<%= request.getContextPath() %>/staff/event?action=create">Add Event</a></li>
        <!--<li><a href="<%= request.getContextPath() %>/staff/event?action //=list">Update Event</a></li>-->
        <li><a href="<%= request.getContextPath() %>/staff/event?action=list">List Event</a></li>
        <li><a href="<%= request.getContextPath() %>/staff/pendEvent?action=list">List Pending Event</a></li>
        <li><a href="<%= request.getContextPath() %>/staff/delEvent?action=list">List Inactive Event</a></li>
        <li><a href="<%= request.getContextPath() %>/staff/denEvent?action=list">List Denied Event</a></li>
        <li><a href="<%= request.getContextPath() %>/staff/applications">View Applications</a></li>
    </ul>
</div>


