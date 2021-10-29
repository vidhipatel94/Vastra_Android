package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.LayoutDialogTitleBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectPrevAddedSizesDialog extends DialogFragment {

    public interface PrevAddedSizesListener {
        void onClickAddCustomSizes();
    }

    private List<BasicProduct> prevAddedProducts;
    private final PrevAddedSizesListener listener;

    public SelectPrevAddedSizesDialog(@NotNull List<BasicProduct> prevAddedProducts, @NonNull PrevAddedSizesListener listener) {
        this.prevAddedProducts = prevAddedProducts;
        this.listener = listener;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        String[] list = new String[prevAddedProducts.size()];
        for (int i = 0; i < prevAddedProducts.size(); i++) {
            list[i] = prevAddedProducts.get(i).getTitle();
        }

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
