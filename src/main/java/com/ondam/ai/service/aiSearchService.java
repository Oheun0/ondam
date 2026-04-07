package com.ondam.ai.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import com.ondam.ai.dto.aiSearchDTO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductSeasonDAO;
import com.ondam.product.dto.ProductDTO;

public class aiSearchService {
    private final ProductDAO productDAO = new ProductDAO();
    private final ProductSeasonDAO seasonDAO = new ProductSeasonDAO();

    /**
     * 인덱스 관리 메서드
     * [수정] 컨트롤러에서 add, delete 등이 들어와도 파이썬은 항상 'build'로 실행하여 
     * 폴더 상태를 최신화(능동적 동기화)합니다.
     */
    public boolean manageIndex(String scriptPath, String mode, String imagePath, String productNo, String productName) {
        System.out.println("[AI Service] 인덱스 관리 요청 (Original Mode: " + mode + ")");
        
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        
        // [핵심 수정] 파이썬에 전달하는 모드는 항상 'build' 혹은 'search'여야 합니다.
        // add, delete, build 모두 폴더를 스캔해서 인덱스를 새로 고치는 'build'로 처리합니다.
        if (mode.equals("search")) {
            command.add("search");
        } else {
            command.add("build"); 
        }

        if (imagePath != null) { command.add("--image"); command.add(imagePath); }
        if (productNo != null) { command.add("--product-id"); command.add(productNo); }
        if (productName != null) { command.add("--name"); command.add(productName); }
        command.add("--json");

        String result = executePython(command);
        return result != null && (result.contains("success") || result.contains("true"));
    }

    /**
     * 이미지 검색 및 데이터 조립 (중복 제거 및 계절 정보 포함)
     */
    public Vector<aiSearchDTO> searchProducts(String scriptPath, String queryImagePath) {
        Vector<aiSearchDTO> resultList = new Vector<>();
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        command.add("search");
        command.add("--image");
        command.add(queryImagePath);
        command.add("--json");

        String rawData = executePython(command);
        if (rawData == null || rawData.isEmpty() || rawData.contains("error")) return resultList;

        Vector<ParsedItem> parsedItems = parsePythonJson(rawData);
        Set<Integer> duplicateCheck = new HashSet<>();
        int currentRank = 1;

        for (ParsedItem item : parsedItems) {
            if (duplicateCheck.contains(item.productNo)) continue;

            ProductDTO pDto = productDAO.getProductById(item.productNo);
            if (pDto != null) {
                aiSearchDTO searchDto = new aiSearchDTO();
                searchDto.setRank(currentRank++);
                searchDto.setScore(item.score);
                searchDto.setProductNo(item.productNo);
                searchDto.setProductName(pDto.getProductName());
                searchDto.setProductPrice(pDto.getProductPrice());
                searchDto.setImgFile(productDAO.getProductImage(item.productNo));
                
                // 계절 정보 결합 (Vector<String>)[cite: 5, 6]
                searchDto.setSeasons(seasonDAO.getSeasonsByProductNo(item.productNo));
                
                resultList.add(searchDto);
                duplicateCheck.add(item.productNo);
            }
        }
        return resultList;
    }

    private String executePython(Vector<String> command) {
        StringBuilder sb = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("[Python Log] " + line);
                if (line.trim().startsWith("{") || line.trim().startsWith("[")) {
                    sb.append(line);
                }
            }
            p.waitFor();
        } catch (Exception e) { e.printStackTrace(); }
        return sb.toString().trim();
    }

    private Vector<ParsedItem> parsePythonJson(String json) {
        Vector<ParsedItem> items = new Vector<>();
        try {
            String clean = json.replaceAll("[\\[\\]\" ]", "");
            String[] tokens = clean.split("\\},\\{");
            for (String token : tokens) {
                String row = token.replace("{", "").replace("}", "");
                ParsedItem pi = new ParsedItem();
                for (String field : row.split(",")) {
                    String[] kv = field.split(":");
                    if (kv[0].equals("product_id")) pi.productNo = Integer.parseInt(kv[1]);
                    else if (kv[0].equals("score")) pi.score = Double.parseDouble(kv[1]);
                }
                items.add(pi);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return items;
    }

    private static class ParsedItem { int productNo; double score; }
}