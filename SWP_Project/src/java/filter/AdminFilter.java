package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/admin-disabled/*"})
//@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "Admin"; }
}   