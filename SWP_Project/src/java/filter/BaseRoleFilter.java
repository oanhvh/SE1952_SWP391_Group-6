package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseRoleFilter implements Filter {

    protected abstract String expectedRole();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if (role == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!role.equalsIgnoreCase(expectedRole())) {
            resp.sendRedirect(req.getContextPath() + "/error/403.jsp");
            return;
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        chain.doFilter(request, response);
    }
}
