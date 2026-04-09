package com.ondam.ai.service;

import java.io.*;
import java.util.*;
import java.util.Calendar;
import com.ondam.ai.dto.AiRecommendDTO;
import com.ondam.group.dao.FamilyMemberDAO;
import com.ondam.product.dao.*;
import com.ondam.product.dto.*;
import com.ondam.user.dao.*;
import com.ondam.user.dto.*;
// [추가 1] 찜 목록을 가져오기 위해 WishDAO import
import com.ondam.wish.dao.WishDAO; 

public class AiRecommendService {
    private final UserDAO userDAO = new UserDAO();
    private final UserHobbyDAO userHobbyDAO = new UserHobbyDAO();
    private final UserPreferColorDAO userPreferColorDAO = new UserPreferColorDAO();
    private final FamilyMemberDAO familyMemberDAO = new FamilyMemberDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final ProductImageDAO productImageDAO = new ProductImageDAO();
    // [추가 2] WishDAO 객체 생성
    private final WishDAO wishDAO = new WishDAO(); 

    public Vector<AiRecommendDTO> getTodayRecommendations(int userNo, String scriptPath) {
        UserDTO me = userDAO.getUserPhysicalInfo(userNo);
        if (me == null) return new Vector<>();
        
        String myHobbies = userHobbyDAO.getUserHobbies(userNo);
        List<UserPreferColorDTO> colorList = userPreferColorDAO.getUserPreferColor(userNo);
        String myColors = formatColorList(colorList);

        int familyUserNo = familyMemberDAO.getRandomFamilyMemberUserNo(userNo);
        UserDTO family = null;
        if (familyUserNo > 0) {
            family = userDAO.getUserPhysicalInfo(familyUserNo);
        }

        Vector<ProductDTO> activeProducts = productDAO.getAllActiveProducts();
        
        String jsonInput = composeJsonInput(me, myHobbies, myColors, family, activeProducts);
        String rawPythonResult = executePython(scriptPath, jsonInput);

        // [추가 3] 현재 로그인한 유저가 찜한 상품 번호(productNo) 목록을 Set으로 가져옴
        Set<Integer> myWishSet = wishDAO.getWishedProductNos(userNo);

        // [수정] 조립 메서드에 myWishSet도 함께 넘겨줍니다
        return assembleFinalDtoList(rawPythonResult, me, myHobbies, myColors, myWishSet);
    }

