package com.esolution.vastrashopper;

// singleton
public class ShopperHandler {

    private static ShopperHandler INSTANCE;
    private Listener listener;

    public static ShopperHandler instance(Listener listener) {
        if (INSTANCE == null) {
            INSTANCE = new ShopperHandler();
        }
        INSTANCE.listener = listener;
        return INSTANCE;
    }

    public static Listener getListener() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.listener;
    }

    public interface Listener {
        void onLoggedOut();
    }
}
