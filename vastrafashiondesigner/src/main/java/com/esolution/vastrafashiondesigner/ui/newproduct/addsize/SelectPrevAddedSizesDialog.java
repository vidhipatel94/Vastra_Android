package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.LayoutDialogTitleBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SelectPrevAddedSizesDialog extends DialogFragment {

    private static final String EXTRA_PREV_PRODUCTS = "extra_prev_products";

    public static SelectPrevAddedSizesDialog newInstance(ArrayList<String> prevAddedProducts,
                                                         PrevAddedSizesListener listener) {
        SelectPrevAddedSizesDialog dialog = new SelectPrevAddedSizesDialog(listener);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA_PREV_PRODUCTS, prevAddedProducts);
        dialog.setArguments(bundle);
        return dialog;
    }

    public interface PrevAddedSizesListener {
        void onClickAddCustomSizes();
    }

    private ArrayList<String> prevAddedProducts;
    private final PrevAddedSizesListener listener;

    public SelectPrevAddedSizesDialog(@NonNull PrevAddedSizesListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            prevAddedProducts = getArguments().getStringArrayList(EXTRA_PREV_PRODUCTS);
        }
        if (prevAddedProducts == null || prevAddedProducts.isEmpty()) {
            listener.onClickAddCustomSizes();
            dismiss();
        }

        String[] list = new String[prevAddedProducts.size()];
        list = prevAddedProducts.toArray(list);

        LayoutDialogTitleBinding titleBinding = LayoutDialogTitleBinding.inflate(getLayoutInflater());
        titleBinding.title.setText(R.string.title_select_prev_addded_sizes);
        titleBinding.iconClose.setOnClickListener((v) -> {
            listener.onClickAddCustomSizes();
            dismiss();
        });

        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setCustomTitle(titleBinding.getRoot())
                .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton(R.string.btn_add_custom_sizes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickAddCustomSizes();
                    }
                })
                .setPositiveButton(R.string.btn_done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }
}
