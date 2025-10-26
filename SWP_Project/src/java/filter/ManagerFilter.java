package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/manager/*"})
public class ManagerFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "Manager"; }
}
