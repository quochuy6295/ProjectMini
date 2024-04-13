package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.common.staticdata.StockStatus;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

@Getter
@Setter
public class ProductItemInfo extends BaseInfo {

    private String productItemCode;

    private ProductInfo productInfo;

    private StockStatus stockStatus;

}
