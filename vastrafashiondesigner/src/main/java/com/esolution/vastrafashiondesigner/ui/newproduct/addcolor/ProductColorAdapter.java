package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrafashiondesigner.databinding.ListProductColorBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ViewHolder> {

    private final List<ProductColor> productColors;

    private Listener listener;

    public ProductColorAdapter(@NotNull List<ProductColor> productColors, Listener listener) {
        this.productColors = productColors;
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ProductColorAdapter.ViewHolder(ListProductColorBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        ProductColor productColor = productColors.get(position);

        holder.binding.prominentColorText.setText(productColor.getProminentColorName());
        holder.binding.prominentColorView.setColor(productColor.getProminentColorHexCode());

        if (!TextUtils.isEmpty(productColor.getSecondaryColorName())) {
            holder.binding.secondaryColorLayout.setVisibility(View.VISIBLE);
            holder.binding.secondaryColorText.setText(productColor.getSecondaryColorName());
            holder.binding.secondaryColorView.setColor(productColor.getSecondaryColorHexCode());
        } else {
            holder.binding.secondaryColorLayout.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(productColor.getThirdColorName())) {
            holder.binding.tertiaryColorLayout.setVisibility(View.VISIBLE);
            holder.binding.tertiaryColorText.setText(productColor.getThirdColorName());
            holder.binding.tertiaryColorView.setColor(productColor.getThirdColorHexCode());
        } else {
            holder.binding.tertiaryColorLayout.setVisibility(View.GONE);
        }

        holder.binding.iconEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickEditProductColor(position);
            }
        });
        holder.binding.iconDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickDeleteProductColor(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productColors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListProductColorBinding binding;

        public ViewHolder(@NotNull ListProductColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Listener {
        void onClickEditProductColor(int index);

        void onClickDeleteProductColor(int index);
    }
}
