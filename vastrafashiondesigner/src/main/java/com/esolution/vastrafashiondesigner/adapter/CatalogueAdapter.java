package com.esolution.vastrafashiondesigner.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrafashiondesigner.databinding.RowCatalogueBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
    public ArrayList<String> stringArrayList;

    public CatalogueAdapter(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowCatalogueBinding rowCatalogueItemBinding;

        public ViewHolder(RowCatalogueBinding catalogueItemBinding) {
            super(catalogueItemBinding.getRoot());
            this.rowCatalogueItemBinding = catalogueItemBinding;
        }
    }

    @NonNull
    @NotNull
    @Override
    public CatalogueAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RowCatalogueBinding catalogueItemBinding = RowCatalogueBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(catalogueItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogueAdapter.ViewHolder holder, int position) {
        String str = stringArrayList.get(position);

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
