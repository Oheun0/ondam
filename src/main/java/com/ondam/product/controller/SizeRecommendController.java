package com.ondam.product.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ondam.common.controller.Controller;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductFeatureDAO;
import com.ondam.product.dao.ProductOptionDAO;
import com.ondam.product.dao.ProductSeasonDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.user.dao.UserHobbyDAO;
import com.ondam.user.dao.UserPreferColorDAO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.dto.UserPreferColorDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SizeRecommendController implements Controller {

    private final ProductDAO productDAO = new ProductDAO();
    private final ProductOptionDAO optionDAO = new ProductOptionDAO();
    private final ProductFeatureDAO featureDAO = new ProductFeatureDAO();
    private final ProductSeasonDAO seasonDAO = new ProductSeasonDAO();
    private final UserHobbyDAO hobbyDAO = new UserHobbyDAO();
    private final UserPreferColorDAO colorDAO = new UserPreferColorDAO();
    private static final ResourceBundle rb = ResourceBundle.getBundle("config");
    private final String API_KEY2 = rb.getString("openai.api.key2");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            writeJson(response, false, "로그인이 필요합니다.", "");
            return null;
        }
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 1. 유저 맞춤 데이터 수집
        int userNo = loginUser.getUserNo();
        String userName = loginUser.getUserName();
        String hobbies = hobbyDAO.getUserHobbies(userNo); // 예: "등산"
        
        List<UserPreferColorDTO> colorList = colorDAO.getUserPreferColor(userNo);
        String preferColors = colorList.stream()
                                     .map(UserPreferColorDTO::getPreferColor)
                                     .collect(Collectors.joining(", ")); // 예: "검정색"

        // 2. 상품 상세 데이터 수집
        int productNo = parseIntOrZero(request.getParameter("productNo"));
        ProductDTO product = productDAO.getProductById(productNo);
        if (product == null) {
            writeJson(response, false, "상품 정보를 찾을 수 없습니다.", "");
            return null;
        }

        // 재고가 있는 모든 옵션 리스트
        List<ProductOptionDTO> options = optionDAO.getProductOptionList(productNo);
        String optionStr = options.stream()
                .map(o -> o.getOptionSize() + " / " + o.getOptionColor())
                .collect(Collectors.joining(", "));

        // 특징 및 시즌 정보
        Vector<String> features = featureDAO.getFeaturesByProductNo(productNo);
        Vector<String> seasons = seasonDAO.getSeasonsByProductNo(productNo);
        String productContext = String.format("특징: %s, 적합계절: %s, 핏: %s, 두께: %s", 
                String.join("/", features), String.join("/", seasons), product.getProductFit(), product.getProductThickness());

        // 3. AI 프롬프트 구성 (요구사항 반영)
        String prompt = buildStylistPrompt(userName, loginUser.getUserHeight(), loginUser.getUserWeight(), 
                                           hobbies, preferColors, product.getProductName(), optionStr, productContext);

        // 4. OpenAI 호출 (이미 성공했던 로직 유지)
        String apiKey = (API_KEY2 != null) ? API_KEY2.replaceAll("\\s", "") : "";

        String aiResponse = callOpenAI(apiKey, prompt);
        
        if (aiResponse == null || aiResponse.isEmpty()) {
            writeJson(response, false, "추천 결과를 생성하지 못했습니다.", "");
            return null;
        }

        writeJson(response, true, "", aiResponse);
        return null;
    }

    private String buildStylistPrompt(String name, int h, int w, String hobby, String color, String pName, String options, String context) {
        return "너는 시니어 맞춤 쇼핑몰 '온담'의 인공지능 스타일리스트다.\n"
             + "다음 정보를 바탕으로 고객에게 가장 잘 어울리는 옵션을 추천하고 따뜻하게 설명해주고 반드시 '쉬운 우리말'만 사용하고 영어 단어는 쓰지 마 아예 포함시키지마 답변에. 레귤러핏 이런거 아예 쓰지마\n\n"
             + "### [고객 정보]\n"
             + "- 성함: " + name + "\n"
             + "- 체형: " + h + "cm / " + w + "kg\n"
             + "- 관심사(취미): " + hobby + "\n"
             + "- 선호 색상: " + color + "\n\n"
             + "### [상품 정보]\n"
             + "- 상품명: " + pName + "\n"
             + "- 상세 정보: " + context + "\n"
             + "- 선택 가능 옵션(사이즈/색상): [" + options + "]\n\n"
             + "### [답변 가이드라인 - 반드시 지킬 것]\n"
             + "1. 첫 줄 형식: 추천 사이즈 : {사이즈}({사이즈설명}) / {색상}\n"
             + "   (예: 추천 사이즈 : 95(보통) / 노란색)\n"
             + "2. 반드시 한 줄 띄우고(엔터 두 번), 그 다음 줄에 설명을 적을 것.\n"
             + "3. 설명에는 고객의 '이름'과 '관심사(취미)'를 포함하여 친절하게 1~2줄로 작성할 것.\n"
             + "4. 고객의 선호 색상이 옵션에 있다면 가급적 그 색상을 우선 추천할 것.";
    }

    private String callOpenAI(String apiKey, String prompt) throws Exception {
        URL url = new URL("https://api.openai.com/v1/responses"); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey.trim());
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // [체크!] toJsonString이 프롬프트 내의 줄바꿈과 따옴표를 완벽히 처리해야 합니다.
        String body = "{"
                + "\"model\":\"gpt-4.1-mini\","
                + "\"input\":" + toJsonString(prompt)
                + "}";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        System.err.println("[SizeRecommend] HTTP Status: " + status);

        InputStream is = (status >= 200 && status < 300)
                ? conn.getInputStream() : conn.getErrorStream();
        
        String responseBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        
        // [중요!] 서버의 실제 응답을 콘솔에 찍어서 눈으로 확인해야 합니다.
        System.err.println("[SizeRecommend] 서버 응답 원문: " + responseBody);

        if (status != 200) return null;

        // 여러가지 응답 키(output_text, text, content)를 모두 체크하도록 보강
        String result = null;
        Pattern[] patterns = {
            Pattern.compile("\"output_text\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""),
            Pattern.compile("\"text\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""),
            Pattern.compile("\"content\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"")
        };

        for (Pattern p : patterns) {
            Matcher m = p.matcher(responseBody);
            if (m.find()) {
                result = unescapeJson(m.group(1));
                break;
            }
        }

        if (result == null) {
            System.err.println("[SizeRecommend] 매칭되는 텍스트 필드를 찾지 못함");
        }

        return result;
    }

    private void writeJson(HttpServletResponse response, boolean ok, String message, String result) throws Exception {
        String json = "{\"ok\":" + ok + ",\"message\":" + toJsonString(message) + ",\"result\":" + toJsonString(result) + "}";
        response.getWriter().write(json);
    }

    private String toJsonString(String s) {
        return "\"" + (s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")) + "\"";
    }

    private String unescapeJson(String s) {
        if (s == null) return "";
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            
            // 역슬래시(\)를 만났을 때
            if (ch == '\\' && i + 1 < s.length()) {
                char next = s.charAt(i + 1);
                
                // 1. 유니코드 형태 (XXXX) 처리
                if (next == 'u' && i + 5 < s.length()) {
                    try {
                        String hex = s.substring(i + 2, i + 6);
                        sb.append((char) Integer.parseInt(hex, 16));
                        i += 5; // XXXX 총 6자 점프
                    } catch (Exception e) {
                        sb.append(ch); // 파싱 실패 시 원문 유지
                    }
                } 
                // 2. 줄바꿈 (\n) 처리
                else if (next == 'n') {
                    sb.append('\n');
                    i++;
                } 
                // 3. 따옴표 (\") 처리
                else if (next == '\"') {
                    sb.append('\"');
                    i++;
                } 
                // 4. 역슬래시 자체 (\\) 처리
                else if (next == '\\') {
                    sb.append('\\');
                    i++;
                } else {
                    sb.append(ch);
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private int parseIntOrZero(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }
}