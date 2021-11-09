package com.esolution.vastrabasic.models;

import com.esolution.vastrabasic.models.product.BasicProduct;

import java.util.List;

public class BasicCatalogue extends Catalogue {

    List<BasicProduct> products;

    public List<BasicProduct> getProducts() {
        return products;
    }
}