    private String composeJsonInput(UserDTO me, String meHobbies, String myColors, UserDTO family, Vector<ProductDTO> activeProducts) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"current_season\":\"").append(getCurrentSeason()).append("\",");
        
        sb.append("\"me\":{");
        sb.append("\"age\":").append(calculateAge(me.getUserBirth())).append(",");
        sb.append("\"height\":").append(me.getUserHeight()).append(",");
        sb.append("\"weight\":").append(me.getUserWeight()).append(",");
        sb.append("\"gender\":").append(me.getUserGender()).append(",");
        sb.append("\"hobbies\":\"").append(escapeJson(meHobbies)).append("\",");
        sb.append("\"pref_colors\":\"").append(escapeJson(myColors)).append("\"");
        sb.append("},");
        
        if (family != null) {
            sb.append("\"family\":{");
            sb.append("\"userName\":\"").append(escapeJson(family.getUserName())).append("\",");
            sb.append("\"age\":").append(calculateAge(family.getUserBirth())).append(",");
            sb.append("\"height\":").append(family.getUserHeight()).append(",");
            sb.append("\"weight\":").append(family.getUserWeight()).append(",");
            sb.append("\"gender\":").append(family.getUserGender());
            sb.append("},");
        } else {
            sb.append("\"family\":null,");
        }

        sb.append("\"active_products\":[");
        for (int i = 0; i < activeProducts.size(); i++) {
            ProductDTO p = activeProducts.get(i);
            sb.append("{");
            sb.append("\"productNo\":").append(p.getProductNo()).append(",");
            sb.append("\"productGender\":").append(p.getProductGender()).append(",");
            sb.append("\"productName\":\"").append(escapeJson(p.getProductName())).append("\"");
            sb.append("}");
            if (i < activeProducts.size() - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

    // [수정] 파라미터에 Set<Integer> myWishSet 추가
    private Vector<AiRecommendDTO> assembleFinalDtoList(String json, UserDTO me, String hobbies, String colors, Set<Integer> myWishSet) {
        Vector<AiRecommendDTO> list = new Vector<>();
        
        if (json == null || !json.contains("[") || json.trim().equals("[]")) {
            AiRecommendDTO emptyDto = new AiRecommendDTO();
            setUserInfo(emptyDto, me, hobbies, colors);
            list.add(emptyDto);
            return list;
        }

        try {
            int startIndex = json.indexOf("[");
            int endIndex = json.lastIndexOf("]");
            String clean = json.substring(startIndex + 1, endIndex).trim();
            
            String[] tokens = clean.split("\\}\\s*,\\s*\\{");
            for (String token : tokens) {
                String row = token.replace("{", "").replace("}", "");
                AiRecommendDTO dto = new AiRecommendDTO();
                setUserInfo(dto, me, hobbies, colors);

                String[] fields = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for (String f : fields) {
                    String[] kv = f.split(":", 2);
                    if(kv.length < 2) continue;
                    String key = kv[0].replace("\"", "").trim();
                    String val = kv[1].replace("\"", "").trim();
                    
                    if (key.equals("productNo")) dto.setProductNo(Integer.parseInt(val));
                    else if (key.equals("phrase")) dto.setPhrase(val);
                    else if (key.equals("target")) dto.setTargetName(val);
                }
                
                ProductDTO p = productDAO.getProductById(dto.getProductNo());
                if (p != null) {
                    dto.setProductName(p.getProductName());
                    dto.setProductBrand(p.getProductBrand());
                    
                    dto.setProductPrice(p.getProductPrice());
                    dto.setProductOriginPrice(p.getProductOriginPrice());
                    dto.setProductWishCount(p.getWishCount());
                    
                    dto.setProductGender(p.getProductGender());
                    dto.setImgFile(productImageDAO.getProductImageFile(p.getProductNo()));
                    
                    // =========================================================
                    // [핵심 추가] 내 찜 목록(myWishSet)에 이 상품의 번호가 들어있다면, true로 세팅!
                    dto.setWishActive(myWishSet.contains(p.getProductNo()));
                    // =========================================================

                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (list.isEmpty()) {
            AiRecommendDTO emptyDto = new AiRecommendDTO();
            setUserInfo(emptyDto, me, hobbies, colors);
            list.add(emptyDto);
        }
        
        return list;
    }

    private void setUserInfo(AiRecommendDTO dto, UserDTO me, String hobbies, String colors) {
        dto.setUserName(me.getUserName());
        dto.setAge(calculateAge(me.getUserBirth()));
        dto.setHeight(me.getUserHeight());
        dto.setWeight(me.getUserWeight());
        dto.setGender(me.getUserGender());
        dto.setUserHobby(hobbies);
        dto.setPrefColor(colors);
    }

    private String formatColorList(List<UserPreferColorDTO> list) {
        if (list == null || list.isEmpty()) return "";
        List<String> colors = new ArrayList<>();
        for (UserPreferColorDTO dto : list) colors.add(dto.getPreferColor());
        return String.join(",", colors);
    }

    private String getCurrentSeason() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (month >= 3 && month <= 5) return "봄";
        if (month >= 6 && month <= 8) return "여름";
        if (month >= 9 && month <= 11) return "가을";
        return "겨울";
    }

    private int calculateAge(String birth) {
        if (birth == null || birth.length() < 4) return 20;
        return Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(birth.substring(0, 4)) + 1;
    }

    private String escapeJson(String data) {
        return data == null ? "" : data.replace("\"", "\\\"");
    }

    private String executePython(String scriptPath, String jsonInput) {
        StringBuilder output = new StringBuilder();
        File tempFile = null;
        try {
            tempFile = File.createTempFile("ai_input_", ".json");
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"))) {
                bw.write(jsonInput);
            }
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath, tempFile.getAbsolutePath());
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) { output.append(line).append("\n"); }
            p.waitFor();
        } catch (Exception e) {} 
        finally { if (tempFile != null && tempFile.exists()) tempFile.delete(); }
        return output.toString().trim();
    }
}