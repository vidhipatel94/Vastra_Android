package com.esolution.vastrabasic.ui;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    protected void showMessage(View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    // Used while closing keyboard on back pressed
    protected View getRootView() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (getRootView() != null && isKeyboardOpen() && closeKeyboard()) {
            return;
        }
        super.onBackPressed();
    }

    protected boolean isKeyboardOpen() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    protected boolean closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.clearFocus();
            if (view instanceof EditText) {
                ((EditText) view).setCursorVisible(false);
            }
            if (getRootView() != null) {
                getRootView().setFocusable(true);
                getRootView().setFocusableInTouchMode(true);
                getRootView().requestFocus();
            }
            return view instanceof EditText;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        subscriptions.clear();
        super.onDestroy();
    }
}
