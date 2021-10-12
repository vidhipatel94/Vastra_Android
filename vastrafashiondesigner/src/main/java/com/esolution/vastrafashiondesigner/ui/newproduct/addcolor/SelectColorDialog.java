package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.FragmentDialogSelectColorBinding;

import org.jetbrains.annotations.NotNull;

public class SelectColorDialog extends DialogFragment {

    private static final String EXTRA_LEVEL = "extra_level";

    public static SelectColorDialog newInstance(int colorLevel, SelectColorListener listener) {
        SelectColorDialog dialog = new SelectColorDialog(listener);
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_LEVEL, colorLevel);
        dialog.setArguments(bundle);
        return dialog;
    }

    public interface SelectColorListener {
        void onColorSelected();
    }

    private FragmentDialogSelectColorBinding binding;
    private SelectColorListener listener;
    private int colorLevel = 1;

    public SelectColorDialog(SelectColorListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            colorLevel = getArguments().getInt(EXTRA_LEVEL, 1);
        }

        binding = FragmentDialogSelectColorBinding.inflate(getLayoutInflater());
        initView();
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .setPositiveButton(colorLevel < 3 ? R.string.btn_next : R.string.btn_done,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onColorSelected();
                            }
                        })
                .create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    private void initView() {
        setTitle();

        ColorAdapter adapter = new ColorAdapter();
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
}
