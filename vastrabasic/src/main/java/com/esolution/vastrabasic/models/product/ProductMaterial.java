package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;

    int materialId;     // Material
    String materialName;   // EXTRA

    int percentage;

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getPercentage() {
        return percentage;
    }
}
