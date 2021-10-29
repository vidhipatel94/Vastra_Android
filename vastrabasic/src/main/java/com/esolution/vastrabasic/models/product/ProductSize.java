package com.esolution.vastrabasic.models.product;

import android.content.Context;

import com.esolution.vastrabasic.R;

import java.io.Serializable;

public class ProductSize implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int TYPE_ONE_SIZE = 0;
    public static final int TYPE_LETTER_SIZE = 1;
    public static final int TYPE_CUSTOM_SIZE = 4;

    int id;
    int productId;

    int sizeType;

    String brandSize;
    String USSize;

    float headCircumferenceMin;
    float headCircumferenceMax;

    float neckMin;
    float neckMax;

    float bustMin;
    float bustMax;

    float waistMin;
    float waistMax;

    float hipMin;
    float hipMax;

    float inseamLength;
    float outseamLength;

    float sleeveLength;
    float palmCircumferenceMin;
    float palmCircumferenceMax;
    float palmToFingerLength;

    float wristMin;
    float wristMax;

    float footLength;

    float frontLength;
    float backLength;
    float width;

    float weightGram;
    float volumeML;
    String customSize;

    public ProductSize(int productId, int sizeType) {
        this.productId = productId;
        this.sizeType = sizeType;
    }

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

    public int getSizeType() {
        return sizeType;
    }

    public void setSizeType(int sizeType) {
        this.sizeType = sizeType;
    }

    public String getBrandSize() {
        return brandSize;
    }

    public void setBrandSize(String brandSize) {
        this.brandSize = brandSize;
    }

    public String getUSSize() {
        return USSize;
    }

    public void setUSSize(String USSize) {
        this.USSize = USSize;
    }

    public float getHeadCircumferenceMin() {
        return headCircumferenceMin;
    }

    public void setHeadCircumferenceMin(float headCircumferenceMin) {
        this.headCircumferenceMin = headCircumferenceMin;
    }

    public float getHeadCircumferenceMax() {
        return headCircumferenceMax;
    }

    public void setHeadCircumferenceMax(float headCircumferenceMax) {
        this.headCircumferenceMax = headCircumferenceMax;
    }

    public float getNeckMin() {
        return neckMin;
    }

    public void setNeckMin(float neckMin) {
        this.neckMin = neckMin;
    }

    public float getNeckMax() {
        return neckMax;
    }

    public void setNeckMax(float neckMax) {
        this.neckMax = neckMax;
    }

    public float getBustMin() {
        return bustMin;
    }

    public void setBustMin(float bustMin) {
        this.bustMin = bustMin;
    }

    public float getBustMax() {
        return bustMax;
    }

    public void setBustMax(float bustMax) {
        this.bustMax = bustMax;
    }

    public float getWaistMin() {
        return waistMin;
    }

    public void setWaistMin(float waistMin) {
        this.waistMin = waistMin;
    }

    public float getWaistMax() {
        return waistMax;
    }

    public void setWaistMax(float waistMax) {
        this.waistMax = waistMax;
    }

    public float getHipMin() {
        return hipMin;
    }

    public void setHipMin(float hipMin) {
        this.hipMin = hipMin;
    }

    public float getHipMax() {
        return hipMax;
    }

    public void setHipMax(float hipMax) {
        this.hipMax = hipMax;
    }

    public float getInseamLength() {
        return inseamLength;
    }

    public void setInseamLength(float inseamLength) {
        this.inseamLength = inseamLength;
    }

    public float getOutseamLength() {
        return outseamLength;
    }

    public void setOutseamLength(float outseamLength) {
        this.outseamLength = outseamLength;
    }

    public float getSleeveLength() {
        return sleeveLength;
    }

    public void setSleeveLength(float sleeveLength) {
        this.sleeveLength = sleeveLength;
    }

    public float getPalmCircumferenceMin() {
        return palmCircumferenceMin;
    }

    public void setPalmCircumferenceMin(float palmCircumferenceMin) {
        this.palmCircumferenceMin = palmCircumferenceMin;
    }

    public float getPalmCircumferenceMax() {
        return palmCircumferenceMax;
    }

    public void setPalmCircumferenceMax(float palmCircumferenceMax) {
        this.palmCircumferenceMax = palmCircumferenceMax;
    }

    public float getPalmToFingerLength() {
        return palmToFingerLength;
    }

    public void setPalmToFingerLength(float palmToFingerLength) {
        this.palmToFingerLength = palmToFingerLength;
    }

    public float getWristMin() {
        return wristMin;
    }

    public void setWristMin(float wristMin) {
        this.wristMin = wristMin;
    }

    public float getWristMax() {
        return wristMax;
    }

    public void setWristMax(float wristMax) {
        this.wristMax = wristMax;
    }

    public float getFootLength() {
        return footLength;
    }

    public void setFootLength(float footLength) {
        this.footLength = footLength;
    }

    public float getFrontLength() {
        return frontLength;
    }

    public void setFrontLength(float frontLength) {
        this.frontLength = frontLength;
    }

    public float getBackLength() {
        return backLength;
    }

    public void setBackLength(float backLength) {
        this.backLength = backLength;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWeightGram() {
        return weightGram;
    }

    public void setWeightGram(float weightGram) {
        this.weightGram = weightGram;
    }

    public float getVolumeML() {
        return volumeML;
    }

    public void setVolumeML(float volumeML) {
        this.volumeML = volumeML;
    }

    public String getCustomSize() {
        return customSize;
    }

    public void setCustomSize(String customSize) {
        this.customSize = customSize;
    }

    public String getSizeText(Context context) {
        switch (sizeType) {
            case TYPE_ONE_SIZE:
                return context.getString(R.string.one_size);
            case TYPE_LETTER_SIZE:
                return brandSize;
            case TYPE_CUSTOM_SIZE:
                return customSize;
        }
        return null;
    }
}
