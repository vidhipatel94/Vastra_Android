package com.esolution.vastrabasic.utils;

import android.content.Context;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogHelper {
    public interface Listener {
        void onClickPositiveButton();

        void onClickNegativeButton();
    }

    public static void showDialog(Context context, String title, String message, String positiveBtnText,
                           String negativeBtnText, Listener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnText, (dialog, which) -> {
                    if (listener != null) {
                        listener.onClickPositiveButton();
                    }
                }).setNegativeButton(negativeBtnText, (dialog, which) -> {
                    if (listener != null) {
                        listener.onClickNegativeButton();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
}
