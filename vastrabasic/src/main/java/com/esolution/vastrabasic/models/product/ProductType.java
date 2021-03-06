package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductType implements Comparable<ProductType>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final int GENDER_BOTH = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public static final int AGE_GROUP_ADULTS = 0;
    public static final int AGE_GROUP_KIDS = 1;
    public static final int AGE_GROUP_BABY = 2;

    int id;
    String name;
    int gender;
    int ageGroup;

    public ProductType(int id, String name, int gender, int ageGroup) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.ageGroup = ageGroup;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    @Override
    public int compareTo(ProductType o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
