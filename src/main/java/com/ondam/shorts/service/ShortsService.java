package com.ondam.shorts.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.Vector;
import jakarta.servlet.http.Part;

import com.ondam.product.dao.ProductDAO;
import com.ondam.seller.dao.VendorDAO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

public class ShortsService {

    private final ShortsDAO dao; 
    private final ShortsGenerator generator;
    
    // [추가] 조립을 위한 타 영역 DAO 선언
    private final VendorDAO vendorDao = new VendorDAO();
    private final ProductDAO productDao = new ProductDAO();
    
    public ShortsService() {
        this.dao = new ShortsDAO();
        this.generator = new ShortsGenerator();
    }

    public Vector<ShortsDTO> getShortsList() {
        return dao.getShorts();
    }

 // [수정] 메인 화면용 쇼츠 리스트 가져오기 (데이터 조립 포함)
    public Vector<ShortsDTO> getPublicAndShuffledShorts() {
        Vector<ShortsDTO> allShorts = dao.getShorts();
        Vector<ShortsDTO> publicShorts = new Vector<>();
        
        for (ShortsDTO dto : allShorts) {
            if (dto.getShortsState() == 1) {
                // [데이터 조립] 
                // 각각의 외래키(vendorNo, productNo)를 이용해 이름을 조회해옵니다.
                // 각 DAO에는 getVendorName(int), getProductName(int) 메서드가 있다고 가정합니다.
                String vName = vendorDao.getVendorName(dto.getVendorNo());
                String pName = productDao.getProductName(dto.getProductNo());
                int pPrice = productDao.getProductPrice(dto.getProductNo());
                
                dto.setVendorName(vName != null ? vName : "Unknown Vendor");
                dto.setProductName(pName != null ? pName : "Unknown Product");
                dto.setProductPrice(pPrice != 0 ? pPrice : null);
                
                publicShorts.add(dto);
            }
        }
        Collections.shuffle(publicShorts);
        return publicShorts;	
    }

    public String requestGenerateShorts(int vendorNo, int productNo, String realPath) {
        ShortsDTO current = dao.getShortByProductNo(productNo);
        if (current != null && current.getShortsState() == 0) {
            return "이미 해당 상품의 영상 제작이 진행 중입니다.";
        }
        generator.generateShortsAsync(vendorNo, productNo, realPath);
        return "success";
    }

    public String removeShortsWithValidation(int vendorNo, int productNo, String realPath) {
        ShortsDTO target = dao.getShortByProductNo(productNo);
        if (target == null) return "삭제할 영상이 없습니다.";
        if (target.getVendorNo() != vendorNo) return "해당 영상을 삭제할 권한이 없습니다.";

        String path = realPath + "uploads" + File.separator + "shorts" + File.separator;
        if (target.getVideoFile() != null) new File(path + target.getVideoFile()).delete();
        if (target.getThumbnailImg() != null) new File(path + target.getThumbnailImg()).delete();
        
        return dao.deleteShorts(target.getShortsNo()) ? "success" : "삭제에 실패했습니다.";
    }

    public String toggleVisibilityWithValidation(int vendorNo, int productNo) {
        ShortsDTO target = dao.getShortByProductNo(productNo);
        if (target == null) return "상태를 변경할 영상이 존재하지 않습니다.";
        if (target.getVendorNo() != vendorNo) return "해당 영상의 상태를 변경할 권한이 없습니다.";

        int newState = (target.getShortsState() == 1) ? 2 : 1;
        return dao.updateShortsState(productNo, newState) ? "success" : "상태 변경에 실패했습니다.";
    }

    // [로직 변경] FFmpeg 없이 Base64 디코딩으로 썸네일 생성
    public String uploadManualShorts(int vendorNo, int productNo, String title, String content, Part videoPart, String thumbnailBase64, String realPath) throws Exception {
        
        long maxSize = 60 * 1024 * 1024; // 60MB
        if (videoPart == null || videoPart.getSize() == 0) return "업로드할 영상 파일이 없습니다.";
        if (videoPart.getSize() > maxSize) return "영상 파일 크기는 60MB를 초과할 수 없습니다.";

        ShortsDTO existing = dao.getShortByProductNo(productNo);
        if (existing != null && existing.getVendorNo() != vendorNo) {
            return "타인의 상품에 영상을 덮어쓸 수 없습니다.";
        }

        String savePath = realPath + "uploads" + File.separator + "shorts";
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // 1. 영상 저장
        String videoName = "shorts_manual_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
        videoPart.write(savePath + File.separator + videoName);

        // 2. Base64 썸네일 변환 및 저장
        String thumbName = "default_thumb.jpg";
        if (thumbnailBase64 != null && !thumbnailBase64.isEmpty()) {
            try {
                // "data:image/jpeg;base64,/9j/4AAQ..." 형태에서 쉼표 뒷부분 실제 데이터만 분리
                String base64Data = thumbnailBase64.split(",")[1];
                byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                thumbName = "shorts_thumb_manual_" + productNo + "_" + System.currentTimeMillis() + ".jpg";
                String fullThumbPath = savePath + File.separator + thumbName;
                
                Files.write(Paths.get(fullThumbPath), decodedBytes);
            } catch (Exception e) {
                System.err.println("[ShortsService] Base64 디코딩 실패, 기본 이미지 사용: " + e.getMessage());
                thumbName = "default_thumb.jpg"; // 실패 시 안전하게 기본 이미지로 폴백
            }
        }

        // 3. DB 기록
        ShortsDTO dto = new ShortsDTO();
        dto.setProductNo(productNo);
        dto.setVendorNo(vendorNo);
        dto.setVideoFile(videoName);
        dto.setThumbnailImg(thumbName); 
        dto.setShortsTitle(title != null ? title : "");     
        dto.setShortsContent(content != null ? content : ""); 
        dto.setShortsState(1);

        boolean success = (existing == null) ? dao.insertShorts(dto) : dao.updateManualShorts(dto);
        return success ? "success" : "DB 기록에 실패했습니다.";
    }
}