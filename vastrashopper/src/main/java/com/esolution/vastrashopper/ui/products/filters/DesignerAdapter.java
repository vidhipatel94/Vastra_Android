package com.esolution.vastrashopper.ui.products.filters;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.ViewHolder> {

    private final ArrayList<Designer> designers;
    private final ArrayList<Integer> selectedDesigners = new ArrayList<>();

    public DesignerAdapter(@NotNull ArrayList<Designer> designers) {
        this.designers = designers;
    }

    public ArrayList<Integer> getSelectedDesigners() {
        return selectedDesigners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DesignerAdapter.ViewHolder holder, int position) {
        Designer designer = designers.get(position);

        holder.binding.chkBox.setText(designer.getFirstName().concat(" " + designer.getLastName()));

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedDesigners.contains(designer.getId())) {
                        selectedDesigners.add(designer.getId());
                        Log.i("A", "onItemAdded: " + designer.getId());
                    }
                } else {
                    Log.i("R", "onItemRemoved: " + designer.getId());
                    selectedDesigners.remove((Integer) designer.getId());
                }
            }
        });

        if(selectedDesigners.contains(designer.getId())) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return designers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
