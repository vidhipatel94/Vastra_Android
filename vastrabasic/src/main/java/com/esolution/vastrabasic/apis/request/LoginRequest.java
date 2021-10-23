package com.esolution.vastrabasic.apis.request;

import android.content.Context;

import com.esolution.vastrabasic.utils.Utils;

public class LoginRequest {
    String email;
    String password;
    String deviceId;

    public LoginRequest(Context context, String email, String password) {
        this.email = email;
        this.password = password;
        deviceId = Utils.getDeviceId(context);
    }
}
