package com.esolution.vastrashopper.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.databinding.ListProductBinding;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.utils.ImageUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<BasicProduct> basicProductList;
    public Context context;

    public HomeAdapter(Context context, List<BasicProduct> basicProductList) {
        this.context = context;
        this.basicProductList = basicProductList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListProductBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull HomeAdapter.ViewHolder holder, int position) {
        BasicProduct basicProduct = basicProductList.get(position);

        holder.binding.title.setText(basicProduct.getTitle());
        holder.binding.rating.setText(String.valueOf(basicProduct.getOverallRating()));
        holder.binding.iconMenu.setVisibility(View.INVISIBLE);
        holder.binding.designerName.setText(basicProduct.getDesignerName());
        holder.binding.brandName.setText(basicProduct.getBrandName());
        holder.binding.totalLikes.setText(String.valueOf(basicProduct.getTotalLikes()));
        holder.binding.price.setText("$" + String.valueOf(basicProduct.getPrice()));
        ImageUtils.loadImageUrl(holder.binding.image, basicProduct.getImages().get(0));
    }

    @Override
    public int getItemCount() {
        return basicProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ListProductBinding binding;

        public ViewHolder(@NotNull ListProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
