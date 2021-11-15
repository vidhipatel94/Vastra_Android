package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductImageBinding;
import com.esolution.vastrafashiondesigner.ui.MainActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductImagesActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProductImagesActivity extends AddProductImagesActivity {

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, UpdateProductImagesActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogHandler = new ProgressDialogHandler(this);

        fillData();
        setAllImagesRemovable();
    }

    private void fillData() {
        ArrayList<String> images = product.getImages();
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                if (i >= 3) {
                    binding.addMoreProductImage.performClick();
                }
                List<RowAddProductImageBinding> list = new ArrayList<>(imageBindings.keySet());
                RowAddProductImageBinding imageBinding =
                        list.get(i >= 3 ? list.size() - 1 : i);
                ImageUtils.loadImageUrl(imageBinding.productImage, images.get(i));
                imageBinding.productImage.setTag(images.get(i));
            }
        }

        binding.inputDescription.setText(product.getDescription());
    }

    private void setAllImagesRemovable() {
        binding.image1Layout.iconDelete.setVisibility(View.VISIBLE);
        binding.image2Layout.iconDelete.setVisibility(View.VISIBLE);
        binding.image3Layout.iconDelete.setVisibility(View.VISIBLE);

        binding.image1Layout.iconDelete.setOnClickListener(v -> {
            onClickRemoveImage(binding.image1Layout);
        });

        binding.image2Layout.iconDelete.setOnClickListener(v -> {
            onClickRemoveImage(binding.image2Layout);
        });

        binding.image3Layout.iconDelete.setOnClickListener(v -> {
            onClickRemoveImage(binding.image3Layout);
        });
    }

    private void onClickRemoveImage(RowAddProductImageBinding imageBinding) {
        imageBinding.getRoot().setVisibility(View.GONE);
        imageBindings.remove(imageBinding);
    }

    @Override
    protected void onSetSelectedImage(RowAddProductImageBinding imageBinding) {
        imageBinding.productImage.setTag(null);
    }

    @Override
    protected int getTotalImages() {
        List<RowAddProductImageBinding> list = new ArrayList<>(imageBindings.keySet());
        for (int i = 0; i < list.size(); i++) {
            RowAddProductImageBinding imageBinding = list.get(i);
            if (imageBinding.productImage.getTag() != null) {
                String url = (String) imageBinding.productImage.getTag();
                imageUrls.add(url);
            }
        }
        return imageUrls.size() + pendingUploadURIs.size();
    }

    @Override
    protected void openNextScreen() {
        saveData();
    }

    private void saveData() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().updateProduct(preferences.getSessionToken(), product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        onDataSaved();
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

    private void onDataSaved() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
