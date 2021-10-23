package com.esolution.vastrabasic.models;

public class Designer extends User {

    int id;
    int userId;
    String brandName;
    String tagline;

    protected Designer() {
    }

    public Designer(String email) {
        super(email);
    }

    @Override
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}
