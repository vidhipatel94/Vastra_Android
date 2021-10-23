package com.esolution.vastrabasic.apis.response;

import com.esolution.vastrabasic.models.User;

public class RegisterUserResponse {
    User user;
    String sessionToken;

    public User getUser() {
        return user;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
