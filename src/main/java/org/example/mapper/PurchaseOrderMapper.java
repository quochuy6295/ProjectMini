package org.example.mapper;

import org.example.dto.PurchaseOrderInfo;
import org.example.entity.PurchaseOrder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(implementationPackage = "mapper.impl")
public interface PurchaseOrderMapper {

    PurchaseOrderMapper INSTANCE =  Mappers.getMapper(PurchaseOrderMapper.class);
    PurchaseOrder purchaseOrderInfoToPurchaseOrder(PurchaseOrderInfo purchaseOrderInfo);
    @InheritInverseConfiguration
    PurchaseOrderInfo purchaseOrderToPurchaseOrderInfo(PurchaseOrder purchaseOrder);
}
