package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;
    int productSizeId;  // ProductSize
    int productColorId;     // ProductColor
    int quantityAvailable;
}
