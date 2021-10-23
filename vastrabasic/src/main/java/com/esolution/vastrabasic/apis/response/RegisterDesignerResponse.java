package com.esolution.vastrabasic.apis.response;

import com.esolution.vastrabasic.models.Designer;

public class RegisterDesignerResponse {
    Designer designer;
    String sessionToken;

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
