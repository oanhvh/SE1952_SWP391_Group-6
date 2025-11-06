/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author NHThanh
 */
public class GoogleOAuthUtils {
    
    //Tạo URL để chuyển hướng user đến Google Login
    public static String buildAuthUrl(String authBaseUrl, String clientId, String redirectUri) throws IOException {
        String scope = "email%20profile";
        return authBaseUrl
                + "?response_type=code"
                + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                + "&scope=" + scope
                + "&access_type=online"
                + "&prompt=select_account";
    }
    
    //Đổi authorization code lấy access token
    public static String exchangeCodeForToken(String tokenUrl, String clientId, String clientSecret,
            String redirectUri, String code) throws IOException {
        String body = "code=" + URLEncoder.encode(code, "UTF-8")
                + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                + "&grant_type=authorization_code";
        return postForm(tokenUrl, body);
    }
    
    //Lấy thông tin user từ Google API
    public static String fetchUserInfo(String userInfoUrl, String accessToken) throws IOException {
        URL url = new URL(userInfoUrl + "?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return read(con);
    }
    
    //Gửi POST request
    private static String postForm(String urlStr, String body) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream os = con.getOutputStream()) {
            os.write(body.getBytes("UTF-8"));
        }
        return read(con);
    }
    
    //Đọc response
    private static String read(HttpURLConnection con) throws IOException {
        InputStream is = (con.getResponseCode() >= 400) ? con.getErrorStream() : con.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
