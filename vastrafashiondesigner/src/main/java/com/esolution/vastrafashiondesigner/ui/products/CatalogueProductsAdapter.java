package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.GridProductBinding;
import com.esolution.vastrabasic.databinding.ListProductBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CatalogueProductsAdapter extends RecyclerView.Adapter<CatalogueProductsAdapter.ViewHolder> {

    private List<BasicProduct> basicProducts = new ArrayList<>();
    private final ProductListListener listener;

    public CatalogueProductsAdapter(@NotNull List<BasicProduct> basicProducts, ProductListListener listener) {
        this.basicProducts = basicProducts;
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(GridProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        BasicProduct basicProduct = basicProducts.get(position);

        holder.binding.title.setText(basicProduct.getTitle());
        holder.binding.price.setText("$" + basicProduct.getPrice());

        if (basicProduct.getOverallRating() != 0) {
            holder.binding.rating.setText(String.valueOf(basicProduct.getOverallRating()));
        } else {
            holder.binding.rating.setVisibility(View.INVISIBLE);
        }

        String image = null;
        if (basicProduct.getImages() != null && !basicProduct.getImages().isEmpty()) {
            image = basicProduct.getImages().get(0);
        }
        if (image != null) {
            ImageUtils.loadImageUrl(holder.binding.image, image);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickProduct(basicProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return basicProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        GridProductBinding binding;

        public ViewHolder(@NotNull GridProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ProductListListener {
        void onClickProduct(BasicProduct product);
    }
}
