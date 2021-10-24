package com.esolution.vastrabasic.models.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;


    int id;

    int catalogueId;
    String catalogueName;  // EXTRA

    String designerName;    // EXTRA, in get query

    int typeId;     // ProductType
    ProductType productType;  // EXTRA

    String title;
    String description;
    ArrayList<String> images;
    float price;
    int multipackSet;
    float weight;
    int occasion;
    int pattern;
    int knitOrWoven;
    int washCare;
    String trend;
    int minAge;
    int maxAge;
    int totalLikes;
    float overallRating;

    List<ProductMaterial> materials;  // EXTRA
    List<ProductSeason> seasons;  // EXTRA

    List<ProductColor> colors;  // EXTRA
    List<ProductSize> sizes;  // EXTRA

    List<ProductInventory> inventories;  // EXTRA


    public Product(int catalogueId, int typeId, String title) {
        this.catalogueId = catalogueId;
        this.typeId = typeId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMultipackSet() {
        return multipackSet;
    }

    public void setMultipackSet(int multipackSet) {
        this.multipackSet = multipackSet;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getOccasion() {
        return occasion;
    }

    public void setOccasion(int occasion) {
        this.occasion = occasion;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getKnitOrWoven() {
        return knitOrWoven;
    }

    public void setKnitOrWoven(int knitOrWoven) {
        this.knitOrWoven = knitOrWoven;
    }

    public int getWashCare() {
        return washCare;
    }

    public void setWashCare(int washCare) {
        this.washCare = washCare;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(float overallRating) {
        this.overallRating = overallRating;
    }

    public List<ProductMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ProductMaterial> materials) {
        this.materials = materials;
    }

    public List<ProductSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<ProductSeason> seasons) {
        this.seasons = seasons;
    }

    public List<ProductColor> getColors() {
        return colors;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
    }

    public List<ProductSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSize> sizes) {
        this.sizes = sizes;
    }

    public List<ProductInventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<ProductInventory> inventories) {
        this.inventories = inventories;
    }

}
