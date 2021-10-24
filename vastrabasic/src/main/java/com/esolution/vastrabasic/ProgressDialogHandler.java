package com.esolution.vastrabasic;

import android.app.ProgressDialog;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;

import com.esolution.vastrabasic.utils.Utils;

public class ProgressDialogHandler {
    private final FragmentActivity activity;
    private String message;
    private ProgressDialog progressDialog;
    private boolean isCancelable;
    private boolean isProgress;

    public ProgressDialogHandler(FragmentActivity activity) {
        this.activity = activity;
        this.message = activity.getString(R.string.please_wait);
        this.isCancelable = false;
    }

    public ProgressDialogHandler setMessage(@StringRes int message) {
        this.message = activity.getString(message);
        return this;
    }

    public ProgressDialogHandler setMessage(String message) {
        this.message = message;
        return this;
    }

    public ProgressDialogHandler setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public void setProgress(boolean progress) {
        if (progress) {
            isProgress = showDialog();
        } else {
            dismissDialog();
            isProgress = false;
        }
    }

    public boolean isProgress() {
        return isProgress;
    }

    private boolean showDialog() {
        if (!Utils.isActivityExists(activity)) return false;
        if (progressDialog != null && progressDialog.isShowing()) {
            return true;
        }
        progressDialog = ProgressDialog.show(activity, null, message, false, isCancelable);
        return true;
    }

    private void dismissDialog() {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception ignore) {
            }
        }
        progressDialog = null;
    }
}
