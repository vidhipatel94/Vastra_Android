package com.esolution.vastrabasic.models.product;

public class Material implements Comparable<Material> {
    int id;
    String material;

    public int getId() {
        return id;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public int compareTo(Material o) {
        return this.material.compareTo(o.material);
    }
}
