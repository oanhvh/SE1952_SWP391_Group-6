/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import util.GoogleOAuthUtils;

/**
 *
 * @author NHThanh
 */
@WebServlet(name = "GoogleAuthController", urlPatterns = {"/auth/google"})
public class GoogleAuthController extends HttpServlet {



    private static final String REDIRECT_URI = "http://localhost:8080/SWP_Project/auth/google";

    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    private static final String USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String error = req.getParameter("error");

        if (error != null) {
            req.getSession().setAttribute("error", "You have denied Google access. Please try again.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if (code == null) {
            String url = GoogleOAuthUtils.buildAuthUrl(AUTH_URL, CLIENT_ID, REDIRECT_URI);
            resp.sendRedirect(url); // Chuyển đến trang đăng nhập Google
            return;
        }

        try {
            String tokenJson = GoogleOAuthUtils.exchangeCodeForToken(TOKEN_URL, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, code);
            JsonObject tokenObj = new Gson().fromJson(tokenJson, JsonObject.class);
            String accessToken = tokenObj.get("access_token").getAsString();

            String userJson = GoogleOAuthUtils.fetchUserInfo(USERINFO_URL, accessToken);
            JsonObject u = new Gson().fromJson(userJson, JsonObject.class);

            String googleId = u.get("sub").getAsString();
            String email = u.has("email") && !u.get("email").isJsonNull() ? u.get("email").getAsString() : null;
            boolean emailVerified = u.has("email_verified") && u.get("email_verified").getAsBoolean();
            String fullName = u.has("name") && !u.get("name").isJsonNull() ? u.get("name").getAsString() : "";
            String avatar = u.has("picture") && !u.get("picture").isJsonNull() ? u.get("picture").getAsString() : null;

            UserDao userDao = new UserDao();
            Users user = userDao.upsertUserByGoogle(googleId, email, fullName, avatar, emailVerified);

            // Chặn đăng nhập nếu tài khoản đã bị vô hiệu hóa
            if ("Inactive".equalsIgnoreCase(user.getStatus())) {
                HttpSession session = req.getSession(true);
                session.setAttribute("error", "This account has been deactivated. Please contact support.");
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("authUser", user);
            session.setAttribute("role", user.getRole());

            String role = user.getRole();
            if ("Volunteer".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/volunteer/index_1.jsp");
            } else if ("Staff".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/staff/index_1.jsp");
            } else if ("Manager".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/manager/index_1.jsp");
            } else if ("Admin".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/ListAccount");
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.getSession().setAttribute("error", "Google login failed. Please try again later.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
