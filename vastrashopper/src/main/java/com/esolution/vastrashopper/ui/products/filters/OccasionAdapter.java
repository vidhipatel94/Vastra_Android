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

public class OccasionAdapter extends RecyclerView.Adapter<OccasionAdapter.ViewHolder> {

    private static String[] occasions;
    private final ArrayList<Integer> selectedOccasions = new ArrayList<>();

    public OccasionAdapter(@NotNull String[] occasions) {
        this.occasions = occasions;
    }

    public ArrayList<Integer> getSelectedOccasions() {
        return selectedOccasions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionAdapter.ViewHolder holder, int position) {
        String occasion = occasions[position];

        holder.binding.chkBox.setText(occasion);

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int value = position + 1;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedOccasions.contains(value)) {
                        selectedOccasions.add(value);
                        Log.i("A", "onItemAdded: " + value);
                    }
                } else {
                    Log.i("R", "onItemRemoved: " + value);
                    selectedOccasions.remove((Integer) value);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return occasions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
