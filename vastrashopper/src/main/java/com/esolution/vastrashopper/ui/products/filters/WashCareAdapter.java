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

public class WashCareAdapter extends RecyclerView.Adapter<WashCareAdapter.ViewHolder> {

    private final String[] washcares;
    private final ArrayList<Integer> selectedWashCares = new ArrayList<>();

    private final List<Integer> prevSelectedWashCares;

    public WashCareAdapter(@NotNull String[] washcares, List<Integer> prevSelectedWashCares) {
        this.washcares = washcares;
        this.prevSelectedWashCares = prevSelectedWashCares;
    }

    public ArrayList<Integer> getSelectedWashCares() {
        return selectedWashCares;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WashCareAdapter.ViewHolder holder, int position) {
        String washcare = washcares[position];

        holder.binding.chkBox.setText(washcare);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedWashCares.add(value);
                } else {
                    selectedWashCares.remove((Integer) value);
                }
            }
        });

        if(prevSelectedWashCares != null) {
            if(prevSelectedWashCares.contains(position+1)){
                holder.binding.chkBox.setChecked(true);
            } else {
                holder.binding.chkBox.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return washcares.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

