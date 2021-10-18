package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ListProductBinding;

import org.jetbrains.annotations.NotNull;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ProductListListener listener;

    public ProductAdapter(ProductListListener listener) {
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
        holder.binding.actionLike.setOnClickListener((v) -> {
            holder.binding.likeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_grey_500_24dp));
        });
        holder.binding.iconMenu.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickMenu(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
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
    }
}
