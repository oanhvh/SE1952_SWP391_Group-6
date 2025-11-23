/*
 * Click nbsp://nbSystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://nbSystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DBUtils;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import service.StaffService;

/**
 *
 * @author NHThanh
 */
@WebServlet(name = "UpdateStaffStatusController", urlPatterns = {"/manager/staff/status"})
public class UpdateStaffStatusController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null
                || !"Manager".equalsIgnoreCase(String.valueOf(session.getAttribute("role")))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            writeJson(response, error("Access denied"));
            return;
        }
        Users auth = (Users) session.getAttribute("authUser");
        if (auth == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            writeJson(response, error("Not authenticated"));
            return;
        }

        String userIdStr = request.getParameter("userId");
        String status = request.getParameter("status");
        if (userIdStr == null || status == null || !("Active".equals(status) || "Inactive".equals(status))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(response, error("Invalid parameters"));
            return;
        }

        int targetUserId;
        try {
            targetUserId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(response, error("Invalid userId"));
            return;
        }

        StaffService service = new StaffService();
        StaffService.UpdateStatusResult r = service.updateStaffStatus(auth.getUserID(), targetUserId, status);
        if (r.success) {
            result.put("success", true);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result.put("success", false);
            result.put("message", r.message);
        }
        writeJson(response, result);
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> m = new HashMap<>();
        m.put("success", false);
        m.put("message", message);
        return m;
    }

    private void writeJson(HttpServletResponse response, Map<String, Object> body) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            boolean first = true;
            for (Map.Entry<String, Object> e : body.entrySet()) {
                if (!first) {
                    sb.append(',');
                }
                first = false;
                sb.append('"').append(escape(e.getKey())).append('"').append(':');
                Object v = e.getValue();
                if (v instanceof Boolean) {
                    sb.append(((Boolean) v) ? "true" : "false");
                } else if (v == null) {
                    sb.append("null");
                } else {
                    sb.append('"').append(escape(String.valueOf(v))).append('"');
                }
            }
            sb.append('}');
            out.write(sb.toString());
        }
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
