package com.esolution.vastrabasic.models.product;

public class ProductType {
    public static final int GENDER_BOTH = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public static final int AGE_GROUP_ADULTS = 0;
    public static final int AGE_GROUP_KIDS = 1;

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
}
