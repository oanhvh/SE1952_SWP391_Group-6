package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/organization/*"})
public class OrganizationFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "Organization"; }
}
