package com.esolution.vastrashopper.ui.products.filters;

import android.util.Log;
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

    private static final String ONESIZE = "One Size";

    private final List<String> customSizes;
    private final String[] letterSizes;
    //private final ArrayList<String> selectedSizes = new ArrayList<>();
    private final ArrayList<String> selectedBrandSizes = new ArrayList<>();
    private final ArrayList<String> selectedCustomSizes = new ArrayList<>();

    private final List<String> prevSelectedBrandSize;
    private final List<String> prevSelectedCustomSize;


    public SizeAdapter(List<String> customSizes, String[] letterSizes,
                       List<String> prevSelectedBrandSize, List<String> prevSelectedCustomSize) {
        this.customSizes = customSizes;
        this.letterSizes = letterSizes;
        this.prevSelectedBrandSize = prevSelectedBrandSize;
        this.prevSelectedCustomSize = prevSelectedCustomSize;
    }

    public ArrayList<String> getSelectedBrandSizes() {
        return selectedBrandSizes;
    }

    public ArrayList<String> getSelectedCustomSizes() {
        return selectedCustomSizes;
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
            holder.binding.chkBox.setText(ONESIZE);
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
                if (isChecked) {
                    if (position == 0) {
                        //selectedSizes.add(ONESIZE);
                        if (!selectedBrandSizes.contains(ONESIZE)) {
                            selectedBrandSizes.add(ONESIZE);
                        }
                    } else if (position >= 1 && position <= letterSizes.length) {
                        //selectedSizes.add(letterSizes[position-1]);
                        if (!selectedBrandSizes.contains(letterSizes[position - 1])) {
                            selectedBrandSizes.add(letterSizes[position - 1]);
                        }
                    } else {
                        //selectedSizes.add(customSizes.get(position - letterSizes.length - 1));
                        String value = customSizes.get(position - letterSizes.length - 1);
                        if (!selectedCustomSizes.contains(value)) {
                            selectedCustomSizes.add(value);
                        }
                    }
                } else {
                    if (position == 0) {
                        selectedBrandSizes.remove(ONESIZE);
                    } else if (position >= 1 && position <= letterSizes.length) {
                        selectedBrandSizes.remove(letterSizes[position - 1]);
                    } else {
                        selectedCustomSizes.remove(customSizes.get(position - letterSizes.length - 1));
                    }
                }
            }
        });

        if (selectedBrandSizes.contains(ONESIZE)) {
            holder.binding.chkBox.setChecked(true);
        } else if (selectedBrandSizes.contains(letterSize)) {
            holder.binding.chkBox.setChecked(true);
        } else if (selectedCustomSizes.contains(customSize)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }

        if (prevSelectedBrandSize != null) {
            if (prevSelectedBrandSize.contains(ONESIZE)  && position == 0) {
                holder.binding.chkBox.setChecked(true);
            } else if (prevSelectedBrandSize.contains(letterSize)
                    && (position >= 1 && position <= letterSizes.length)) {
                holder.binding.chkBox.setChecked(true);
            } else {
                holder.binding.chkBox.setChecked(false);
            }
        }

        if((prevSelectedBrandSize != null && prevSelectedCustomSize != null) ||
                (prevSelectedBrandSize == null && prevSelectedCustomSize != null)) {
            if (prevSelectedCustomSize.contains(customSize)
                    && position >= letterSizes.length + 1
                    && position <= customSizes.size() + letterSizes.length + 1) {
                holder.binding.chkBox.setChecked(true);
            }
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
