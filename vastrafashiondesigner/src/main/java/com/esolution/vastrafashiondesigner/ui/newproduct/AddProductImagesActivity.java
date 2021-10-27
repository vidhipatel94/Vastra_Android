package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductImagesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductImageBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addcolor.SelectProductColorsActivity;

import org.jetbrains.annotations.NotNull;

public class AddProductImagesActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductImagesActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityAddProductImagesBinding binding;

    private Catalogue catalogue;
    private Product product;

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

        Log.d("--------", "onCreate: "+ JsonUtils.toJson(product));

        binding = ActivityAddProductImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.addMoreProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RowAddProductImageBinding imageBinding = RowAddProductImageBinding.inflate(getLayoutInflater());
                binding.addMoreImageLinearLayout.addView(imageBinding.getRoot());

                imageBinding.iconDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.addMoreImageLinearLayout.removeView(imageBinding.getRoot());
                    }
                });
            }
        });

        binding.productImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(1);
            }
        });

        binding.productImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(2);
            }
        });

        binding.productImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(3);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductImagesActivity.this, SelectProductColorsActivity.class);
                startActivity(intent);
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(AddProductImagesActivity.this);
            }
        });

        activityResultLauncherGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUrl = result.getData().getData();
                    switch (photoIndex) {
                        case 1:
                            binding.productImage1.setImageURI(imageUrl);
                            return;
                        case 2:
                            binding.productImage2.setImageURI(imageUrl);
                            return;
                        case 3:
                            binding.productImage3.setImageURI(imageUrl);
                    }
                }
            }
        });
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
            Log.i("permissiongranted", "checkGalleryPermission: ");
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

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
