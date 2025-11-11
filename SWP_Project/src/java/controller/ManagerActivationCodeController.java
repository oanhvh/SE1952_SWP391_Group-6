/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author NHThanh
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import dao.DBUtils;
import dao.EmployeeCodesDAO;
import dao.ManagerDAO;
import entity.Users;

@WebServlet(name = "ManagerActivationCodeController", urlPatterns = {"/manager/activation-code"})
public class ManagerActivationCodeController extends HttpServlet {

    private static final String ALPHANUM = "QWERTYUOPASDFGHJKZXCVBNM123456789";
    private static final SecureRandom RNG = new SecureRandom();
    private final EmployeeCodesDAO codesDAO = new EmployeeCodesDAO();
    private final ManagerDAO managerDAO = new ManagerDAO();
    private static final int DEFAULT_LIMIT = 20;
    private static final int MAX_LIMIT = 100;

    private String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RNG.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(idx));
        }
        return sb.toString();
    }

    //lấy danh sách số lượng mã
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);
        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int pageSize = clamp(parseIntOrDefault(request.getParameter("pageSize"), DEFAULT_LIMIT), 1, MAX_LIMIT); //kích thước trang

        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = requireManagerId(request, response, conn, auth);
            if (managerId == null) {
                return;
            }
            int total = codesDAO.getCodesCountByManager(conn, managerId);
            java.util.List<dao.EmployeeCodesDAO.CodeInfo> list = codesDAO.listCodesByManager(conn, managerId, page, pageSize);  //lấy danh sách code theo trang
            String body = "{\"items\":" + listToJson(list)
                    + ",\"total\":" + total
                    + ",\"page\":" + page
                    + ",\"pageSize\":" + pageSize
                    + "}";
            writeJson(response, HttpServletResponse.SC_OK, body);
        } catch (Exception e) {
            e.printStackTrace();
            writeError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load codes");
        }
    }

    //xóa code đã tạo
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);

        String idParam = request.getParameter("id");
        Integer codeId = parseIntOrNull(idParam);
        if (codeId == null) {
            writeError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid id");
            return;
        }

        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = requireManagerId(request, response, conn, auth);
            if (managerId == null) {
                return;
            }
            int affected = codesDAO.deleteCodeByManager(conn, managerId, codeId);
            writeJson(response, HttpServletResponse.SC_OK, "{\"deleted\":" + affected + "}");
        } catch (Exception e) {
            e.printStackTrace();
            writeError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete code");
        }
    }

    //tạo code mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);

        String code = generateCode(12);
        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = requireManagerId(request, response, conn, auth);
            if (managerId == null) {
                return;
            }
            int codeId = codesDAO.createCode(conn, managerId, code, auth.getUserID());
            writeJson(response, HttpServletResponse.SC_OK, "{\"code\":\"" + escapeJson(code) + "\",\"codeId\":" + codeId + "}");
        } catch (Exception e) {
            e.printStackTrace();
            writeError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create code: " + e.getMessage());
        }
    }

    //kết nối giữa server với browser thông báo
    private void writeJson(HttpServletResponse resp, int status, String body) throws IOException {
        resp.setStatus(status);
        try (PrintWriter out = resp.getWriter()) {
            out.write(body);
        }
    }

    //kết nối giữa server với browser thông báo lỗi
    private void writeError(HttpServletResponse resp, int status, String message) throws IOException {
        writeJson(resp, status, "{\"error\":\"" + message + "\"}");
    }

    private int parseIntOrDefault(String val, int def) {
        if (val == null) {
            return def;
        }
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return def;
        }
    }

    private Integer parseIntOrNull(String val) {
        if (val == null) {
            return null;
        }
        try {
            return Integer.valueOf(val);
        } catch (Exception e) {
            return null;
        }
    }

    //kiểm tra quyền
    private Integer requireManagerId(HttpServletRequest req, HttpServletResponse resp, Connection conn, Users auth) throws IOException {
        try {
            if (auth == null) {
                writeError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return null;
            }
            Integer managerId = managerDAO.getManagerIdByUserId(conn, auth.getUserID());
            if (managerId == null) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Manager profile not found");
                return null;
            }
            return managerId;
        } catch (Exception e) {
            e.printStackTrace();
            writeError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load manager profile");
            return null;
        }
    }

    //Biến danh sách mã Java thành chuỗi JSON chuẩn để gửi cho browser
    private String listToJson(java.util.List<dao.EmployeeCodesDAO.CodeInfo> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            var c = list.get(i);
            String createdAtMs = "null";
            if (c.createdAt != null) {
                java.time.LocalDateTime ldt = c.createdAt.toLocalDateTime();
                java.time.Instant inst = ldt.atZone(java.time.ZoneOffset.UTC).toInstant();
                createdAtMs = String.valueOf(inst.toEpochMilli());
            }
            sb.append("{\"codeId\":").append(c.codeId)
                    .append(",\"code\":\"").append(escapeJson(c.codeValue)).append("\"")
                    .append(",\"used\":").append(c.isUsed)
                    .append(",\"createdAtMs\":").append(createdAtMs)
                    .append(",\"creatorName\":\"").append(c.creatorName != null ? escapeJson(c.creatorName) : "").append("\"")
                    .append(",\"createdByUserId\":").append(c.createdByUserId == null ? "null" : c.createdByUserId)
                    .append("}");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    //Báo cho browser biết tôi sẽ gửi JSON
    private void setJson(HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
    }

    //lấy User đã đăng nhập
    private Users getAuthUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return (s != null) ? (Users) s.getAttribute("authUser") : null;
    }

    //giới hạn số lượng code
    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    //tránh lỗi cú pháp của Json
    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
