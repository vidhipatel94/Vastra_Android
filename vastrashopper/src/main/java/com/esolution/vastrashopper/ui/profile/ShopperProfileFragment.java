package com.esolution.vastrashopper.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.LanguageHelper;
import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.databinding.LayoutToolbarMenuItemBinding;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseFragment;
import com.esolution.vastrabasic.utils.AlertDialogHelper;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.ShopperHandler;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.FragmentShopperProfileBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShopperProfileFragment extends BaseFragment {

    private FragmentShopperProfileBinding binding;

    private ShopperLoginPreferences shopperLoginPreferences;
    private User user;

    private String[] provinces;
    private int selectedProvincePosition = -1;

    private ProgressDialogHandler progressDialogHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopperProfileBinding.inflate(inflater, container, false);

        shopperLoginPreferences = ShopperLoginPreferences.createInstance(requireContext());
        user = shopperLoginPreferences.getShopper();

        provinces = getResources().getStringArray(R.array.provinces);

        progressDialogHandler = new ProgressDialogHandler(getActivity());

        initView();

        fillData();

        return binding.getRoot();
    }

    private void initView() {
        binding.toolbarLayout.iconBack.setVisibility(View.GONE);
        binding.toolbarLayout.title.setText(R.string.title_my_profile);

        binding.toolbarLayout.layoutMenu.setVisibility(View.VISIBLE);

        LayoutToolbarMenuItemBinding menuItemBinding = LayoutToolbarMenuItemBinding.inflate(getLayoutInflater());
        menuItemBinding.actionMenuItem.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_logout_grey_500_24dp));
        binding.toolbarLayout.layoutMenu.addView(menuItemBinding.getRoot());

        menuItemBinding.actionMenuItem.setOnClickListener((v) -> onClickLogout());

        binding.inputProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProvinceDialog();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        binding.profilePic.setOnClickListener(v -> {
            showMessage(binding.getRoot(), getString(R.string.not_implemented));
        });

        binding.btnSave.setOnClickListener((v) -> {
            showMessage(binding.getRoot(), getString(R.string.not_implemented));
        });

        binding.linkForgotPassword.setOnClickListener((v) -> {
            showMessage(binding.getRoot(), getString(R.string.not_implemented));
        });

        String savedLng = LanguageHelper.getLanguage(requireContext());
        if (savedLng.equals("fr")) {
            binding.languageText.setText(R.string.language_fr);
        }
        binding.languageLayout.setOnClickListener(v -> openLanguageDialog());
    }

    private void openProvinceDialog() {
        closeKeyboard();
        new AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(provinces, Math.max(selectedProvincePosition, 0), null)
                .setTitle(R.string.dialog_province_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        selectedProvincePosition = selectedPosition;
                        binding.inputProvince.setText(provinces[selectedPosition]);
                    }
                }).show();
    }

    private void onClickLogout() {
        AlertDialogHelper.showDialog(requireContext(),
                getString(R.string.logout),
                getString(R.string.msg_logout),
                getString(R.string.logout),
                getString(R.string.cancel),
                new AlertDialogHelper.Listener() {
                    @Override
                    public void onClickPositiveButton() {
                        performLogout();
                    }

                    @Override
                    public void onClickNegativeButton() {

                    }
                });
    }

    private void performLogout() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(requireContext());
        subscriptions.add(RestUtils.getAPIs().logout(preferences.getSessionToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            preferences.logout();
                            onLoggedOut();
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(requireContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void onLoggedOut() {
        if (ShopperHandler.getListener() != null) {
            ShopperHandler.getListener().onLoggedOut();
        }
    }

    private void fillData() {
        if (user == null) return;

        binding.inputProfileName.setText(user.getFirstName() + " " + user.getLastName());
        binding.inputEmail.setText(user.getEmail());
        binding.inputCity.setText(user.getCity());
        binding.inputAddress.setText(user.getAddress());
        binding.inputPostalCode.setText(user.getPostalCode());

        if (!TextUtils.isEmpty(user.getAvatarURL())) {
            ImageUtils.loadImageUrl(binding.profilePic, user.getAvatarURL());
        }

        if (!TextUtils.isEmpty(user.getProvince())) {
            for (int i = 0; i < provinces.length; i++) {
                if (provinces[i].equals(user.getProvince())) {
                    selectedProvincePosition = i;
                    binding.inputProvince.setText(provinces[i]);
                    break;
                }
            }
        }
    }

    private void openLanguageDialog() {
        closeKeyboard();

        String[] languages = new String[2];
        languages[0] = getString(R.string.language_en);
        languages[1] = getString(R.string.language_fr);

        String savedLng = LanguageHelper.getLanguage(requireContext());
        int index = savedLng.equals("fr") ? 1 : 0;

        new AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(languages, index, null)
                .setTitle(R.string.select_language)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.languageText.setText(languages[selectedPosition]);
                        changeLanguage(selectedPosition);
                    }
                }).show();
    }

    private void changeLanguage(int index) {
        LanguageHelper.changeLocale(requireContext(), index == 0 ? "en" : "fr");
        if (ShopperHandler.getListener() != null) {
            ShopperHandler.getListener().restartApp();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}