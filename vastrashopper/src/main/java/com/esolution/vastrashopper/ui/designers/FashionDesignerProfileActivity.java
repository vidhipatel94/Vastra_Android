package com.esolution.vastrashopper.ui.designers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.apis.request.FollowerRequest;
import com.esolution.vastrabasic.databinding.RowCatalogueBinding;
import com.esolution.vastrabasic.models.BasicCatalogue;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.ui.chat.ChatActivity;
import com.esolution.vastrabasic.ui.products.CatalogueProductsAdapter;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.ActivityFashionDesignerProfileBinding;
import com.esolution.vastrashopper.ui.products.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FashionDesignerProfileActivity extends BaseActivity {

    private static final String EXTRA_DESIGNER = "extra_designer";

    public static Intent createIntent(Context context, Designer designer) {
        Intent intent = new Intent(context, FashionDesignerProfileActivity.class);
        intent.putExtra(EXTRA_DESIGNER, designer);
        return intent;
    }

    private ActivityFashionDesignerProfileBinding binding;
    private Designer designer;

    private ProgressDialogHandler progressDialogHandler;
    private List<BasicCatalogue> catalogues = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityFashionDesignerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();

        getCatalogues();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            designer = (Designer) getIntent().getSerializableExtra(EXTRA_DESIGNER);
            return designer != null;
        }
        return false;
    }

    private void initView() {
        binding.toolbarLayout.iconBack.setOnClickListener((View.OnClickListener) v -> onBackPressed());

        ImageUtils.loadImageUrl(binding.imageProfilePic, designer.getAvatarURL(),
                ContextCompat.getDrawable(this, R.drawable.designer));
        binding.fashionDesignerName.setText(designer.getFirstName() + " " + designer.getLastName());
        binding.brandName.setText(designer.getBrandName());
        binding.textTagLine.setText(designer.getTagline());

        binding.noOfFollowers.setText(String.valueOf(designer.getTotalFollowers()));
        binding.noOfPosts.setText(String.valueOf(designer.getTotalProducts()));

        setFollowing(designer.isFollowing());

        binding.btnChat.setOnClickListener(v -> {
            openChat();
        });

        binding.btnFollow.setOnClickListener(v -> {
            setFollowing(!designer.isFollowing());
            saveFollowing();
        });
    }

    private void openChat() {
        User user = ShopperLoginPreferences.createInstance(this).getShopper();
        startActivity(ChatActivity.createIntent(this, user, designer,
                ChatActivity.getChatId(designer.getUserId(), user.getUserId())));
    }

    private void setFollowing(boolean isFollowing) {
        designer.setFollowing(isFollowing);
        binding.btnFollow.setImageDrawable(ContextCompat.getDrawable(this,
                isFollowing ? R.drawable.ic_person_followed_24dp : R.drawable.ic_person_follow_gray_500_24dp));
        binding.btnFollow.setBackground(ContextCompat.getDrawable(this,
                isFollowing ? R.drawable.bg_followed_icon_filled : R.drawable.bg_follow_icon_filled));
        binding.btnFollow.setColorFilter(ContextCompat.getColor(this,
                isFollowing ? R.color.accent : R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

        int paddingDp = isFollowing ? 11 : 12;
        float density = getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * density);
        binding.btnFollow.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
    }

    private void getCatalogues() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().getCatalogues(preferences.getSessionToken(), designer.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        catalogues.clear();
                        List<BasicCatalogue> data = response.getData();
                        if (data != null) {
                            catalogues.addAll(data);
                        }
                        refreshView();
                    } else if (binding != null) {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    if (binding != null) {
                        showMessage(binding.getRoot(), message);
                    }
                }));
    }

    private void refreshView() {
        if (binding == null) return;

        binding.cataloguesLayout.removeAllViews();

        int totalCatalogues = catalogues.size();
        if (totalCatalogues > 0) {
            binding.emptyListTextView.setVisibility(View.INVISIBLE);
            for (int i = 0; i < totalCatalogues; i++) {
                BasicCatalogue catalogue = catalogues.get(i);

                List<BasicProduct> products = catalogue.getProducts();
                if (products != null && !products.isEmpty()) {

                    RowCatalogueBinding catalogueBinding = RowCatalogueBinding.inflate(getLayoutInflater());
                    binding.cataloguesLayout.addView(catalogueBinding.getRoot());

                    catalogueBinding.title.setText(catalogue.getName());
                    catalogueBinding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                            LinearLayoutManager.HORIZONTAL, false);
                    catalogueBinding.products.setLayoutManager(layoutManager);
                    catalogueBinding.products.setAdapter(new CatalogueProductsAdapter(products, this::onClickProduct));

                    catalogueBinding.linkShowAll.setOnClickListener((v) -> {
                        openCatalogueProducts(catalogue);
                    });
                }
            }
        } else {
            binding.emptyListTextView.setVisibility(View.VISIBLE);
        }
    }

    private void openCatalogueProducts(BasicCatalogue catalogue) {
        startActivity(CatalogueProductsActivity.createIntent(this, catalogue));
    }

    private void onClickProduct(BasicProduct basicProduct) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductId", basicProduct.getId());
        startActivity(intent);
    }

    private void saveFollowing() {
        if (designer.isFollowing()) {
            addFollower();
        } else {
            removeFollower();
        }
    }

    private void addFollower() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(this);
        FollowerRequest request = new FollowerRequest(designer.getId(), preferences.getShopper().getUserId());
        subscriptions.add(RestUtils.getAPIs().addFollower(preferences.getSessionToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (!response.isSuccess()) {
                        showMessage(binding.getRoot(), response.getMessage());
                        setFollowing(false);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    if (binding != null) {
                        showMessage(binding.getRoot(), message);
                        setFollowing(false);
                    }
                }));
    }

    private void removeFollower() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(this);
        FollowerRequest request = new FollowerRequest(designer.getId(), preferences.getShopper().getUserId());
        subscriptions.add(RestUtils.getAPIs().removeFollower(preferences.getSessionToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (!response.isSuccess()) {
                        showMessage(binding.getRoot(), response.getMessage());
                        setFollowing(true);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    if (binding != null) {
                        showMessage(binding.getRoot(), message);
                        setFollowing(true);
                    }
                }));
    }
}
