package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductImagesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductImageBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addcolor.SelectProductColorsActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddProductImagesActivity extends BaseActivity {

    protected static final String EXTRA_CATALOGUE = "extra_catalogue";
    protected static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductImagesActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    protected ActivityAddProductImagesBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    protected Catalogue catalogue;
    protected Product product;

    private ActivityResultLauncher<Intent> activityResultLauncherGallery;
    private static final int REQUEST_CODE_GALLARY = 102;
    private int photoIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return catalogue != null && product != null;
        }
        return false;
    }

    protected final Map<RowAddProductImageBinding, Uri> imageBindings = new LinkedHashMap<>();
    protected final ArrayList<String> imageUrls = new ArrayList<>();

    private void initView() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());
        binding.productTitle.setText(product.getTitle());

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.image1Layout.iconDelete.setVisibility(View.GONE);
        binding.image2Layout.iconDelete.setVisibility(View.GONE);
        binding.image3Layout.iconDelete.setVisibility(View.GONE);

        imageBindings.put(binding.image1Layout, null);
        imageBindings.put(binding.image2Layout, null);
        imageBindings.put(binding.image3Layout, null);

        binding.image1Layout.productImage.setOnClickListener(v -> chooseImage(0));
        binding.image2Layout.productImage.setOnClickListener(v -> chooseImage(1));
        binding.image3Layout.productImage.setOnClickListener(v -> chooseImage(2));

        binding.addMoreProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RowAddProductImageBinding imageBinding = RowAddProductImageBinding.inflate(getLayoutInflater());
                binding.addMoreImageLinearLayout.addView(imageBinding.getRoot());
                imageBindings.put(imageBinding, null);

                int i = imageBindings.size() - 1;
                imageBinding.productImage.setOnClickListener(v1 -> chooseImage(i));

                imageBinding.iconDelete.setOnClickListener(v1 -> {
                    binding.addMoreImageLinearLayout.removeView(imageBinding.getRoot());
                    imageBindings.remove(imageBinding);
                });
            }
        });

        activityResultLauncherGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUrl = result.getData().getData();
                        onImageSelected(imageUrl);
                    }
                });

        binding.btnNext.setOnClickListener(v -> {
            closeKeyboard();
            imageUrls.clear();
            if (isFormValidated()) {
                uploadImagesAndOpenNextScreen();
            }
        });
    }

    private void chooseImage(int i) {
        boolean granted = checkGalleryPermission();
        if (granted) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoIndex = i;
            activityResultLauncherGallery.launch(intent);
        } else {
            openPermissionRequiredDialog();
        }
    }

    private void onImageSelected(Uri imageUri) {
        if (imageBindings.size() > 0 && photoIndex < imageBindings.size()) {
            List<RowAddProductImageBinding> list = new ArrayList<>(imageBindings.keySet());
            RowAddProductImageBinding imageBinding = list.get(photoIndex);
            imageBinding.productImage.setImageURI(imageUri);
            imageBindings.put(imageBinding, imageUri);
            onSetSelectedImage(imageBinding);
        }
    }

    protected void onSetSelectedImage(RowAddProductImageBinding imageBinding) {
        // Not implemented
        // Only for inheritance
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GALLARY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncherGallery.launch(intent);
            } else {
                openPermissionRequiredDialog();
            }
        }
    }

    private void openPermissionRequiredDialog() {
        new AlertDialog.Builder(AddProductImagesActivity.this)
                .setTitle(R.string.gallery_permission_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private boolean checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(AddProductImagesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(AddProductImagesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(AddProductImagesActivity.this, R.string.gallery_access_msg,
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AddProductImagesActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLARY);

        }
        return false;
    }

    protected Stack<Uri> pendingUploadURIs;

    private boolean isFormValidated() {
        pendingUploadURIs = new Stack<>();
        List<Uri> uris = new ArrayList<>(imageBindings.values());
        for (int i = uris.size() - 1; i >= 0; i--) {
            if (uris.get(i) != null) {
                pendingUploadURIs.push(uris.get(i));
            }
        }

        if (getTotalImages() < 3) {
            showMessage(binding.getRoot(), getString(R.string.error_insufficient_product_images));
            return false;
        }

        String description = binding.inputDescription.getText().toString().trim();
        if (description.isEmpty()) {
            showMessage(binding.getRoot(), getString(R.string.error_empty_product_description));
            return false;
        }

        product.setDescription(description);

        return true;
    }

    protected int getTotalImages() {
        return pendingUploadURIs.size();
    }

    private void uploadImagesAndOpenNextScreen() {
        if (pendingUploadURIs == null) return;

        if (!pendingUploadURIs.isEmpty()) {
            Uri uri = pendingUploadURIs.pop();
            progressDialogHandler.setProgress(true);
            uploadImage(uri);
        } else {
            progressDialogHandler.setProgress(false);
            product.setImages(imageUrls);
            openNextScreen();
        }
    }

    private void uploadImage(Uri imageUri) {
        File file = new File(getPath(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        String token = DesignerLoginPreferences.createInstance(this).getSessionToken();

        subscriptions.add(RestUtils.getAPIs().uploadImage(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        String url = response.getData();
                        if (!TextUtils.isEmpty(url)) {
                            imageUrls.add(url);
                            uploadImagesAndOpenNextScreen();
                        } else {
                            progressDialogHandler.setProgress(false);
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        progressDialogHandler.setProgress(false);
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    throwable.printStackTrace();
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    protected void openNextScreen() {
        startActivity(SelectProductColorsActivity.createIntent(this, product));
    }
}
