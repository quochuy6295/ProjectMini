package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.BaseObject;
import org.example.entity.Tax;

@Getter
@Setter
public class PaymentInfo extends BaseObject {

    private BillerInfo biller;

    private ProductItemInfo productItem;

    private Tax tax;

    private double price;

    private double totalPrice;

    private double taxAmount;

    private long productId;

}
