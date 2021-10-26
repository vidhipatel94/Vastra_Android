package com.esolution.vastrabasic.apis;

import android.content.Context;

import com.esolution.vastrabasic.R;
import com.esolution.vastrabasic.apis.response.APIResponse;
import com.esolution.vastrabasic.utils.InternetConnectionUtils;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestUtils {

    public static final int CODE_UNAUTHORIZED = 403;

    private static VastraAPIs vastraAPIs;

    public static VastraAPIs getAPIs() {
        if (vastraAPIs == null) {
            //MAC - ipconfig getifaddr en0
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.0.114:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            vastraAPIs = retrofit.create(VastraAPIs.class);
        }
        return vastraAPIs;
    }

    public static String processThrowable(Context context, Throwable throwable) {
        if (throwable instanceof MalformedJsonException) {
            return "Service JSON Error";
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            httpException.printStackTrace();
            return "Service Error";
        } else if (throwable instanceof IOException || !InternetConnectionUtils.isConnectingToInternet(context)) {
            throwable.printStackTrace();
            return context.getString(R.string.internet_error);
        }
        return context.getString(R.string.server_error);
    }

    public static boolean processResponseFailure(APIResponse apiResponse){
        if (!apiResponse.isSuccess()){
            if (apiResponse.getCode() == CODE_UNAUTHORIZED) {
                // TODO logout user
                return true;
            }
        }
        return false;
    }
}
