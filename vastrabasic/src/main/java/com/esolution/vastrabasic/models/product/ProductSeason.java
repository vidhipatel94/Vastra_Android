package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductSeason implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;
    int season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
