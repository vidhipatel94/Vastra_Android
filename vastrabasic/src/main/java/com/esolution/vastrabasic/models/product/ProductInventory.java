package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;
    int productSizeId;  // ProductSize
    int productColorId;     // ProductColor
    int quantityAvailable;

    String sizeName;    //  For adapter only

    public ProductInventory(int productId, int productSizeId, int productColorId) {
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.productColorId = productColorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
