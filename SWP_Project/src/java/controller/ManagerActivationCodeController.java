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
    private static final int MAX_LIMIT = 20;

    private String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RNG.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(idx));
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);
        int page = 1;
        int pageSize = DEFAULT_LIMIT;

        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = requireManagerId(request, response, conn, auth);
            if (managerId == null) {
                return; //Dừng nếu không phải manager
            }
            // Lấy 20 code đầu tiên của manager này
            java.util.List<dao.EmployeeCodesDAO.CodeInfo> list = codesDAO.listCodesByManager(conn, managerId, page, pageSize); 
            int total = list.size(); // Đếm tổng số code
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);

        String idParam = request.getParameter("id");
        Integer codeId = Integer.parseInt(idParam);
//      Integer codeId = parseIntOrNull(idParam);
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

    //Tạo code mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setJson(response);
        Users auth = getAuthUser(request);

        String code = generateCode(12);
        try (Connection conn = DBUtils.getConnection1()) {
            Integer managerId = requireManagerId(request, response, conn, auth);
            if (managerId == null) { //Dừng nếu không phải manager
                return;
            }
            //Lưu code vào database
            int codeId = codesDAO.createCode(conn, managerId, code, auth.getUserID());
            writeJson(response, HttpServletResponse.SC_OK, "{\"code\":\"" + escapeJson(code) + "\",\"codeId\":" + codeId + "}"); // Trả về kết quả
        } catch (Exception e) {
            e.printStackTrace();
            writeError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create code: " + e.getMessage());
        }
    }

    //Gửi dữ liệu Json về browser
    private void writeJson(HttpServletResponse resp, int status, String body) throws IOException {
        resp.setStatus(status);
        try (PrintWriter out = resp.getWriter()) {
            out.write(body);
        }
    }

    //Gửi thông báo lỗi dưới dạng Json
    private void writeError(HttpServletResponse resp, int status, String message) throws IOException {
        writeJson(resp, status, "{\"error\":\"" + message + "\"}");
    }

    //Kiểm tra user có phải Manager không và có ManagerID hợp lệ không
    private Integer requireManagerId(HttpServletRequest req, HttpServletResponse resp, Connection conn, Users auth) throws IOException {
        try {
            //Kiểm tra user đã login chưa
            if (auth == null) {
                writeError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return null;
            }
            //Kiểm tra user có phải Manager không
            Integer managerId = managerDAO.getManagerIdByUserId(conn, auth.getUserID());
            if (managerId == null) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Manager profile not found");
                return null;
            }
            //Trả về ManagerID nếu hợp lệ
            return managerId;
        } catch (Exception e) {
            e.printStackTrace();
            writeError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load manager profile");
            return null;
        }
    }

    //Chuyển danh sách codes từ Java objects thành chuỗi JSON để gửi cho browser
    private String listToJson(java.util.List<dao.EmployeeCodesDAO.CodeInfo> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) { //sử lí từng code 1
            var c = list.get(i);
            String createdAtMs = "null"; //Chuyển đổi thời gian thành milliseconds
            if (c.createdAt != null) {
                java.time.LocalDateTime ldt = c.createdAt.toLocalDateTime();
                java.time.Instant inst = ldt.atZone(java.time.ZoneOffset.UTC).toInstant();
                createdAtMs = String.valueOf(inst.toEpochMilli());
            }
            //Tạo object Json cho mỗi code
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

    //Báo cho browser biết tôi sẽ gửi Json
    private void setJson(HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
    }

    //lấy User đã đăng nhập
    private Users getAuthUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return (s != null) ? (Users) s.getAttribute("authUser") : null;
    }

    //tránh lỗi cú pháp của Json
    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
