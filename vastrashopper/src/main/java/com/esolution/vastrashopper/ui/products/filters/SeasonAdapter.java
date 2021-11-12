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

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private final String[] seasons;
    private final ArrayList<Integer> selectedSeasons = new ArrayList<>();

    public SeasonAdapter(String[] seasons) {
        this.seasons = seasons;
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
            int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedSeasons.contains(value)) {
                        selectedSeasons.add(value);
                        Log.i("A", "onItemAdded: " + value);
                    }
                } else {
                    Log.i("R", "onItemRemoved: " + value);
                    selectedSeasons.remove((Integer) value);
                }
            }
        });
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
