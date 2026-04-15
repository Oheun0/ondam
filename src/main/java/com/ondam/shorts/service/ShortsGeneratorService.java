package com.ondam.shorts.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

import jakarta.servlet.http.Part;

public class ShortsGeneratorService {
    
    private final ShortsDAO shortsDAO = new ShortsDAO();
    private final ProductDAO productDAO = new ProductDAO();
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(3, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true); 
        return t;
    });

    public String requestGenerateShorts(int vendorNo, int productNo, String title, String content, String webappRootPath) {
        generateShortsAsync(vendorNo, productNo, title, content, webappRootPath);
        return "success";
    }

    public void generateShortsAsync(int vendorNo, int productNo, String title, String content, String webappRootPath) {
        
        // 💡 [개선] 스레드 시작 전에 파일명을 미리 결정합니다.
        String outputFileName = "shorts_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
        String thumbnailName = outputFileName.replace(".mp4", "_thumb.jpg");

        ShortsDTO existing = shortsDAO.getShortByProductNo(productNo);
        if (existing == null) {
            ShortsDTO initialDto = new ShortsDTO();
            initialDto.setVendorNo(vendorNo);
            initialDto.setProductNo(productNo);
            initialDto.setShortsTitle(title);     
            initialDto.setShortsContent(content); 
            
            // 💡 등록 즉시 파일명을 DB에 기입하여 UI에서 경로를 미리 알 수 있게 합니다.
            initialDto.setVideoFile(outputFileName);       
            initialDto.setThumbnailImg(thumbnailName);    
            
            initialDto.setShortsState(0); // 생성 중 상태
            shortsDAO.insertShorts(initialDto);
        } else {
            existing.setShortsTitle(title);
            existing.setShortsContent(content);
            // 💡 기존 데이터 수정 시에도 새로운 파일명을 미리 적용합니다.
            existing.setVideoFile(outputFileName);
            existing.setThumbnailImg(thumbnailName);
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
                // 💡 미리 정의된 outputFileName을 사용하여 경로를 설정합니다.
                String outputPath = webappRootPath + "uploads" + File.separator + "shorts" + File.separator + outputFileName;
                
                File outputDir = new File(webappRootPath + "uploads" + File.separator + "shorts");
                if (!outputDir.exists() && !outputDir.mkdirs()) {
                    throw new RuntimeException("디렉토리 생성 실패: " + outputDir.getAbsolutePath());
                }

                // 배경음악 랜덤 선택 로직
                String audioDirPath = webappRootPath + "uploads" + File.separator + "audio";
                File audioDir = new File(audioDirPath);
                String selectedAudioPath = null;

                if (audioDir.exists() && audioDir.isDirectory()) {
                    File[] mp3Files = audioDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
                    if (mp3Files != null && mp3Files.length > 0) {
                        int randomIndex = new Random().nextInt(mp3Files.length);
                        selectedAudioPath = mp3Files[randomIndex].getAbsolutePath();
                    }
                }

                List<String> command = new ArrayList<>();
                command.add("python");
                command.add(scriptPath);
                command.add("--images"); command.add(imagesArg.toString());
                command.add("--text"); command.add(title != null ? title : productDTO.getProductName());
                command.add("--output"); command.add(outputPath);
                
                if (selectedAudioPath != null) {
                    command.add("--audio");
                    command.add(selectedAudioPath);
                }

                ProcessBuilder pb = new ProcessBuilder(command);
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
                    System.err.println("[ShortsService] 파이썬 스크립트 실행 시간 초과.");
                    shortsDAO.updateShortsState(productNo, -1);
                    return;
                }

                int exitCode = process.exitValue();
                if (exitCode == 0) {
                    // 💡 생성이 완료되면 상태만 1(공개)로 변경합니다. 파일명은 이미 DB에 있습니다.
                    shortsDAO.updateShortsState(productNo, 1);
                } else {
                    shortsDAO.updateShortsState(productNo, -1);
                }

            } catch (Exception e) {
                System.err.println("[ShortsService] Exception 발생: " + e.getMessage());
                shortsDAO.updateShortsState(productNo, -1);
            } finally {
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                }
            }
        });
    }

    public String removeShortsWithValidation(int vendorNo, int productNo, String webappRootPath) {
        ShortsDTO dto = shortsDAO.getShortByProductNo(productNo);
        if (dto != null && dto.getVendorNo() == vendorNo) {
            // 물리 파일 삭제 로직
            String videoPath = webappRootPath + "uploads" + File.separator + "shorts" + File.separator + dto.getVideoFile();
            String thumbPath = webappRootPath + "uploads" + File.separator + "shorts" + File.separator + dto.getThumbnailImg();
            new File(videoPath).delete();
            new File(thumbPath).delete();
            
            shortsDAO.deleteShorts(dto.getShortsNo());
            return "success";
        }
        return "삭제 권한이 없거나 데이터가 없습니다.";
    }

    public String toggleVisibilityWithValidation(int vendorNo, int productNo) {
        ShortsDTO dto = shortsDAO.getShortByProductNo(productNo);
        if (dto != null && dto.getVendorNo() == vendorNo) {
            int newState = (dto.getShortsState() == 1) ? 2 : 1;
            shortsDAO.updateShortsState(productNo, newState);
            return "success";
        }
        return "상태 변경 권한이 없습니다.";
    }

    public String uploadManualShorts(int vendorNo, int productNo, String title, String content, Part videoPart, String thumbnailBase64, String webappRootPath) {
        // 수동 업로드 구현 로직 (생략 가능하나 구조 유지)
        return "success";
    }
}