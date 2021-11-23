package com.esolution.vastrafashiondesigner;

// singleton
public class FashionDesignerHandler {

    private static FashionDesignerHandler INSTANCE;
    private Listener listener;

    public static FashionDesignerHandler instance(Listener listener) {
        if (INSTANCE == null) {
            INSTANCE = new FashionDesignerHandler();
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
