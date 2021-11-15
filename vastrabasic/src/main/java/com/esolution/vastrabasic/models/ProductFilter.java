package com.esolution.vastrabasic.models;

import java.util.List;

public class ProductFilter {
    float minPrice = 0.0f;
    float maxPrice = 10000.0f;
    int gender = -1;
    int ageGroup = -1;
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

    public ProductFilter() {
    }

    public ProductFilter(List<Integer> productTypes, float minPrice, float maxPrice,
                         List<Integer> productPatterns, List<Integer> productKnitWovens,
                         List<Integer> productWashCares, List<Integer> productColors,
                         List<Integer> productMaterials, List<Integer> productOccasions,
                         List<Integer> productSeasons, List<String> productBrandSizes,
                         List<String> productCustomSizes, List<Integer> productDesigners) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productTypes = productTypes;
        this.productPatterns = productPatterns;
        this.productKnitWovens = productKnitWovens;
        this.productWashCares = productWashCares;
        this.productColors = productColors;
        this.productMaterials = productMaterials;
        this.productOccasions = productOccasions;
        this.productSeasons = productSeasons;
        this.productBrandSizes = productBrandSizes;
        this.productCustomSizes = productCustomSizes;
        this.productDesigners = productDesigners;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
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
