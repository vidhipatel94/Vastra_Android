package com.esolution.vastrabasic.models.product;

import java.io.Serializable;

public class ProductOccasion implements Serializable {
    public static final int CASUAL = 1;
    public static final int SMART_CASUAL = 2;   // office parties, happy hours, business luncheons
    public static final int DRESSY_CASUAL = 3;  // church, dinner, or an invite received via phone or e-mail.
    public static final int COUNTRY_CLUB_CASUAL = 4; // cruise lines, the country club, friend’s home for dinner, nice restaurant.
    public static final int BUSINESS_CASUAL = 5;    // company party, daily work attire, business lunch meetings
    public static final int FORMAL_OFFICE_WEAR = 6;
    public static final int SPORTS_WEAR = 7;    // Sports
    public static final int NATIVE_WEAR = 8;    // weddings, traditional occasions and religious gatherings
    public static final int COCKTAIL_ATTIRE = 9;    // adult birthday parties, evening social events.
    public static final int LOUNGE = 10;     // daytime engagement parties, business breakfasts, afternoon tea.
    public static final int WHITE_TIE = 11;  // charity fundraisers, government ceremonies, weddings, the opera
    public static final int BLACK_TIE = 12;  // charity fundraisers, political dinner parties, weddings.
    public static final int CREATIVE_BLACK_TIE = 13; // galas, silent auctions, weddings, and formal dinners that have a fun atmosphere.
    public static final int WARM_WEATHER_BLACK_TIE = 14; // formal events that are held outdoors, such as a cruise line or country club dinners, weddings, and galas.
    public static final int BLACK_TIE_OPTIONAL = 15; // elegant events such as galas, silent auctions, weddings, formal dinners

    private static final long serialVersionUID = 1L;

    int id;
    int productId;
    int occasion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOccasion() {
        return occasion;
    }

    public void setOccasion(int occasion) {
        this.occasion = occasion;
    }
}
