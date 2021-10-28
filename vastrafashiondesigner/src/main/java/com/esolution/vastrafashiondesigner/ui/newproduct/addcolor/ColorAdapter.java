package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ListColorBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private final List<Color> colors;

    private int selectedPosition = -1;

    public ColorAdapter(@NotNull List<Color> colors, int selectedPosition) {
        this.colors = colors;
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListColorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.prominentColorText.setText(colors.get(position).getName());
        holder.binding.prominentColorView.setColor(colors.get(position).getHexCode());

        holder.binding.iconSelected.setVisibility(position == selectedPosition ? View.VISIBLE : View.INVISIBLE);

        holder.binding.itemLayout.setOnClickListener((view) -> {
            int prevPosition = selectedPosition;
            selectedPosition = position;
            if (prevPosition != -1) {
                notifyItemChanged(prevPosition);
            }
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListColorBinding binding;

        public ViewHolder(@NotNull ListColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
