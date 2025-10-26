package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/staff/*"})
public class StaffFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "Staff"; }
}
