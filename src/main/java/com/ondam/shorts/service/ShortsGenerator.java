package com.ondam.shorts.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

public class ShortsGenerator {
    
    private final ShortsDAO shortsDAO = new ShortsDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public void generateShortsAsync(int vendorNo, int productNo, String realPath) {
        
        // 1. 비동기 실행 전, DB 상태를 선점하여 '0(생성 중)'으로 초기화
        ShortsDTO existing = shortsDAO.getShortByProductNo(productNo);
        if (existing == null) {
            ShortsDTO initialDto = new ShortsDTO();
            initialDto.setVendorNo(vendorNo);
            initialDto.setProductNo(productNo);
            initialDto.setShortsState(0); // 생성 중
            shortsDAO.insertShorts(initialDto);
        } else {
            shortsDAO.updateShortsState(productNo, 0);
        }

        executor.submit(() -> {
            try {
                System.out.println("[ShortsService] 숏폼 생성 시작 - 상품번호: " + productNo);
                
                ProductDTO productDTO = productDAO.getProductById(productNo);
                Vector<String> imageFiles = productDAO.getProductImages(productNo); 

                if (productDTO == null || imageFiles == null || imageFiles.isEmpty()) {
                    System.err.println("[ShortsService] 상품 정보나 이미지가 없어 중단합니다.");
                    shortsDAO.updateShortsState(productNo, -1);
                    return;
                }

                StringBuilder imagesArg = new StringBuilder();
                int imageCount = Math.min(imageFiles.size(), 3); 
                
                for (int i = 0; i < imageCount; i++) {
                    if (i > 0) imagesArg.append(",");
                    imagesArg.append(realPath).append("uploads").append(File.separator)
                             .append("products").append(File.separator).append(imageFiles.get(i));
                }

                String scriptPath = realPath + "scripts" + File.separator + "shorts_generator.py";
                String outputFileName = "shorts_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
                String outputPath = realPath + "uploads" + File.separator + "shorts" + File.separator + outputFileName;
                
                File outputDir = new File(realPath + "uploads" + File.separator + "shorts");
                if (!outputDir.exists()) outputDir.mkdirs();

                ProcessBuilder pb = new ProcessBuilder(
                    "python", scriptPath,
                    "--images", imagesArg.toString(),
                    "--text", productDTO.getProductName(),
                    "--output", outputPath
                );
                
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Python] " + line);
                }

                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    System.out.println("[ShortsService] 생성 성공. 1(공개) 상태로 업데이트.");
                    
                    ShortsDTO resultDto = new ShortsDTO();
                    resultDto.setProductNo(productNo);
                    resultDto.setVideoFile(outputFileName);
                    resultDto.setThumbnailImg(outputFileName.replace(".mp4", "_thumb.jpg"));
                    resultDto.setShortsState(1); // 1: 공개 완료

                    shortsDAO.updateShortsByProductNo(resultDto);
                } else {
                    System.err.println("[ShortsService] 생성 실패. -1로 업데이트.");
                    shortsDAO.updateShortsState(productNo, -1);
                }

            } catch (Exception e) {
                e.printStackTrace();
                shortsDAO.updateShortsState(productNo, -1);
            }
        });
    }
}