package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/volunteer/*"})
public class VolunteerFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "Volunteer"; }
}
