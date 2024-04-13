package org.example.service.bo;

import org.example.common.CodeGenerator;
import org.example.common.TaxCalculation;
import org.example.common.TaxCalculationInput;
import org.example.common.TaxCalculationResponse;
import org.example.common.staticdata.CodeType;
import org.example.dto.PurchaseOrderInfo;
import org.example.entity.Category;
import org.example.entity.PurchaseOrder;
import org.example.entity.Tax;
import org.example.entity.Vendor;
import org.example.mapper.PurchaseOrderMapper;
import org.example.message.ResponseMessage;
import org.example.repository.CategoryRepository;
import org.example.repository.PurchaseOrderRepository;
import org.example.repository.TaxRepository;
import org.example.repository.VendorRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService extends BaseService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxCalculation taxCalculation;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseMessage<PurchaseOrderInfo> save(PurchaseOrderInfo purchaseOrderInfo) {

        PurchaseOrder purchaseOrder = PurchaseOrderMapper.INSTANCE
                .purchaseOrderInfoToPurchaseOrder(purchaseOrderInfo);
        if (purchaseOrderInfo.getId() == null) {
            Category category = categoryRepository.findById(purchaseOrderInfo.getCategory().getId()).orElse(null);
            purchaseOrder.setCategory(category);
            Vendor vendor = vendorRepository.getOne(purchaseOrder.getVendor().getId());
            purchaseOrder.setVendor(vendor);
            Tax tax = taxRepository.getOne(purchaseOrder.getTax().getId());
            purchaseOrder.setTax(tax);
            String purchaseOrderCode = codeGenerator.newCode(CodeType.PURCHASE_ORDER_CODE);
            purchaseOrder.setPurchaseOrderCode(purchaseOrderCode);
//            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);
        }
        TaxCalculationInput taxCalculationInput =
                new TaxCalculationInput(
                        purchaseOrder.getUnitPrice(), purchaseOrder.getTax().getTaxPercentage(), purchaseOrder.getQuantity());
        TaxCalculationResponse taxCalculationResponse
                = taxCalculation.calculateTax(taxCalculationInput);
        purchaseOrder.setTotalAmount(taxCalculationResponse.getTotalAmount());
        purchaseOrder.setGrandTotal(taxCalculationResponse.getGrandTotal());
        purchaseOrder.setTaxAmount(taxCalculationResponse.getTaxAmount());
        PurchaseOrder newPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        PurchaseOrderInfo newPurchaseOrderInfo = PurchaseOrderMapper
                .INSTANCE.purchaseOrderToPurchaseOrderInfo(newPurchaseOrder);
        ResponseMessage<PurchaseOrderInfo> responseMessage = ResponseMessage.withResponseData(newPurchaseOrderInfo, "", "");
        return responseMessage;
    }

    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(Long.parseLong(id)).orElse(null);
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderMapper.INSTANCE.purchaseOrderToPurchaseOrderInfo(purchaseOrder);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(purchaseOrderInfo, "", "");
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        List<PurchaseOrderInfo> purchaseOrderInfos = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderMapper.INSTANCE.purchaseOrderToPurchaseOrderInfo(purchaseOrder);
            purchaseOrderInfos.add(purchaseOrderInfo);
        }
        ResponseMessage responseMessage = ResponseMessage.withResponseData(purchaseOrderInfos, "", "");
        return responseMessage;
    }

//    public void findPurchaseOrderByVendorAndProductNameAndStatus(Long vendorId,String productName,
//                                                                 PurchaseOrderStatus purchaseOrderStatus) {
//        PurchaseOrder purchaseOrder
//    }
}
