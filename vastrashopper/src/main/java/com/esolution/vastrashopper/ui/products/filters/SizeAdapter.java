package com.esolution.vastrashopper.ui.products.filters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    private List<String> customSizes;
    private String[] letterSizes;

    public SizeAdapter(List<String> customSizes, String[] letterSizes) {
        this.customSizes = customSizes;
        this.letterSizes = letterSizes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.binding.textView.setText(R.string.one_size);
        } else if (position >= 1 && position <= letterSizes.length) {
            String letterSize = letterSizes[position - 1];
            holder.binding.textView.setText(letterSize);
        } else {
            String customSize = customSizes.get(position - letterSizes.length - 1);
            holder.binding.textView.setText(customSize);
        }

        holder.binding.rowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.chkBox.setChecked(true);
                Toast.makeText(v.getContext(), position + " item selected.", Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position + " item selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customSizes.size() + letterSizes.length + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowFilterBinding binding;

        public ViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
