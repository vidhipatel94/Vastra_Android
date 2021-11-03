package com.esolution.vastrashopper.ui.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.databinding.RowFilterTypeBinding;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterTypeBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.ViewHolder holder, int position) {
        holder.binding.textView.setText("XL");
        holder.binding.textViewNumber.setText("19");
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterTypeBinding binding;

        public ViewHolder(@NonNull RowFilterTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
