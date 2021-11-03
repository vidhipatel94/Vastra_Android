package com.esolution.vastrashopper.ui.products.filters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.esolution.vastrashopper.databinding.RowColorBinding;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowColorBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        holder.binding.rowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.chkBox.setChecked(true);
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.filterColorView.setColor("#654321");
        holder.binding.chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.textView.setText("Brown");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowColorBinding binding;

        public ViewHolder(@NonNull RowColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
