package com.esolution.vastrabasic.apis.request;

import com.esolution.vastrabasic.models.product.ProductSize;

import java.util.List;

public class UpdateProductSizesRequest {
    int productId;
    List<ProductSize> productSizes;

    public UpdateProductSizesRequest(int productId, List<ProductSize> productSizes) {
        this.productId = productId;
        this.productSizes = productSizes;
    }
}
