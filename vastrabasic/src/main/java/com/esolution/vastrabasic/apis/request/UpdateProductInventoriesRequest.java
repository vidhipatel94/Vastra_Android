package com.esolution.vastrabasic.apis.request;

import com.esolution.vastrabasic.models.product.ProductInventory;

import java.util.List;

public class UpdateProductInventoriesRequest {
    int productId;
    List<ProductInventory> inventories;

    public UpdateProductInventoriesRequest(int productId, List<ProductInventory> inventories) {
        this.productId = productId;
        this.inventories = inventories;
    }
}
