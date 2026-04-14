package com.ondam.shorts.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.seller.dao.VendorDAO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

import jakarta.servlet.http.Part;

public class ShortsService {

    private final ShortsDAO dao; 
    private final ShortsGeneratorService generator;
    
    private final VendorDAO vendorDao = new VendorDAO();
    private final ProductDAO productDao = new ProductDAO();
    
    public ShortsService() {
        this.dao = new ShortsDAO();
        this.generator = new ShortsGeneratorService();
    }

    public Vector<ShortsDTO> getShortsList() {
        return dao.getShorts();
    }

    public Vector<ShortsDTO> getPublicAndShuffledShorts() {
        Vector<ShortsDTO> allShorts = dao.getShorts();
        Vector<ShortsDTO> publicShorts = new Vector<>();
        
        for (ShortsDTO dto : allShorts) {
            if (dto.getShortsState() == 1) {
                String vName = vendorDao.getVendorName(dto.getVendorNo());
                String pName = productDao.getProductName(dto.getProductNo());
                int pPrice = productDao.getProductPrice(dto.getProductNo());
                int pOriginPrice = productDao.getProductOriginPrice(dto.getProductNo()); 
                String pImage = productDao.getProductImage(dto.getProductNo()); 
                
                int wishCnt = 0;
                ProductDTO productDTO = productDao.getProductById(dto.getProductNo());
                if (productDTO != null) {
                    wishCnt = productDTO.getWishCount();
                }

                dto.setVendorName(vName != null ? vName : "Unknown Vendor");
                dto.setProductName(pName != null ? pName : "Unknown Product");
                dto.setProductPrice(pPrice);
                dto.setProductOriginPrice(pOriginPrice);
                dto.setImgFile(pImage);
                dto.setWishCount(wishCnt); 
                
                if (pOriginPrice > 0 && pOriginPrice > pPrice) {
                    int rate = (int)(((double)(pOriginPrice - pPrice) / pOriginPrice) * 100);
                    dto.setDiscountRate(rate);
                } else {
                    dto.setDiscountRate(0);
                }
                
                publicShorts.add(dto);
            }
        }
        Collections.shuffle(publicShorts);
        return publicShorts;	
    }

    // [FIXED] AI 영상 생성 시 기존 영상 덮어쓰기 방지 검증 추가
    public String requestGenerateShorts(int vendorNo, int productNo, String title, String content, String realPath) {
        ShortsDTO current = dao.getShortByProductNo(productNo);
        if (current != null) {
            if (current.getShortsState() == 0) {
                return "이미 해당 상품의 영상 제작이 진행 중입니다.";
            } else if (current.getShortsState() == 1 || current.getShortsState() == 2) {
                // 이미 완성된 영상(공개/비공개)이 존재하면 차단
                return "이미 등록된 숏폼 영상이 존재합니다. 다시 생성하려면 기존 영상을 목록에서 삭제해 주세요.";
            }
            // 상태가 -1(생성 실패)인 경우에만 통과시켜서 재시도(덮어쓰기)를 허용함
        }
        
        generator.generateShortsAsync(vendorNo, productNo, title, content, realPath);
        return "success";
    }

    public String removeShortsWithValidation(int vendorNo, int productNo, String webappRootPath) {
        ShortsDTO target = dao.getShortByProductNo(productNo);
        if (target == null) return "삭제할 영상이 없습니다.";
        if (target.getVendorNo() != vendorNo) return "해당 영상을 삭제할 권한이 없습니다.";

        String path = webappRootPath + "uploads" + File.separator + "shorts" + File.separator;
        if (target.getVideoFile() != null && !target.getVideoFile().isEmpty()) {
            new File(path + target.getVideoFile()).delete();
        }
        if (target.getThumbnailImg() != null && !target.getThumbnailImg().isEmpty()) {
            new File(path + target.getThumbnailImg()).delete();
        }
        
        return dao.deleteShorts(target.getShortsNo()) ? "success" : "삭제에 실패했습니다.";
    }

    public String toggleVisibilityWithValidation(int vendorNo, int productNo) {
        ShortsDTO target = dao.getShortByProductNo(productNo);
        if (target == null) return "상태를 변경할 영상이 존재하지 않습니다.";
        if (target.getVendorNo() != vendorNo) return "해당 영상의 상태를 변경할 권한이 없습니다.";

        int newState = (target.getShortsState() == 1) ? 2 : 1;
        return dao.updateShortsState(productNo, newState) ? "success" : "상태 변경에 실패했습니다.";
    }

    // [FIXED] 수동 업로드 시에도 기존 영상 덮어쓰기 방지 검증 추가
    public String uploadManualShorts(int vendorNo, int productNo, String title, String content, Part videoPart, String thumbnailBase64, String webappRootPath) throws Exception {
        
        long maxSize = 60 * 1024 * 1024; 
        if (videoPart == null || videoPart.getSize() == 0) return "업로드할 영상 파일이 없습니다.";
        if (videoPart.getSize() > maxSize) return "영상 파일 크기는 60MB를 초과할 수 없습니다.";

        ShortsDTO existing = dao.getShortByProductNo(productNo);
        if (existing != null) {
            if (existing.getVendorNo() != vendorNo) {
                return "타인의 상품에 영상을 덮어쓸 수 없습니다.";
            }
            if (existing.getShortsState() == 0) {
                return "현재 AI 영상 생성이 진행 중입니다. 완료 후 시도해주세요.";
            }
            if (existing.getShortsState() == 1 || existing.getShortsState() == 2) {
                return "이미 등록된 숏폼 영상이 존재합니다. 새로운 영상을 업로드하려면 기존 영상을 삭제해 주세요.";
            }
        }

        String savePath = webappRootPath + "uploads" + File.separator + "shorts";
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String videoName = "shorts_manual_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
        videoPart.write(savePath + File.separator + videoName);

        String thumbName = "default_thumb.jpg";
        if (thumbnailBase64 != null && !thumbnailBase64.isEmpty()) {
            try {
                String base64Data = thumbnailBase64.split(",")[1];
                byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                thumbName = "shorts_thumb_manual_" + productNo + "_" + System.currentTimeMillis() + ".jpg";
                String fullThumbPath = savePath + File.separator + thumbName;
                
                Files.write(Paths.get(fullThumbPath), decodedBytes);
            } catch (Exception e) {
                System.err.println("[ShortsService] Base64 디코딩 실패, 기본 이미지 사용: " + e.getMessage());
                thumbName = "default_thumb.jpg"; 
            }
        }

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