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

public class KnitWovenAdapter extends RecyclerView.Adapter<KnitWovenAdapter.ViewHolder> {

    private final String[] knit_wovens;
    private final ArrayList<Integer> selectedKnitWovens = new ArrayList<>();

    public KnitWovenAdapter(String[] knit_wovens) {
        this.knit_wovens = knit_wovens;
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
            int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedKnitWovens.contains(value)) {
                        selectedKnitWovens.add(value);
                        Log.i("A", "onItemAdded: " + value);
                    }
                } else {
                    Log.i("R", "onItemRemoved: " + value);
                    selectedKnitWovens.remove((Integer) value);
                }
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
