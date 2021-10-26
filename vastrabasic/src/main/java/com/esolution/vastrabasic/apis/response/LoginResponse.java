package com.esolution.vastrabasic.apis.response;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;

public class LoginResponse {

    String sessionToken;
    User user;
    Designer designer;

    public User getUser() {
        return user;
    }

    public Designer getDesigner() {
        return designer;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
