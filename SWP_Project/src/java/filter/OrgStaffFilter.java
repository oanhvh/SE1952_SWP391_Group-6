package filter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/orgstaff/*"})
public class OrgStaffFilter extends BaseRoleFilter {
    @Override
    protected String expectedRole() { return "OrgStaff"; }
}
