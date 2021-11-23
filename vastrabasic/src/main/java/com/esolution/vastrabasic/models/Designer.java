package com.esolution.vastrabasic.models;

public class Designer extends User {

    int id;
    String brandName;
    String tagline;

    int totalFollowers;
    int totalProducts;

    protected Designer() {
    }

    public Designer(String email) {
        super(email, UserType.FashionDesigner.getValue());
    }

    public Designer(String email, String password, String firstName, String lastName, String address,
                    String city, String province, String postalCode, String avatarURL) {
        super(email, password, firstName, lastName, address, city, province, postalCode, avatarURL,
                UserType.FashionDesigner.getValue());
    }

    public int getId() {
        return id;
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

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public int getTotalProducts() {
        return totalProducts;
    }
}
