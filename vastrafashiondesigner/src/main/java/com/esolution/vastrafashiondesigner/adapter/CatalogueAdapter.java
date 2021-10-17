package com.esolution.vastrafashiondesigner.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.esolution.vastrafashiondesigner.databinding.RowCatalogueItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
    public ArrayList<String> stringArrayList;

    public CatalogueAdapter(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowCatalogueItemBinding rowCatalogueItemBinding;

        public ViewHolder(RowCatalogueItemBinding catalogueItemBinding) {
            super(catalogueItemBinding.getRoot());
            this.rowCatalogueItemBinding = catalogueItemBinding;
        }
    }

    @NonNull
    @NotNull
    @Override
    public CatalogueAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RowCatalogueItemBinding catalogueItemBinding = RowCatalogueItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(catalogueItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogueAdapter.ViewHolder holder, int position) {
        String str = stringArrayList.get(position);
        holder.rowCatalogueItemBinding.textName.setText("Catalogue " + str);
        holder.rowCatalogueItemBinding.textNoOfProducts.setText(str + " Products");
        holder.rowCatalogueItemBinding.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Catalogue: " + position + " Clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
