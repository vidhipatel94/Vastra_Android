package com.esolution.vastrabasic.models.product;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BasicProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;

    String designerName;    // EXTRA, in get query
    String brandName;   // EXTRA

    int typeId;     // ProductType

    String title;
    ArrayList<String> images;
    float price;
    int totalLikes;
    float overallRating;

    boolean isUserLiked;

    public int getId() {
        return id;
    }

    public String getDesignerName() {
        return designerName;
    }

    public String getBrandName() {
        return brandName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public float getPrice() {
        return price;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public float getOverallRating() {
        return overallRating;
    }

    public boolean isUserLiked() {
        return isUserLiked;
    }

    public void setUserLiked(boolean userLiked) {
        isUserLiked = userLiked;
    }

}
