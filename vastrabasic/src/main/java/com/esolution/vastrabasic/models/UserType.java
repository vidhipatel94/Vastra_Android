package com.esolution.vastrabasic.models;

public enum UserType {
    Shopper(1),
    FashionDesigner(2);

    private final int type;

    private UserType(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
