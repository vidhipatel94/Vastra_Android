package com.esolution.vastrabasic.models;

import java.util.List;

public class ProductFilter {
    int designerId;
    int catalogueId;
    float minPrice;
    float maxPrice;
    List<Integer> productTypes;
    List<Integer> productPatterns;
    List<Integer> productKnitWovens;
    List<Integer> productWashCares;
    List<Integer> productColors;
    List<Integer> productMaterials;
    List<Integer> productOccasions;
    List<Integer> productSeasons;
    List<String> productBrandSizes;
    List<String> productCustomSizes;
    List<Integer> productDesigners;

    public int getDesignerId() {
        return designerId;
    }

    public void setDesignerId(int designerId) {
        this.designerId = designerId;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public List<Integer> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<Integer> productTypes) {
        this.productTypes = productTypes;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<Integer> getProductPatterns() {
        return productPatterns;
    }

    public void setProductPatterns(List<Integer> productPatterns) {
        this.productPatterns = productPatterns;
    }

    public List<Integer> getProductKnitWovens() {
        return productKnitWovens;
    }

    public void setProductKnitWovens(List<Integer> productKnitWovens) {
        this.productKnitWovens = productKnitWovens;
    }

    public List<Integer> getProductWashCares() {
        return productWashCares;
    }

    public void setProductWashCares(List<Integer> productWashCares) {
        this.productWashCares = productWashCares;
    }

    public List<Integer> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<Integer> productColors) {
        this.productColors = productColors;
    }

    public List<Integer> getProductMaterials() {
        return productMaterials;
    }

    public void setProductMaterials(List<Integer> productMaterials) {
        this.productMaterials = productMaterials;
    }

    public List<Integer> getProductOccasions() {
        return productOccasions;
    }

    public void setProductOccasions(List<Integer> productOccasions) {
        this.productOccasions = productOccasions;
    }

    public List<Integer> getProductSeasons() {
        return productSeasons;
    }

    public void setProductSeasons(List<Integer> productSeasons) {
        this.productSeasons = productSeasons;
    }

    public List<String> getProductBrandSizes() {
        return productBrandSizes;
    }

    public void setProductBrandSizes(List<String> productBrandSizes) {
        this.productBrandSizes = productBrandSizes;
    }

    public List<String> getProductCustomSizes() {
        return productCustomSizes;
    }

    public void setProductCustomSizes(List<String> productCustomSizes) {
        this.productCustomSizes = productCustomSizes;
    }

    public List<Integer> getProductDesigners() {
        return productDesigners;
    }

    public void setProductDesigners(List<Integer> productDesigners) {
        this.productDesigners = productDesigners;
    }
}
