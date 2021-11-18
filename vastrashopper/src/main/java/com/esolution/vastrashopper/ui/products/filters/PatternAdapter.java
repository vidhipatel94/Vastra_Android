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

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ViewHolder> {

    private final String[] patterns;
    protected final ArrayList<Integer> selectedPatterns = new ArrayList<>();
    //protected final List<Integer> prevSelectedPatterns;

    public PatternAdapter(@NotNull String[] patterns, List<Integer> prevSelectedPatterns) {
        this.patterns = patterns;
        if(prevSelectedPatterns != null) {
            this.selectedPatterns.addAll(prevSelectedPatterns);
        }
    }

    public ArrayList<Integer> getSelectedPatterns() {
        return selectedPatterns;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatternAdapter.ViewHolder holder, int position) {
        String pattern = patterns[position];

        holder.binding.chkBox.setText(pattern);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int value = position + 1;
                if (isChecked) {
                    if (!selectedPatterns.contains(value)) {
                        selectedPatterns.add(value);
                    }
                } else {
                    selectedPatterns.remove((Integer) (value));
                }
            }
        });

        // For showing previously selected item on swapping the filter options
        if (selectedPatterns.contains(position + 1)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
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
