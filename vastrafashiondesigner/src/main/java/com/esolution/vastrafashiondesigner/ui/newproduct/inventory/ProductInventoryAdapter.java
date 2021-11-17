package com.esolution.vastrafashiondesigner.ui.newproduct.inventory;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ListProductInventoryBinding;
import com.esolution.vastrafashiondesigner.databinding.RowProductSizeInventoryBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductInventoryAdapter extends RecyclerView.Adapter<ProductInventoryAdapter.ViewHolder> {

    private List<ProductColor> colors = new ArrayList<>();
    private List<ProductInventory> inventories = new ArrayList<>();

    private final List<String> errorEditTextTags = new ArrayList<>();

    public ProductInventoryAdapter(@NonNull List<ProductColor> colors, @NonNull List<ProductInventory> inventories) {
        this.colors.addAll(colors);
        this.inventories.addAll(inventories);
    }

    public List<ProductInventory> getInventories() {
        return inventories;
    }

    public boolean hasAnyError() {
        return !errorEditTextTags.isEmpty();
    }

    public void setColorsAndInventories(List<ProductColor> colors,List<ProductInventory> inventories) {
        this.colors.clear();
        this.colors.addAll(colors);
        this.inventories.clear();
        this.inventories.addAll(inventories);
        notifyDataSetChanged();
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
        Context context = holder.binding.getRoot().getContext();

        ProductColor color = colors.get(position);

        holder.binding.prominentColorText.setText(color.getProminentColorName());
        holder.binding.prominentColorView.setColor(color.getProminentColorHexCode());

        holder.binding.inventoryLayout.removeAllViews();
        for (int i = 0; i < inventories.size(); i++) {
            ProductInventory inventory = inventories.get(i);
            if (inventory.getProductColorId() == color.getId()) {
                RowProductSizeInventoryBinding rowBinding = RowProductSizeInventoryBinding
                        .inflate(LayoutInflater.from(context));
                holder.binding.inventoryLayout.addView(rowBinding.getRoot());

                rowBinding.sizeText.setText(inventory.getSizeName());
                rowBinding.qtyBox.setText(String.valueOf(inventory.getQuantityAvailable()));

                String tag = color.getId() + "-" + inventory.getSizeName();
                rowBinding.qtyBox.setTag(tag);

                int finalIndex = i;
                rowBinding.qtyBox.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String qtyStr = s.toString().trim();
                        int qty = 0;
                        if (!TextUtils.isEmpty(qtyStr)) {
                            try {
                                qty = Integer.parseInt(qtyStr);
                            } catch (NumberFormatException e) {
                                rowBinding.qtyBox.setError(context.getString(R.string.error_invalid_qty));
                                if (!errorEditTextTags.contains(tag)) {
                                    errorEditTextTags.add(tag);
                                }
                                return;
                            }
                        }
                        inventory.setQuantityAvailable(qty);
                        inventories.set(finalIndex, inventory);
                        errorEditTextTags.remove(tag);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListProductInventoryBinding binding;

        public ViewHolder(@NonNull ListProductInventoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}