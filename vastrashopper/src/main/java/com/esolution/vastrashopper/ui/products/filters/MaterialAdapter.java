package com.esolution.vastrashopper.ui.products.filters;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.product.Material;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {

    private final List<Material> materials;
    private final ArrayList<Integer> selectedMaterials = new ArrayList<>();

    private final List<Integer> prevSelectedMaterials;

    public MaterialAdapter(@NotNull List<Material> materials, List<Integer> prevSelectedMaterials) {
        this.materials = materials;
        this.prevSelectedMaterials = prevSelectedMaterials;
    }

    public ArrayList<Integer> getSelectedMaterials() {
        return selectedMaterials;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialAdapter.ViewHolder holder, int position) {
        Material material = materials.get(position);

        holder.binding.chkBox.setText(material.getMaterial());

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!selectedMaterials.contains(material.getId())){
                        selectedMaterials.add(material.getId());
                    }
                } else {
                    selectedMaterials.remove((Integer) material.getId());
                }
            }
        });

        if(selectedMaterials.contains(material.getId())) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }

        if(prevSelectedMaterials != null) {
            if(prevSelectedMaterials.contains(material.getId())) {
                holder.binding.chkBox.setChecked(true);
            } else {
                holder.binding.chkBox.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
