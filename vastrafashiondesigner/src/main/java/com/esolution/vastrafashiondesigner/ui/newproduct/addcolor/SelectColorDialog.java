package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.FragmentDialogSelectColorBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectColorDialog extends DialogFragment {

    private static final String EXTRA_LEVEL = "extra_level";

    public static SelectColorDialog newInstance(int colorLevel,
                                                ArrayList<Color> colors,
                                                SelectColorListener listener) {
        return newInstance(colorLevel, colors, listener, -1);
    }

    public static SelectColorDialog newInstance(int colorLevel,
                                                ArrayList<Color> colors,
                                                SelectColorListener listener,
                                                int defaultSelectedIndex) {
        SelectColorDialog dialog = new SelectColorDialog(listener, colors, defaultSelectedIndex);
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_LEVEL, colorLevel);
        dialog.setArguments(bundle);
        return dialog;
    }

    public interface SelectColorListener {
        void onColorSelected(Color color);
    }

    private FragmentDialogSelectColorBinding binding;
    private SelectColorListener listener;
    private int colorLevel = 1;

    private List<Color> colors;
    private ColorAdapter adapter;

    private int defaultSelectedIndex = -1;

    public SelectColorDialog(SelectColorListener listener, @NotNull ArrayList<Color> colors) {
        this.listener = listener;
        this.colors = colors;
    }

    public SelectColorDialog(SelectColorListener listener, @NotNull ArrayList<Color> colors, int defaultSelectedIndex) {
        this.listener = listener;
        this.colors = colors;
        this.defaultSelectedIndex = defaultSelectedIndex;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            colorLevel = getArguments().getInt(EXTRA_LEVEL, 1);
        }

        binding = FragmentDialogSelectColorBinding.inflate(getLayoutInflater());
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .setPositiveButton(colorLevel < 3 ? R.string.btn_next : R.string.btn_done,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing, override later
                            }
                        })
                .setNegativeButton(R.string.cancel, null);
        if (colorLevel > 1) {
            builder.setNeutralButton(R.string.skip, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onColorSelected(null);
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    private void initView() {
        setTitle();

        adapter = new ColorAdapter(colors, defaultSelectedIndex);
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.colorsRecyclerView.setAdapter(adapter);
    }

    private void setTitle() {
        int titleText;
        switch (colorLevel) {
            case 2:
                titleText = R.string.title_secondary_color;
                break;
            case 3:
                titleText = R.string.title_tertiary_color;
                break;
            default:
                titleText = R.string.title_prominent_color;
                break;
        }
        binding.title.setText(titleText);
        binding.iconClose.setOnClickListener((view) -> dismiss());
    }

    @Override
    public void onResume() {
        super.onResume();

        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (adapter.getSelectedPosition() >= 0) {
                listener.onColorSelected(colors.get(adapter.getSelectedPosition()));
                dismiss();
            }
        });
    }
}
