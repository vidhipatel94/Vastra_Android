package com.esolution.vastrashopper.ui.designers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.databinding.RowDesignerBinding;

import org.jetbrains.annotations.NotNull;

public class DesignersAdapter extends RecyclerView.Adapter<DesignersAdapter.ViewHolder> {

    @NotNull
    @Override
    public DesignersAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowDesignerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull DesignersAdapter.ViewHolder holder, int position) {
        Context context = holder.binding.getRoot().getContext();

        holder.binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Fashion Designer " + position + " Selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowDesignerBinding binding;

        public ViewHolder(@NotNull RowDesignerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
