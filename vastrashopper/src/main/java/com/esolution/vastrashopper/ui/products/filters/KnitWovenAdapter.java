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

import java.util.ArrayList;
import java.util.List;

public class KnitWovenAdapter extends RecyclerView.Adapter<KnitWovenAdapter.ViewHolder> {

    private final String[] knit_wovens;
    private final ArrayList<Integer> selectedKnitWovens = new ArrayList<>();
    //private final List<Integer> prevSelectedKnitWovens;

    public KnitWovenAdapter(String[] knit_wovens, List<Integer> prevSelectedKnitWovens) {
        this.knit_wovens = knit_wovens;
        if(prevSelectedKnitWovens != null) {
            this.selectedKnitWovens.addAll(prevSelectedKnitWovens);
        }
    }

    public ArrayList<Integer> getSelectedKnitWovens() {
        return selectedKnitWovens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull KnitWovenAdapter.ViewHolder holder, int position) {
        String knit_woven = knit_wovens[position];

        holder.binding.chkBox.setText(knit_woven);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedKnitWovens.contains(value)) {
                        selectedKnitWovens.add(value);
                    }
                } else {
                    selectedKnitWovens.remove((Integer) value);
                }
            }
        });

        /*if(prevSelectedKnitWovens != null){
            if(prevSelectedKnitWovens.contains(position + 1)) {
                holder.binding.chkBox.setChecked(true);
            } else {
                holder.binding.chkBox.setChecked(false);
            }
        }*/
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
