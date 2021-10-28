package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class Color implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    String name;
    String hexCode;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }
}
