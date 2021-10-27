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
}
