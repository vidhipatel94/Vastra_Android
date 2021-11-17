package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.apis.request.UpdateProductColorsRequest;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.ui.newproduct.addcolor.SelectProductColorsActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProductColorsActivity extends SelectProductColorsActivity {

    public static final String RESULT_PRODUCT = "result_product";

    public static Intent createIntent(Context context, Product product) {
        Intent intent = new Intent(context, UpdateProductColorsActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ProgressDialogHandler progressDialogHandler;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogHandler = new ProgressDialogHandler(this);

        if (product.getColors() != null) {
            productColors.addAll(product.getColors());
            adapter.notifyDataSetChanged();
        }

        binding.btnNext.setText(R.string.btn_save);
    }

    @Override
    protected void openNextScreen() {
        updateProductColors();
    }

    private void updateProductColors() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        UpdateProductColorsRequest request = new UpdateProductColorsRequest(product.getId(), product.getColors());
        subscriptions.add(RestUtils.getAPIs().updateProductColors(preferences.getSessionToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            product.setColors(response.getData());
                            onProductColorsUpdated();
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void onProductColorsUpdated() {
        Intent data = new Intent();
        data.putExtra(RESULT_PRODUCT, product);
        setResult(RESULT_OK, data);
        finish();
    }
}
