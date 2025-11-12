package controller;

import dao.SkillsDAO;
import entity.Skills;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import service.SkillsService;

@WebServlet(name = "AdminSkillsController", urlPatterns = {"/admin/skills"})
public class AdminSkillsController extends HttpServlet {

    private final SkillsDAO skillsDAO = new SkillsDAO();
    private final SkillsService skillsService = new SkillsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Skills s = skillsDAO.getById(id);
                if (s != null) {
                    request.setAttribute("skill", s);
                    request.setAttribute("mode", "edit");
                } else {
                    request.setAttribute("msg", "NotFound");
                }
            } catch (Exception e) {
                request.setAttribute("msg", "InvalidId");
            }
        }

        if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                skillsDAO.deleteSkill(id);
                request.setAttribute("msg", "Deleted");
            } catch (Exception e) {
                request.setAttribute("msg", "DeleteFailed");
            }
        }

        List<Skills> list = skillsDAO.getAllSkills();
        request.setAttribute("skills", list);
        request.getRequestDispatcher("/admin/skills_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("skillID");
        String name = request.getParameter("skillName");
        String desc = request.getParameter("description");

        Skills s = new Skills();
        s.setSkillName(name);
        s.setDescription(desc);

        try {
            Map<String,String> errors = (idStr == null || idStr.isEmpty())
                    ? skillsService.validateCreate(name, desc)
                    : skillsService.validateUpdate(Integer.parseInt(idStr), name, desc);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("skill", s);
                request.setAttribute("mode", (idStr == null || idStr.isEmpty()) ? "create" : "edit");
            } else {
                if (idStr == null || idStr.isEmpty()) {
                    skillsDAO.addSkill(s);
                    request.setAttribute("msg", "Created");
                } else {
                    s.setSkillID(Integer.parseInt(idStr));
                    skillsDAO.updateSkill(s);
                    request.setAttribute("msg", "Updated");
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Save failed");
            request.setAttribute("skill", s);
            request.setAttribute("mode", (idStr == null || idStr.isEmpty()) ? "create" : "edit");
        }

        List<Skills> list = skillsDAO.getAllSkills();
        request.setAttribute("skills", list);
        request.getRequestDispatcher("/admin/skills_list.jsp").forward(request, response);
    }
}
