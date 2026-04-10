package com.ondam.ai.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import com.ondam.ai.dto.AiSearchDTO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;

public class AiSearchService {
    private final ProductDAO productDAO = new ProductDAO();

    public boolean manageIndex(String scriptPath, String mode, String imagePath, String productNo, String productName) {
        System.out.println("[Service Debug] 인덱스 관리 모드: " + mode);
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        command.add(mode.equals("search") ? "search" : "build");
        if (imagePath != null) { command.add("--image"); command.add(imagePath); }
        if (productNo != null) { command.add("--product-id"); command.add(productNo); }
        command.add("--json");

        String result = executePython(command);
        return result != null && (result.contains("success") || result.contains("true"));
    }

    public Vector<AiSearchDTO> searchProducts(String scriptPath, String queryImagePath) {
        Vector<AiSearchDTO> resultList = new Vector<>();
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        command.add("search");
        command.add("--image");
        command.add(queryImagePath);
        command.add("--json");

        String rawData = executePython(command);
        if (rawData == null || rawData.isEmpty() || rawData.contains("error")) {
            System.out.println("[Service Debug] 파이썬 결과 없음 혹은 에러 발생");
            return resultList;
        }

        Vector<ParsedItem> parsedItems = parsePythonJson(rawData);
        Set<Integer> duplicateCheck = new HashSet<>();
        int currentRank = 1;

        for (ParsedItem item : parsedItems) {
            if (duplicateCheck.contains(item.productNo)) continue;
            ProductDTO pDto = productDAO.getProductById(item.productNo);
            if (pDto != null) {
                AiSearchDTO searchDto = new AiSearchDTO();
                searchDto.setRank(currentRank++);
                searchDto.setScore(item.score);
                searchDto.setProductNo(item.productNo);
                searchDto.setProductName(pDto.getProductName());
                searchDto.setProductPrice(pDto.getProductPrice());
                searchDto.setImgFile(productDAO.getProductImage(item.productNo));
                searchDto.setProductOriginPrice(pDto.getProductOriginPrice());
                searchDto.setProductBrand(pDto.getProductBrand());
                resultList.add(searchDto);
                duplicateCheck.add(item.productNo);
            }
        }
        return resultList;
    }

 // AiSearchService.java 내 수정 및 추가될 부분
    private String executePython(Vector<String> command) {
        StringBuilder sb = new StringBuilder();
        try {
            System.out.println("[Debug] Command Executed: " + String.join(" ", command));
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true); // 에러 스트림을 일반 스트림으로 합쳐서 출력 확인
            
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            
            String line;
            while ((line = br.readLine()) != null) {
                // 모든 파이썬 출력을 자바 콘솔에 그대로 복사
                System.out.println("[Python stdout/err] " + line);
                
                // JSON 데이터만 선별 (결과 파싱용)
                if (line.trim().startsWith("{") || line.trim().startsWith("[")) {
                    sb.append(line);
                }
            }
            
            int exitCode = p.waitFor();
            System.out.println("[Debug] Python Process Finished. Exit Code: " + exitCode);
            
        } catch (Exception e) {
            System.err.println("[Critical Error] Failed to execute Python process!");
            e.printStackTrace();
        }
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
        } catch (Exception e) { System.out.println("[Error] JSON 파싱 실패"); }
        return items;
    }
    private static class ParsedItem { int productNo; double score; }
}