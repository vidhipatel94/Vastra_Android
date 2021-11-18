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

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private final String[] seasons;
    private final ArrayList<Integer> selectedSeasons = new ArrayList<>();
    //private final List<Integer> prevSelectedSeasons;

    public SeasonAdapter(String[] seasons, List<Integer> prevSelectedSeasons) {
        this.seasons = seasons;
        if(prevSelectedSeasons != null) {
            this.selectedSeasons.addAll(prevSelectedSeasons);
        }
    }

    public ArrayList<Integer> getSelectedSeasons() {
        return selectedSeasons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, int position) {
        String season = seasons[position];

        holder.binding.chkBox.setText(season);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedSeasons.contains(value)) {
                        selectedSeasons.add(value);
                    }
                } else {
                    selectedSeasons.remove((Integer) value);
                }
            }
        });

        if(selectedSeasons.contains(position + 1)) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return seasons.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
