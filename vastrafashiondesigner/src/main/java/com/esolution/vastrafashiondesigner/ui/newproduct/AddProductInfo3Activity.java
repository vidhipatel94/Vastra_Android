package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductOccasion;
import com.esolution.vastrabasic.models.product.ProductSeason;
import com.esolution.vastrabasic.models.product.Season;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo3Binding;
import com.esolution.vastrafashiondesigner.ui.updateproduct.UpdateProductImagesActivity;

import java.util.ArrayList;
import java.util.List;

public class AddProductInfo3Activity extends BaseActivity {

    protected static final String EXTRA_CATALOGUE = "extra_catalogue";
    protected static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductInfo3Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    protected ActivityAddProductInfo3Binding binding;

    protected Catalogue catalogue;
    protected Product product;

    protected static String[] OCCASIONS;
    protected static String[] PATTERNS;
    protected static String[] WASHCARES;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInfo3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OCCASIONS = getResources().getStringArray(R.array.occasions);
        PATTERNS = getResources().getStringArray(R.array.patterns);
        WASHCARES = getResources().getStringArray(R.array.wash_cares);

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

    private void initView() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());
        binding.productTitle.setText(product.getTitle());

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.inputOccasion.setOnClickListener(v -> openOccasionDialog());
        binding.inputPattern.setOnClickListener(v -> openPatternDialog());
        binding.inputWashCare.setOnClickListener(v -> openWashcareDialog());

        binding.btnNext.setOnClickListener(v -> {
            closeKeyboard();
            if (isFormValidated()) {
                openNextScreen();
            }
        });
    }

    protected boolean[] selectedOccasions;

    private void openOccasionDialog() {
        if (selectedOccasions == null) {
            selectedOccasions = new boolean[OCCASIONS.length];
        }
        new AlertDialog.Builder(this)
                .setMultiChoiceItems(OCCASIONS, selectedOccasions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedOccasions[which] = isChecked;
                    }
                })
                .setTitle(R.string.select_occassion)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onUpdateOccasions();
                    }
                }).show();
    }

    protected void onUpdateOccasions() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < OCCASIONS.length; i++) {
            if (selectedOccasions[i]) {
                if (builder.length() != 0) {
                    builder.append(", ");
                }
                builder.append(OCCASIONS[i]);
            }
        }
        binding.inputOccasion.setText(builder.toString());
    }

    protected int selectedPattern;

    private void openPatternDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(PATTERNS, Math.max(selectedPattern - 1, 0), null)
                .setTitle(R.string.select_pattern)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        selectedPattern = selectedPosition + 1;
                        onUpdatePattern();
                    }
                }).show();
    }

    protected void onUpdatePattern() {
        if (selectedPattern < 1 || selectedPattern > PATTERNS.length) return;
        binding.inputPattern.setText(PATTERNS[selectedPattern - 1]);
    }

    protected int selectedWashcare;

    private void openWashcareDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(WASHCARES, Math.max(selectedWashcare - 1, 0), null)
                .setTitle(R.string.select_washcare)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        selectedWashcare = selectedPosition + 1;
                        onUpdateWashCare();
                    }
                }).show();
    }

    protected void onUpdateWashCare() {
        if (selectedWashcare < 1 || selectedWashcare > WASHCARES.length) return;
        binding.inputWashCare.setText(WASHCARES[selectedWashcare - 1]);
    }

    private boolean isFormValidated() {
        List<ProductOccasion> productOccasions = new ArrayList<>();
        for (int i = 0; selectedOccasions != null && i < selectedOccasions.length; i++) {
            if (selectedOccasions[i]) {
                ProductOccasion productOccasion = new ProductOccasion();
                productOccasion.setOccasion(i + 1);
                productOccasions.add(productOccasion);
            }
        }
        if (productOccasions.isEmpty()) {
            binding.inputLayoutOccasion.setErrorEnabled(true);
            binding.inputLayoutOccasion.setError(getString(R.string.error_empty_occasion));
            return false;
        }
        binding.inputLayoutOccasion.setErrorEnabled(false);

        List<ProductSeason> seasons = new ArrayList<>();
        if (binding.chkWinter.isChecked()) {
            ProductSeason productSeason = new ProductSeason();
            productSeason.setSeason(Season.WINTER);
            seasons.add(productSeason);
        }
        if (binding.chkSpring.isChecked()) {
            ProductSeason productSeason = new ProductSeason();
            productSeason.setSeason(Season.SPRING);
            seasons.add(productSeason);
        }
        if (binding.chkSummer.isChecked()) {
            ProductSeason productSeason = new ProductSeason();
            productSeason.setSeason(Season.SUMMER);
            seasons.add(productSeason);
        }
        if (binding.chkFall.isChecked()) {
            ProductSeason productSeason = new ProductSeason();
            productSeason.setSeason(Season.FALL);
            seasons.add(productSeason);
        }
        if (seasons.isEmpty()) {
            showMessage(binding.getRoot(), getString(R.string.error_empty_season));
            return false;
        }

        if (selectedPattern == 0) {
            binding.inputLayoutPattern.setErrorEnabled(true);
            binding.inputLayoutPattern.setError(getString(R.string.error_empty_pattern));
            return false;
        }
        binding.inputLayoutPattern.setErrorEnabled(false);

        int knitOrWoven;
        if (binding.rbKnit.isChecked()) {
            knitOrWoven = Product.KNIT;
        } else if (binding.rbWoven.isChecked()) {
            knitOrWoven = Product.WOVEN;
        } else {
            knitOrWoven = Product.NONE;
        }

        if (selectedWashcare == 0) {
            binding.inputLayoutWashCare.setErrorEnabled(true);
            binding.inputLayoutWashCare.setError(getString(R.string.error_empty_wash_care));
            return false;
        }
        binding.inputLayoutWashCare.setErrorEnabled(false);

        String trend = binding.inputTrend.getText().toString().trim();
        if (trend.isEmpty()) trend = null;

        product.setOccasions(productOccasions);
        product.setSeasons(seasons);
        product.setPattern(selectedPattern);
        product.setKnitOrWoven(knitOrWoven);
        product.setWashCare(selectedWashcare);
        product.setTrend(trend);

        return true;
    }

    protected void openNextScreen() {
        startActivity(AddProductImagesActivity.createIntent(this, catalogue, product));
    }
}
