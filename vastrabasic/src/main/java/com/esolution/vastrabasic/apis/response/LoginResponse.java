package com.esolution.vastrabasic.apis.response;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;

public class LoginResponse extends Designer {

    public User getShopper() {
        return this;
    }

    public Designer getDesigner() {
        return this;
    }
}
