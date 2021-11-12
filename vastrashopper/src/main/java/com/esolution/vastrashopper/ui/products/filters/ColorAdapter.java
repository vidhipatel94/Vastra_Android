package com.esolution.vastrashopper.ui.products.filters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrashopper.databinding.RowColorBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private final List<Color> colors;
    private final ArrayList<Integer> selectedColors = new ArrayList<>();

    public ColorAdapter(@NotNull List<Color> colors) {
        this.colors = colors;
    }

    public ArrayList<Integer> getSelectedColors() {
        return selectedColors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowColorBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        Color color = colors.get(position);

        holder.binding.filterColorView.setColor(color.getHexCode());
        holder.binding.chkBox.setText(color.getName());

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedColors.contains(color.getId())){
                        selectedColors.add(color.getId());
                        Log.i("A", "onItemAdded: " + color.getId());
                    }
                } else {
                    Log.i("R", "onItemRemoved: " + color.getId());
                    selectedColors.remove((Integer) color.getId());
                }
            }
        });

        if(selectedColors.contains(color.getId())) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowColorBinding binding;

        public ViewHolder(@NonNull RowColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
