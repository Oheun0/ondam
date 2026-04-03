package com.ondam.user.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class KakaoCallbackController implements Controller {

    private static final ResourceBundle rb = ResourceBundle.getBundle("config");
    private final String CLIENT_ID = rb.getString("kakao.rest.api.key"); 
    private final String CLIENT_SECRET = rb.getString("kakao.client.secret");
    //private final String REDIRECT_URI = "http://localhost/ondam/kakao-callback";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String scheme = request.getScheme(); 
        String serverName = request.getServerName(); 
        int serverPort = request.getServerPort(); 
        String contextPath = request.getContextPath();
        String portStr = (serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort;
        String dynamicRedirectUri = scheme + "://" + serverName + portStr + contextPath + "/kakao-callback";
    	
        String code = request.getParameter("code");
        if (code == null) return "redirect:/login";

        String accessToken = getAccessToken(code, dynamicRedirectUri);
        
        if (accessToken != null) {
            String userInfoJson = getUserInfo(accessToken);
            
            String kakaoId = userInfoJson.split("\"id\":")[1].split(",")[0];
            String nickname = userInfoJson.split("\"nickname\":\"")[1].split("\"")[0];
            
            UserService userService = new UserService();
            UserDTO loginUser = userService.loginKakao(kakaoId, nickname);

            if (loginUser != null) {
                HttpSession session = request.getSession();
                if (loginUser.getSignUpCompleted() == 1) {
                    session.setAttribute("loginUser", loginUser);
                    return "redirect:/main"; // (또는 홈으로 가는 URL)
                    
                } else {
                    session.setAttribute("signupUser", loginUser);
                    session.setAttribute("loginUser", loginUser); 
                    return "redirect:/signup-step0-basic";
                }
            }
        }
        return "redirect:/login";
    }

    // --- 통신 메서드 ---
    private String getAccessToken(String code, String dynamicRedirectUri) throws Exception {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=").append(CLIENT_ID);
        sb.append("&redirect_uri=").append(dynamicRedirectUri);
        sb.append("&code=").append(code);
        sb.append("&client_secret=").append(CLIENT_SECRET);
        bw.write(sb.toString());
        bw.flush();

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) result.append(line);
            br.close();
            bw.close();
            return result.toString().split("\"access_token\":\"")[1].split("\"")[0];
        }
        return null;
    }

    private String getUserInfo(String accessToken) throws Exception {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(userInfoUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) result.append(line);
            br.close();
            return result.toString();
        }
        return null;
    }
}