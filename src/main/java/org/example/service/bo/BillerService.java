package org.example.service.bo;

import org.example.common.CodeGenerator;
import org.example.common.TaxCalculation;
import org.example.common.TaxCalculationInput;
import org.example.common.TaxCalculationResponse;
import org.example.common.staticdata.CodeType;
import org.example.common.staticdata.StockStatus;
import org.example.dto.BillerInfo;
import org.example.dto.PaymentInfo;
import org.example.dto.PaymentRequest;
import org.example.entity.Biller;
import org.example.entity.Customer;
import org.example.entity.Payment;
import org.example.entity.ProductItem;
import org.example.mapper.BillerMapper;
import org.example.mapper.PaymentMapper;
import org.example.message.ResponseMessage;
import org.example.repository.BillerRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.PaymentRepository;
import org.example.repository.ProductItemRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillerService extends BaseService {

    private final CustomerRepository customerRepository;

    private final ProductItemRepository productItemRepository;

    private final TaxCalculation taxCalculation;

    private final BillerRepository billerRepository;

    private final PaymentRepository paymentRepository;

    private final CodeGenerator codeGenerator;

    @Autowired
    public BillerService(CustomerRepository customerRepository, ProductItemRepository productItemRepository,
                         TaxCalculation taxCalculation,
                         BillerRepository billerRepository,
                         PaymentRepository paymentRepository,
                         CodeGenerator codeGenerator) {
        this.customerRepository = customerRepository;
        this.productItemRepository = productItemRepository;
        this.taxCalculation = taxCalculation;
        this.billerRepository = billerRepository;
        this.paymentRepository = paymentRepository;
        this.codeGenerator = codeGenerator;
    }

    private Biller sellProduct(Customer customer, int quantity) {
        Biller biller = new Biller();
        biller.setCustomer(customer);
        biller.setQuantity(quantity);
        biller.setGrandTotal(0);
        biller.setBillNo(codeGenerator.newCode(CodeType.BILL_NO));
        Biller newBiller = billerRepository.save(biller);
        return newBiller;
    }

    private TaxCalculationResponse calculateTax(double unitPrice, float taxPercentage, int quantity) {
        TaxCalculationInput taxCalculationInput = new TaxCalculationInput(unitPrice, taxPercentage, quantity);
        taxCalculationInput.setUnitPrice(unitPrice);
        taxCalculationInput.setQuantity(quantity);
        taxCalculationInput.setTaxPercentage(taxPercentage);
        return taxCalculation.calculateTax(taxCalculationInput);
    }

    public BillerInfo save(PaymentRequest paymentRequest) {
        List<String> productItemCodes = paymentRequest.getProductItemCode();
        String customerMobileNo = paymentRequest.getCustomerMobileNo();
        int quantity = paymentRequest.getQuantity();
        List<ProductItem> productItems = productItemRepository.findProductItemByProductItemCodeIn(productItemCodes);
        Customer customer = customerRepository.findCustomerByMobileNo(customerMobileNo);
        Biller biller = sellProduct(customer, quantity);
        double grandTotal = 0;
        double totalTaxAmount = 0;
        for (ProductItem productItem : productItems) {
            Payment payment = new Payment();
            payment.setProductItem(productItem);
            payment.setProductId(productItem.getProduct().getId());
            payment.setBiller(biller);
            payment.setTax(productItem.getProduct().getTax());
            TaxCalculationResponse taxCalculationResponse =
                    calculateTax(productItem.getProduct().getPrice(), productItem.getProduct().getTax().getTaxPercentage(), 1);
            payment.setPrice(taxCalculationResponse.getTotalAmount()); // quantity * unit price
            payment.setTaxAmount(taxCalculationResponse.getTaxAmount());
            payment.setTotalPrice(taxCalculationResponse.getTotalAmount() + taxCalculationResponse.getTaxAmount()); // total amount + tax amount
            grandTotal = grandTotal + payment.getPrice();
            totalTaxAmount = totalTaxAmount + payment.getTaxAmount();
            paymentRepository.save(payment);
            productItem.setStockStatus(StockStatus.SOLD);
            productItemRepository.save(productItem);
        }
        biller.setGrandTotal(grandTotal);
        biller.setQuantity(productItems.size());
        biller.setTotalTaxAmount(totalTaxAmount);
        Biller newBiller = billerRepository.save(biller);

        BillerInfo billerInfo = BillerMapper.INSTANCE.billerToBillerInfo(newBiller);
        return billerInfo;
    }

    public List<PaymentInfo> cancel(PaymentRequest paymentRequest) {
        List<PaymentInfo> paymentInfos = new ArrayList<>();
        List<String> productItemCodes = paymentRequest.getProductItemCode();
        String customerMobileNo = paymentRequest.getCustomerMobileNo();
        List<ProductItem> productItems = productItemRepository.findProductItemByProductItemCodeIn(productItemCodes);
        for (ProductItem productItem : productItems) {
            Payment payment = paymentRepository.findPaymentByProductItemAndDeletedFalse(productItem);
            payment.setDeleted(true);
            Payment newPayment = paymentRepository.save(payment);
            Biller biller = payment.getBiller();
            biller.setTotalTaxAmount(biller.getTotalTaxAmount() - payment.getTaxAmount());
            biller.setGrandTotal(biller.getGrandTotal() - payment.getPrice());
            billerRepository.save(biller);
            PaymentInfo paymentInfo = PaymentMapper.INSTANCE.paymentToPaymentInfo(newPayment);
            paymentInfos.add(paymentInfo);
            productItemRepository.save(productItem);
        }
        return paymentInfos;
    }
    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        return null;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }
}
