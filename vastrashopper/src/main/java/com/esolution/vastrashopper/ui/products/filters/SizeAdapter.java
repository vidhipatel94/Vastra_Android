package com.esolution.vastrashopper.ui.products.filters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

import java.util.ArrayList;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    private final List<String> customSizes;
    private final String[] letterSizes;
    private final ArrayList<String> selectedSizes = new ArrayList<>();

    public SizeAdapter(List<String> customSizes, String[] letterSizes) {
        this.customSizes = customSizes;
        this.letterSizes = letterSizes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdapter.ViewHolder holder, int position) {
        String letterSize = "";
        String customSize = "";
        if (position == 0) {
            holder.binding.chkBox.setText(R.string.one_size);
        } else if (position >= 1 && position <= letterSizes.length) {
            letterSize = letterSizes[position - 1];
            holder.binding.chkBox.setText(letterSize);
        } else {
            customSize = customSizes.get(position - letterSizes.length - 1);
            holder.binding.chkBox.setText(customSize);
        }

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(position == 0) {
                        selectedSizes.add("One Size");
                    } else if(position >= 1 && position <= letterSizes.length) {
                        selectedSizes.add(letterSizes[position-1]);
                    } else {
                        selectedSizes.add(customSizes.get(position - letterSizes.length - 1));
                    }
                }
            }
        });

        if(selectedSizes.contains("One Size")) {
            holder.binding.chkBox.setChecked(true);
        } else if(selectedSizes.contains(letterSize)) {
            holder.binding.chkBox.setChecked(true);
        } else if(selectedSizes.contains(customSize)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return customSizes.size() + letterSizes.length + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
