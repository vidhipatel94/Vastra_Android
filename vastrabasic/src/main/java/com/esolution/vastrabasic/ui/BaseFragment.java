package com.esolution.vastrabasic.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseFragment extends Fragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    protected void showMessage(View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    // Used while closing keyboard on back pressed
    protected View getRootView() {
        return null;
    }

    protected void onBackPressed() {
        if (getRootView() != null && isKeyboardOpen() && closeKeyboard()) {
            return;
        }
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    protected boolean isKeyboardOpen() {
        Context context = getContext();
        if (context == null) return false;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    protected boolean closeKeyboard() {
        Activity activity = getActivity();
        if (activity == null) return false;

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
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
    public void onDestroy() {
        subscriptions.clear();
        super.onDestroy();
    }

}
