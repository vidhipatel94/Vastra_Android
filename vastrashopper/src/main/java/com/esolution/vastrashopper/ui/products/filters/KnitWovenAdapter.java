package com.esolution.vastrashopper.ui.products.filters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

public class KnitWovenAdapter extends RecyclerView.Adapter<KnitWovenAdapter.ViewHolder> {

    private String[] knit_wovens;

    public KnitWovenAdapter(String[] knit_wovens) {
        this.knit_wovens = knit_wovens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull KnitWovenAdapter.ViewHolder holder, int position) {
        String knit_woven = knit_wovens[position];

        holder.binding.textView.setText(knit_woven);

        holder.binding.rowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.chkBox.setChecked(true);
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return knit_wovens.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
