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

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ViewHolder> {

    private final String[] patterns;
    protected final ArrayList<Integer> selectedPatterns = new ArrayList<>();

    public PatternAdapter(@NotNull String[] patterns) {
        this.patterns = patterns;
    }

    public ArrayList<Integer> getSelectedPatterns() {
        return selectedPatterns;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatternAdapter.ViewHolder holder, int position) {
        String pattern = patterns[position];

        holder.binding.chkBox.setText(pattern);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int value = position + 1;
                if(isChecked) {
                    if(!selectedPatterns.contains(value)) {
                        //Log.i("A", "onItemAdded: " + value);
                        selectedPatterns.add(value);
                    }
                } else {
                    //Log.i("R", "onItemRemoved: " + value);
                    selectedPatterns.remove((Integer) (value));
                }
            }
        });

        /*if(selectedPatterns.contains(position)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }*/
    }

    @Override
    public int getItemCount() {
        return patterns.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
