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
import com.esolution.vastrabasic.databinding.ListProductBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<BasicProduct> products;
    private ProductListListener listener;

    public ProductAdapter(List<BasicProduct> products, ProductListListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Context context = holder.binding.getRoot().getContext();
        BasicProduct product = products.get(position);

        holder.binding.title.setText(product.getTitle());
        holder.binding.price.setText("$" + product.getPrice());
        holder.binding.designerName.setText(product.getDesignerName());
        holder.binding.brandName.setText(product.getBrandName());
        holder.binding.totalLikes.setText(context.getString(R.string.likes_count, product.getTotalLikes()));

        String image = null;
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            image = product.getImages().get(0);
        }
        if (image != null) {
            ImageUtils.loadImageUrl(holder.binding.image, image);
        }

        if (product.getOverallRating() != 0) {
            holder.binding.rating.setText(String.valueOf(product.getOverallRating()));
        } else {
            holder.binding.rating.setVisibility(View.INVISIBLE);
        }

        holder.binding.likeIcon.setImageDrawable(ContextCompat.getDrawable(context,
                product.isUserLiked() ? R.drawable.ic_thumb_up_grey_500_24dp : R.drawable.ic_thumb_up_off_grey_500_24dp));

        holder.binding.actionLike.setOnClickListener((v) -> {
            if (listener != null) {
                listener.onClickLike(position);
            }
        });
        holder.binding.iconMenu.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickMenu(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListProductBinding binding;

        public ViewHolder(@NotNull ListProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ProductListListener {
        void onClickMenu(View view, int position);

        void onClickLike(int position);
    }
}
