package org.example.service.bo;

import org.example.common.CodeGenerator;
import org.example.common.staticdata.CodeType;
import org.example.common.staticdata.Constants;
import org.example.common.staticdata.StockStatus;
import org.example.dto.ProductItemInfo;
import org.example.entity.Product;
import org.example.entity.ProductItem;
import org.example.mapper.ProductItemMapper;
import org.example.message.ResponseMessage;
import org.example.repository.ProductItemRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

@Service
public class ProductItemService extends BaseService {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    public ResponseMessage save(Product product) {
        Integer stockCount = product.getStockCount();
        ArrayList<ProductItem> productItems = new ArrayList<>();
        for (int i = 0; i < stockCount; i++) {
            ProductItem productItem = new ProductItem();
            String productCodeItem = codeGenerator.newCode(CodeType.PRODUCT_ITEM_CODE);
            productItem.setProductItemCode(productCodeItem);
            productItem.setProduct(product);
            productItem.setStockStatus(StockStatus.IN_STOCK);
            productItemRepository.save(productItem);
            productItems.add(productItem);
        }
        ResponseMessage responseMessage = ResponseMessage.withResponseData(productItems,
                String.format("Product Items are add in Product %s ", product.getProductCode()), Constants.INFO_TYPE);
        return responseMessage;
    }

    public ResponseMessage save(Product product,int productItemCount) {
        ArrayList<ProductItem> productItems = new ArrayList<>();
        for (int i = 0; i < productItemCount; i++) {
            ProductItem productItem = new ProductItem();
            String productCodeItem = codeGenerator.newCode(CodeType.PRODUCT_ITEM_CODE);
            productItem.setProductItemCode(productCodeItem);
            productItem.setProduct(product);
            productItemRepository.save(productItem);
            productItems.add(productItem);
        }
        ResponseMessage responseMessage = ResponseMessage.withResponseData(productItems,
                String.format("Product Items are add in Product %s ", product.getProductCode()), Constants.INFO_TYPE);
        return responseMessage;
    }

    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        ProductItem productItem = productItemRepository.findById(Long.parseLong(id)).orElse(null);
        ProductItemInfo productItemInfo = ProductItemMapper.INSTANCE.productItemToProductItemInfo(productItem);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(productItemInfo, Constants.SUCCESS_STATUS, Constants.INFO_TYPE);
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        List<ProductItemInfo> productItemInfos = new ArrayList<>();
        List<ProductItem> productItems = productItemRepository.findAll();
        for (ProductItem productItem : productItems) {
            ProductItemInfo productItemInfo = ProductItemMapper.INSTANCE.productItemToProductItemInfo(productItem);
            productItemInfos.add(productItemInfo);
        }
        ResponseMessage responseMessage = ResponseMessage.withResponseData(productItemInfos, Constants.SUCCESS_STATUS, Constants.INFO_TYPE);
        return responseMessage;
    }
}
