package com.ondam.shipment.controller;

import java.io.IOException;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.shipment.dto.ShipmentDTO;
import com.ondam.shipment.service.ShipmentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerShipmentController implements Controller {

    private final ShipmentService shipmentService = new ShipmentService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        SellerDTO loginSeller = (session == null) ? null : (SellerDTO) session.getAttribute("loginSeller");
        if (loginSeller == null) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"판매자 로그인 후 이용해주세요.\"}");
            return null;
        }

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"action 파라미터가 필요합니다.\"}");
            return null;
        }

        try {
            switch (action.trim()) {
                case "register":
                    registerShipment(request, response);
                    break;
                case "status":
                    updateShipmentStatus(request, response);
                    break;
                case "list":
                    listShipments(request, response);
                    break;
                default:
                    sendJson(response, "{\"status\":\"error\",\"message\":\"지원하지 않는 action 입니다.\"}");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJson(response, "{\"status\":\"error\",\"message\":\"서버 처리 중 오류가 발생했습니다.\"}");
        }

        return null;
    }

    private void registerShipment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderItemNoStr = request.getParameter("orderItemNo");
        String carrierCode = request.getParameter("carrierCode");
        String trackingNo = request.getParameter("trackingNo");

        if (isBlank(orderItemNoStr) || isBlank(carrierCode) || isBlank(trackingNo)) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"orderItemNo, carrierCode, trackingNo가 필요합니다.\"}");
            return;
        }

        int orderItemNo;
        try {
            orderItemNo = Integer.parseInt(orderItemNoStr.trim());
        } catch (NumberFormatException e) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"orderItemNo는 숫자여야 합니다.\"}");
            return;
        }

        ShipmentDTO dto = new ShipmentDTO();
        dto.setOrderItemNo(orderItemNo);
        dto.setCarrierCode(carrierCode.trim());
        dto.setTrackingNo(trackingNo.trim());

        int vendorNo = ((SellerDTO) request.getSession(false).getAttribute("loginSeller")).getVendorNo();
        boolean ok = shipmentService.registerShipment(vendorNo, dto);
        if (ok) {
            sendJson(response, "{\"status\":\"success\",\"message\":\"송장 등록이 완료되었습니다.\"}");
        } else {
            sendJson(response, "{\"status\":\"error\",\"message\":\"송장 등록 권한이 없거나 처리에 실패했습니다.\"}");
        }
    }

    private void updateShipmentStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderItemNoStr = request.getParameter("orderItemNo");
        String statusStr = request.getParameter("shipmentStatus");

        if (isBlank(orderItemNoStr) || isBlank(statusStr)) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"orderItemNo, shipmentStatus가 필요합니다.\"}");
            return;
        }

        int orderItemNo;
        int shipmentStatus;
        try {
            orderItemNo = Integer.parseInt(orderItemNoStr.trim());
            shipmentStatus = Integer.parseInt(statusStr.trim());
        } catch (NumberFormatException e) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"숫자 파라미터 형식이 올바르지 않습니다.\"}");
            return;
        }

        if (shipmentStatus < 1 || shipmentStatus > 3) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"shipmentStatus는 1~3만 허용됩니다.\"}");
            return;
        }

        int vendorNo = ((SellerDTO) request.getSession(false).getAttribute("loginSeller")).getVendorNo();
        boolean ok = shipmentService.changeShipmentStatus(vendorNo, orderItemNo, shipmentStatus);
        if (ok) {
            sendJson(response, "{\"status\":\"success\",\"message\":\"배송 상태가 변경되었습니다.\"}");
        } else {
            sendJson(response, "{\"status\":\"error\",\"message\":\"배송 상태 변경 권한이 없거나 처리에 실패했습니다.\"}");
        }
    }

    private void listShipments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderNoStr = request.getParameter("orderNo");
        if (isBlank(orderNoStr)) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"orderNo가 필요합니다.\"}");
            return;
        }

        int orderNo;
        try {
            orderNo = Integer.parseInt(orderNoStr.trim());
        } catch (NumberFormatException e) {
            sendJson(response, "{\"status\":\"error\",\"message\":\"orderNo는 숫자여야 합니다.\"}");
            return;
        }

        int vendorNo = ((SellerDTO) request.getSession(false).getAttribute("loginSeller")).getVendorNo();
        Vector<ShipmentDTO> list = shipmentService.getShipmentsByOrderNo(vendorNo, orderNo);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"success\",\"message\":\"ok\",\"data\":[");
        for (int i = 0; i < list.size(); i++) {
            ShipmentDTO dto = list.get(i);
            if (i > 0) {
                sb.append(",");
            }
            sb.append("{")
              .append("\"shipmentNo\":").append(dto.getShipmentNo()).append(",")
              .append("\"orderItemNo\":").append(dto.getOrderItemNo()).append(",")
              .append("\"orderNo\":").append(dto.getOrderNo()).append(",")
              .append("\"productNo\":").append(dto.getProductNo()).append(",")
              .append("\"vendorNo\":").append(dto.getVendorNo()).append(",")
              .append("\"snapProductName\":\"").append(escapeJson(dto.getSnapProductName())).append("\",")
              .append("\"carrierCode\":\"").append(escapeJson(dto.getCarrierCode())).append("\",")
              .append("\"trackingNo\":\"").append(escapeJson(dto.getTrackingNo())).append("\",")
              .append("\"shipmentStatus\":").append(dto.getShipmentStatus()).append(",")
              .append("\"shippedAt\":\"").append(escapeJson(dto.getShippedAt())).append("\",")
              .append("\"deliveredAt\":\"").append(escapeJson(dto.getDeliveredAt())).append("\"")
              .append("}");
        }
        sb.append("]}");
        sendJson(response, sb.toString());
    }

    private void sendJson(HttpServletResponse response, String body) throws IOException {
        response.getWriter().write(body);
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    private String escapeJson(String v) {
        if (v == null) {
            return "";
        }
        return v.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

