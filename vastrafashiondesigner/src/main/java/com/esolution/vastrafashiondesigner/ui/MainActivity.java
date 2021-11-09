package com.esolution.vastrafashiondesigner.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityMainBinding;
import com.esolution.vastrafashiondesigner.ui.startup.RegisterCreateCatalogueActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialogHandler = new ProgressDialogHandler(this);

        if (!DesignerLoginPreferences.createInstance(this).isAnyCatalogueAdded()) {
            checkIfAnyProductIsAdded();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_orders, R.id.navigation_catalogues, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void checkIfAnyProductIsAdded() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        int designerId = preferences.getDesignerId();
        subscriptions.add(RestUtils.getAPIs().isDesignerProductsExist(preferences.getSessionToken(), designerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (!response.getData()) {
                            openCreateCatalogueScreen();
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

    private void openCreateCatalogueScreen() {
        Intent intent = new Intent(this, RegisterCreateCatalogueActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}