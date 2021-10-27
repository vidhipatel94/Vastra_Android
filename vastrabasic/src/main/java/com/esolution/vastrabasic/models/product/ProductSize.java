package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductSize implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    int productId;

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
}
