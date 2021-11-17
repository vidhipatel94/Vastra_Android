package com.esolution.vastrabasic.apis.request;

import com.esolution.vastrabasic.models.product.ProductColor;

import java.util.List;

public class UpdateProductColorsRequest {
    int productId;
    List<ProductColor> productColors;

    public UpdateProductColorsRequest(int productId, List<ProductColor> productColors) {
        this.productId = productId;
        this.productColors = productColors;
    }
}
