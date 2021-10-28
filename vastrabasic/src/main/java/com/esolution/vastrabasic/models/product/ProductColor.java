package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductColor implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;
    int prominentColorId;   // Color
    int secondaryColorId;  // Color
    int thirdColorId;  // Color

    String prominentColorName;  // EXTRA
    String prominentColorHexCode;   // EXTRA

    String secondaryColorName;  // EXTRA
    String secondaryColorHexCode;   // EXTRA

    String thirdColorName;  // EXTRA
    String thirdColorHexCode;   // EXTRA

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

    public int getProminentColorId() {
        return prominentColorId;
    }

    public void setProminentColorId(int prominentColorId) {
        this.prominentColorId = prominentColorId;
    }

    public int getSecondaryColorId() {
        return secondaryColorId;
    }

    public void setSecondaryColorId(int secondaryColorId) {
        this.secondaryColorId = secondaryColorId;
    }

    public int getThirdColorId() {
        return thirdColorId;
    }

    public void setThirdColorId(int thirdColorId) {
        this.thirdColorId = thirdColorId;
    }

    public String getProminentColorName() {
        return prominentColorName;
    }

    public void setProminentColorName(String prominentColorName) {
        this.prominentColorName = prominentColorName;
    }

    public String getProminentColorHexCode() {
        return prominentColorHexCode;
    }

    public void setProminentColorHexCode(String prominentColorHexCode) {
        this.prominentColorHexCode = prominentColorHexCode;
    }

    public String getSecondaryColorName() {
        return secondaryColorName;
    }

    public void setSecondaryColorName(String secondaryColorName) {
        this.secondaryColorName = secondaryColorName;
    }

    public String getSecondaryColorHexCode() {
        return secondaryColorHexCode;
    }

    public void setSecondaryColorHexCode(String secondaryColorHexCode) {
        this.secondaryColorHexCode = secondaryColorHexCode;
    }

    public String getThirdColorName() {
        return thirdColorName;
    }

    public void setThirdColorName(String thirdColorName) {
        this.thirdColorName = thirdColorName;
    }

    public String getThirdColorHexCode() {
        return thirdColorHexCode;
    }

    public void setThirdColorHexCode(String thirdColorHexCode) {
        this.thirdColorHexCode = thirdColorHexCode;
    }
}
