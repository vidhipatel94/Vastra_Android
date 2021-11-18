package com.esolution.vastrashopper.ui.products.filters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.databinding.RowFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OccasionAdapter extends RecyclerView.Adapter<OccasionAdapter.ViewHolder> {

    private final String[] occasions;
    private final ArrayList<Integer> selectedOccasions = new ArrayList<>();
    //private final List<Integer> prevSelectedOccasions;

    public OccasionAdapter(@NotNull String[] occasions, List<Integer> prevSelectedOccasions) {
        this.occasions = occasions;
        if(prevSelectedOccasions != null) {
            this.selectedOccasions.addAll(prevSelectedOccasions);
        }
    }

    public ArrayList<Integer> getSelectedOccasions() {
        return selectedOccasions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionAdapter.ViewHolder holder, int position) {
        String occasion = occasions[position];

        holder.binding.chkBox.setText(occasion);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final int value = position + 1;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!selectedOccasions.contains(value)) {
                        selectedOccasions.add(value);
                    }
                } else {
                    selectedOccasions.remove((Integer) value);
                }
            }
        });

        if (selectedOccasions.contains(position + 1)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return occasions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
