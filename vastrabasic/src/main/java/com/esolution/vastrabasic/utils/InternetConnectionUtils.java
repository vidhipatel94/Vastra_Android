package com.esolution.vastrabasic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class InternetConnectionUtils {
    public static boolean isConnectingToInternet(Context mContext) {
        if (mContext == null) return false;

        if (checkInternetByInfo(mContext)) {
            try {
                return checkInternetByPing();
            } catch (InterruptedException | IOException e) {
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean checkInternetByInfo(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final Network network = connectivityManager.getActiveNetwork();
                if (network != null) {
                    final NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(network);
                    if (nc != null) {
                        return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                    }
                }
            }

            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo tempNetworkInfo : networkInfos) {
                if (tempNetworkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkInternetByPing() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        boolean r = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            r = Runtime.getRuntime().exec(command).waitFor(1000, TimeUnit.MILLISECONDS);
        }
        return r;
    }
}
