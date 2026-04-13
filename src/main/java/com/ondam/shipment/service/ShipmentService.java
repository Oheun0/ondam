package com.ondam.shipment.service;

import java.util.Vector;

import com.ondam.shipment.dao.ShipmentDAO;
import com.ondam.shipment.dto.ShipmentDTO;

public class ShipmentService {

    private ShipmentDAO dao;

    public ShipmentService() {
        this.dao = new ShipmentDAO();
    }

    public boolean registerShipment(int vendorNo, ShipmentDTO dto) {
        if (!dao.isOrderItemOwnedByVendor(dto.getOrderItemNo(), vendorNo)) {
            return false;
        }

        boolean ok = dao.insertShipment(dto);
        if (ok) {
            dao.syncOrderDeliveryStateByOrderItemNo(dto.getOrderItemNo());
        }
        return ok;
    }

    public boolean changeShipmentStatus(int vendorNo, int orderItemNo, int shipmentStatus) {
        if (!dao.isOrderItemOwnedByVendor(orderItemNo, vendorNo)) {
            return false;
        }

        boolean ok = dao.updateShipmentStatus(orderItemNo, shipmentStatus);
        if (ok) {
            dao.syncOrderDeliveryStateByOrderItemNo(orderItemNo);
        }
        return ok;
    }

    public Vector<ShipmentDTO> getShipmentsByOrderNo(int vendorNo, int orderNo) {
        return dao.getShipmentsByOrderNoAndVendorNo(orderNo, vendorNo);
    }

    public boolean canAccessOrderItem(int vendorNo, int orderItemNo) {
        return dao.isOrderItemOwnedByVendor(orderItemNo, vendorNo);
    }
}

