package com.ondam.shorts.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

public class ShortsGeneratorService {
    
    private final ShortsDAO shortsDAO = new ShortsDAO();
    private final ProductDAO productDAO = new ProductDAO();
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(3, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true); 
        return t;
    });

    public void generateShortsAsync(int vendorNo, int productNo, String title, String content, String webappRootPath) {
        
        ShortsDTO existing = shortsDAO.getShortByProductNo(productNo);
        if (existing == null) {
            ShortsDTO initialDto = new ShortsDTO();
            initialDto.setVendorNo(vendorNo);
            initialDto.setProductNo(productNo);
            initialDto.setShortsTitle(title);     
            initialDto.setShortsContent(content); 
            
            // [수정] NOT NULL 제약조건 회피를 위해 임시 빈 문자열 주입
            initialDto.setVideoFile("");       
            initialDto.setThumbnailImg("");    
            
            initialDto.setShortsState(0); 
            shortsDAO.insertShorts(initialDto);
        } else {
            existing.setShortsTitle(title);
            existing.setShortsContent(content);
            existing.setShortsState(0);
            shortsDAO.updateShortsByProductNo(existing);
        }

        executor.submit(() -> {
            Process process = null;
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
                    imagesArg.append(webappRootPath).append("uploads").append(File.separator)
                             .append("products").append(File.separator).append(imageFiles.get(i));
                }

                String scriptPath = webappRootPath + "scripts" + File.separator + "shorts_generator.py";
                String outputFileName = "shorts_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
                String outputPath = webappRootPath + "uploads" + File.separator + "shorts" + File.separator + outputFileName;
                
                File outputDir = new File(webappRootPath + "uploads" + File.separator + "shorts");
                if (!outputDir.exists() && !outputDir.mkdirs()) {
                    throw new RuntimeException("디렉토리 생성 실패: " + outputDir.getAbsolutePath());
                }

                ProcessBuilder pb = new ProcessBuilder(
                    "python", scriptPath,
                    "--images", imagesArg.toString(),
                    "--text", title != null ? title : productDTO.getProductName(),
                    "--output", outputPath
                );
                
                pb.redirectErrorStream(true);
                process = pb.start();

                final Process currentProcess = process;
                Thread streamGobbler = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(currentProcess.getInputStream(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println("[Python] " + line);
                        }
                    } catch (Exception e) {}
                });
                streamGobbler.setDaemon(true);
                streamGobbler.start();

                boolean finished = process.waitFor(5, TimeUnit.MINUTES);
                
                if (!finished) {
                    process.destroyForcibly();
                    System.err.println("[ShortsService] 파이썬 스크립트 실행 시간 초과(Timeout). 강제 종료됨.");
                    shortsDAO.updateShortsState(productNo, -1);
                    return;
                }

                int exitCode = process.exitValue();
                if (exitCode == 0) {
                    ShortsDTO resultDto = new ShortsDTO();
                    resultDto.setProductNo(productNo);
                    resultDto.setVideoFile(outputFileName);
                    resultDto.setThumbnailImg(outputFileName.replace(".mp4", "_thumb.jpg"));
                    resultDto.setShortsTitle(title);
                    resultDto.setShortsContent(content);
                    resultDto.setShortsState(1);

                    shortsDAO.updateShortsByProductNo(resultDto);
                } else {
                    shortsDAO.updateShortsState(productNo, -1);
                }

            } catch (Exception e) {
                System.err.println("[ShortsService] Exception 발생: " + e.getMessage());
                e.printStackTrace();
                shortsDAO.updateShortsState(productNo, -1);
            } finally {
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                }
            }
        });
    }
}