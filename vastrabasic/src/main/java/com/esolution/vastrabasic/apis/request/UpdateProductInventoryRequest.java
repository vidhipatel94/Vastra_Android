package com.esolution.vastrabasic.apis.request;

import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrabasic.models.product.ProductSize;

import java.util.List;

public class UpdateProductInventoryRequest {
    int id;
    List<ProductColor> colors;
    List<ProductSize> sizes;
    List<ProductInventory> inventories;

    public UpdateProductInventoryRequest(int id) {
        this.id = id;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
    }

    public void setSizes(List<ProductSize> sizes) {
        this.sizes = sizes;
    }

    public void setInventories(List<ProductInventory> inventories) {
        this.inventories = inventories;
    }
}
