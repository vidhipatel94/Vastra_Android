package com.esolution.vastrabasic.apis.response;

import com.esolution.vastrabasic.models.Designer;

public class RegisterDesignerResponse {
    Designer designer;
    String sessionToken;

    public Designer getDesigner() {
        return designer;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
