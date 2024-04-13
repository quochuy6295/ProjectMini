package org.example.repository;

import org.example.common.staticdata.PurchaseOrderStatus;
import org.example.entity.PurchaseOrder;
import org.example.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    PurchaseOrder findPurchaseOrderByVendorAndProductNameAndPurchaseOrderStatus
            (Vendor vendor, String productName, PurchaseOrderStatus purchaseOrderStatus);
}
