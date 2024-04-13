package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.common.staticdata.PurchaseOrderStatus;
import org.example.entity.BaseObject;

/*
 * Created by Suresh Stalin on 13 / Oct / 2020.
 */

@Getter
@Setter
public class PurchaseOrderInfo extends BaseObject {

    private String productName;

    private String productDescription;

    private int quantity;

    private double unitPrice;

    private String purchaseOrderCode;

    private VendorInfo vendor;

    private TaxInfo tax;

    private double taxAmount;

    private double totalAmount;

    private CategoryInfo category;

    private double grandTotal;

    private PurchaseOrderStatus purchaseOrderStatus;

}
