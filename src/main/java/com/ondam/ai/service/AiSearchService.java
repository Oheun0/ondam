package com.ondam.ai.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Vector;
import com.ondam.ai.dto.AiSearchDTO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;

public class AiSearchService {
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * 특정 상품의 대표 이미지를 인덱스에 즉시 추가
     */
    public boolean updateSingleProductIndex(String scriptPath, int productNo, String imgFile) {
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        command.add("add_single");
        command.add("--ids");
        command.add(String.valueOf(productNo));
        command.add("--imgs");
        command.add(imgFile);
        command.add("--json");

        String result = executePython(command);
        return result != null && result.contains("\"success\": true");
    }

    /**
     * 전체 인덱스 관리 (빌드)
     */
    public boolean manageIndex(String scriptPath, String mode, String imagePath, String productNo, String productName) {
        Vector<String> command = new Vector<>();
        command.add("python");
        command.add(scriptPath);
        command.add("--mode");
        command.add(mode.equals("search") ? "search" : "build");

        // 💡 [수정됨] 빌드 모드일 때, 파이썬이 폴더를 뒤지지 못하게 DB 명단을 넘겨줌
        if ("build".equals(mode)) {
            com.ondam.product.dao.ProductImageDAO imageDAO = new com.ondam.product.dao.ProductImageDAO();
            Vector<com.ondam.product.dto.ProductImageDTO> mainImages = imageDAO.getAllMainImages();
            
            if (mainImages.isEmpty()) return false;
            
            StringBuilder ids = new StringBuilder();
            StringBuilder imgs = new StringBuilder();
            for (int i = 0; i < mainImages.size(); i++) {
                ids.append(mainImages.get(i).getProductNo());
                imgs.append(mainImages.get(i).getImgFile());
                if (i < mainImages.size() - 1) {
                    ids.append(",");
                    imgs.append(",");
                }
            }
            command.add("--ids");
            command.add(ids.toString());
            command.add("--imgs");
            command.add(imgs.toString());
        } else {
            if (imagePath != null) { command.add("--image"); command.add(imagePath); }
            if (productNo != null) { command.add("--product-id"); command.add(productNo); }
        }

        command.add("--json");

        String result = executePython(command);
        return result != null && (result.contains("success") || result.contains("true"));
    }

    /**
     * 유사 상품 검색 실행 및 DB 정보 조립
     */
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

        String jsonResult = executePython(command);
        if (jsonResult == null || jsonResult.isEmpty()) return resultList;

        Vector<ParsedItem> items = parsePythonJson(jsonResult);
        java.util.Set<Integer> addedProducts = new java.util.HashSet<>();

        for (ParsedItem item : items) {
            // 💡 [핵심 1] 정상적인 상품이 딱 10개가 채워지면 더 이상 찾지 않고 즉시 종료!
            if (resultList.size() >= 10) {
                break;
            }

            // 💡 [핵심 2] 이미 리스트에 들어간 상품 번호(중복)면 건너뜀!
            if (addedProducts.contains(item.productNo)) {
                continue;
            }

            ProductDTO p = productDAO.getProductById(item.productNo);
            
            // 상태가 1(판매중)일 때만 추가
            if (p != null && p.getProductState() == 1) { 
                AiSearchDTO dto = new AiSearchDTO();
                dto.setProductNo(p.getProductNo());
                dto.setProductName(p.getProductName());
                dto.setProductBrand(p.getProductBrand());
                dto.setProductPrice(p.getProductPrice());
                dto.setProductOriginPrice(p.getProductOriginPrice());
                dto.setScore(item.score);
                dto.setImgFile(productDAO.getProductImage(p.getProductNo())); 
                
                resultList.add(dto);
                addedProducts.add(item.productNo); // 💡 중복 방지를 위해 번호 기억
            }
        }
        // 최종 반환되는 결과 개수 확인
        System.out.println("[AiSearch Debug] 프론트로 반환할 최종 검색 결과 개수: " + resultList.size());
        return resultList;
    }

    private String executePython(Vector<String> command) {
        StringBuilder sb = new StringBuilder();
        try {
            // ? 실행하는 파이썬 명령어 확인
            System.out.println("[AiSearch Debug] 파이썬 실행 명령어: " + String.join(" ", command));

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                // 파이썬이 뱉는 모든 문장(에러 포함)을 이클립스 콘솔에 무조건 출력
                System.out.println("[Python Raw Output] " + line);
                
                if (line.trim().startsWith("{") || line.trim().startsWith("[")) {
                    sb.append(line);
                }
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 최종적으로 자바가 받아낸 JSON 문자열 확인
        System.out.println("[AiSearch Debug] 추출된 JSON: " + sb.toString().trim());
        return sb.toString().trim();
    }

    private Vector<ParsedItem> parsePythonJson(String json) {
        Vector<ParsedItem> items = new Vector<>();
        try {
            String clean = json.replaceAll("[\\\\\\[\\\\\\]\\\" ]", "");
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

    private static class ParsedItem {
        int productNo;
        double score;
    }
}