package com.esolution.vastrafashiondesigner.ui.newproduct.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ListProductInventoryBinding;
import com.esolution.vastrafashiondesigner.databinding.RowProductSizeInventoryBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductInventoryAdapter extends RecyclerView.Adapter<ProductInventoryAdapter.ViewHolder> {

    private List<String> sizes = new ArrayList<>();

    public ProductInventoryAdapter(@NonNull List<String> sizes) {
        this.sizes.addAll(sizes);
    }

    @NotNull
    @Override
    public ProductInventoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ProductInventoryAdapter
                .ViewHolder(ListProductInventoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductInventoryAdapter.ViewHolder holder, int position) {
        holder.binding.inventoryLayout.removeAllViews();
        for (int i = 0; i < sizes.size(); i++) {
            RowProductSizeInventoryBinding rowBinding = RowProductSizeInventoryBinding
                    .inflate(LayoutInflater.from(holder.binding.getRoot().getContext()));
            holder.binding.inventoryLayout.addView(rowBinding.getRoot());

            rowBinding.sizeText.setText(sizes.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListProductInventoryBinding binding;

        public ViewHolder(@NonNull ListProductInventoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}