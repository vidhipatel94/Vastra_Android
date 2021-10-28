package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityProductSizesBinding;
import com.esolution.vastrafashiondesigner.databinding.ListSizeTitleBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addcolor.SelectProductColorsActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.inventory.AddProductInventoryActivity;

import java.util.ArrayList;

public class ProductSizesActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, ProductSizesActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityProductSizesBinding binding;

    private Catalogue catalogue;
    private Product product;

    private final int totalParameters = 14;

    private ArrayList<String> sizes;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityProductSizesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbarLayout();

        openSelectPrevAddedSizesDialog();

        setSizesTitleLayout();
        setGridLayout();

        binding.btnUpdateSizes.setOnClickListener((v) -> onClickEditSizes());

        binding.btnNext.setOnClickListener((v) -> {
            Intent intent = new Intent(ProductSizesActivity.this, AddProductInventoryActivity.class);
            startActivity(intent);
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

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_product_sizes);
    }

    private void setSizesTitleLayout() {
        sizes = new ArrayList<>();
        sizes.add("");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("2XL");

        binding.sizesTitleLayout.removeAllViews();
        for (int i = 0; i < sizes.size(); i++) {
            ListSizeTitleBinding sizeBinding = ListSizeTitleBinding.inflate(LayoutInflater.from(this),
                    binding.sizesTitleLayout, false);
            sizeBinding.textView.setText(sizes.get(i));
            binding.sizesTitleLayout.addView(sizeBinding.getRoot());
        }
    }

    private void setGridLayout() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.label_US_size));
        list.add(getString(R.string.label_front_length));
        list.add(getString(R.string.label_back_length));
        list.add(getString(R.string.label_width));
        list.add(getString(R.string.label_head_circumference));
        list.add(getString(R.string.label_neck));
        list.add(getString(R.string.label_bust));
        list.add(getString(R.string.label_waist));
        list.add(getString(R.string.label_hip));
        list.add(getString(R.string.label_inseam_length));
        list.add(getString(R.string.label_outseam_length));
        list.add(getString(R.string.label_sleeve_length));
        list.add(getString(R.string.label_wrist));
        list.add(getString(R.string.label_foot_length));

        // totalColumn = list.size() =  14

        for (int i = 0; i < (sizes.size() - 3) * totalParameters; i++) {
            list.add("888.88\" - 888.88\"");
        }
        for (int i = 0; i < totalParameters * 2; i++) {
            list.add(null);
        }

        SizeAdapter adapter = new SizeAdapter(list, totalParameters);
        binding.gridView.setAdapter(adapter);

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < totalParameters) {
                    return;
                }

                int column = position % totalParameters;
                String parameter = list.get(column);

                int row = (int) Math.floor(position / (float) totalParameters);
                String size = sizes.get(row);

                FragmentManager fragmentManager = getSupportFragmentManager();
                SizeMeasurementDialog dialog = SizeMeasurementDialog.newInstance(parameter, size);
                dialog.show(fragmentManager, SizeMeasurementDialog.class.getName());
            }
        });
    }

    private void openSelectPrevAddedSizesDialog() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Title 1");
        list.add("Title 2");
        list.add("Title 3");
        list.add("Title 4");
        list.add("Title 5");
        list.add("Title 6");
        list.add("Title 7");
        list.add("Title 8");
        list.add("Title 9");
        list.add("Title 10");
        list.add("Title 11");
        list.add("Title 12");
        list.add("Title 13");
        list.add("Title 14");
        list.add("Title 15");

        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectPrevAddedSizesDialog dialog = SelectPrevAddedSizesDialog.newInstance(list,
                new SelectPrevAddedSizesDialog.PrevAddedSizesListener() {
            @Override
            public void onClickAddCustomSizes() {
                onClickEditSizes();
            }
        });
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }

    private void onClickEditSizes() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddSizesDialog dialog = new AddSizesDialog();
        dialog.show(fragmentManager, AddSizesDialog.class.getName());
    }
}
