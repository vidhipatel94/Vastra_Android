package com.esolution.vastrabasic.apis.request;

import android.content.Context;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.utils.Utils;

public class RegisterDesignerRequest extends Designer {
    String deviceId;

    public RegisterDesignerRequest(Context context, String email) {
        super(email);
        this.deviceId = Utils.getDeviceId(context);
    }
}
