package com.esolution.vastrafashiondesigner.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.databinding.LayoutToolbarMenuItemBinding;
import com.esolution.vastrabasic.ui.BaseFragment;
import com.esolution.vastrabasic.utils.AlertDialogHelper;
import com.esolution.vastrafashiondesigner.FashionDesignerHandler;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.FragmentProfileBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;

    private static final String[] PROVINCES = new String[]{
            "Ontario", "New Brunswick", "Sasketchwan", "British Columbia", "Nova Scotia", "Quebec", "Alberta"
    };

    private ProgressDialogHandler progressDialogHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        progressDialogHandler = new ProgressDialogHandler(getActivity());

        initView();

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

        binding.changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openProvinceDialog() {
        new AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(PROVINCES, 0, null)
                .setTitle(R.string.dialog_province_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputProvince.setText(PROVINCES[selectedPosition]);
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
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(requireContext());
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
        if (FashionDesignerHandler.getListener() != null) {
            FashionDesignerHandler.getListener().onLoggedOut();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}