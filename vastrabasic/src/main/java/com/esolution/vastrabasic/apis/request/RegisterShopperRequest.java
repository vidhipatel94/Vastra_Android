package com.esolution.vastrabasic.apis.request;

import android.content.Context;

import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.UserType;
import com.esolution.vastrabasic.utils.Utils;

public class RegisterShopperRequest extends User {
    String deviceId;

    public RegisterShopperRequest(Context context, String email) {
        super(email, UserType.Shopper.getValue());
        this.deviceId = Utils.getDeviceId(context);
    }
}
