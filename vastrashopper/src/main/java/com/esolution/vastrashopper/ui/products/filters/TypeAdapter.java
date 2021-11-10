package com.esolution.vastrashopper.ui.products.filters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductType;
import com.esolution.vastrashopper.databinding.LayoutFilterTypeBinding;
import com.esolution.vastrashopper.databinding.RowFilterBinding;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_GENDER_AGE = 1;
    private static final int TYPE_LIST_ITEM = 2;

    private ArrayList<ProductType> displayingProductTypes;

    public Listener listener;

    public TypeAdapter(ArrayList<ProductType> displayingProductTypes, Listener listener) {
        this.displayingProductTypes = displayingProductTypes;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_GENDER_AGE;
        } else {
            return TYPE_LIST_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_GENDER_AGE) {
            return new GenderAgeViewHolder(LayoutFilterTypeBinding
                    .inflate(LayoutInflater.from(parent.getContext()),parent,false));
        } else {
            return new ListViewHolder(RowFilterBinding
                    .inflate(LayoutInflater.from(parent.getContext()),parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if(viewType == TYPE_GENDER_AGE) {
            GenderAgeViewHolder holder1 = (GenderAgeViewHolder) holder;
            onBindGenderAgeViewHolder(holder1);
        } else {
            ListViewHolder holder2 = (ListViewHolder) holder;
            onBindListViewHolder(holder2,position);
        }
    }

    private void onBindGenderAgeViewHolder(GenderAgeViewHolder holder) {
        holder.binding.rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onGenderAgeChanged(holder);
            }
        });
        holder.binding.rgAgeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onGenderAgeChanged(holder);
            }
        });
    }

    private void onGenderAgeChanged(GenderAgeViewHolder holder) {
        int gender;
        if(holder.binding.rbMale.isChecked()) {
            gender = ProductType.GENDER_MALE;
        } else if(holder.binding.rbFemale.isChecked()) {
            gender = ProductType.GENDER_FEMALE;
        } else {
            gender = ProductType.GENDER_BOTH;
        }

        int age;
        if(holder.binding.rbBaby.isChecked()) {
            age = ProductType.AGE_GROUP_BABY;
        } else if(holder.binding.rbAdult.isChecked()) {
            age = ProductType.AGE_GROUP_ADULTS;
        } else {
            age = ProductType.AGE_GROUP_KIDS;
        }

        if (listener!=null){
            listener.onGenderAgeChanged(gender,age);
        }
    }


    private void onBindListViewHolder(ListViewHolder holder, int position) {
        /*if(position >= displayingProductTypes.size()) {
            return;
        }*/
        ProductType productType = displayingProductTypes.get(position-1);

        holder.binding.textView.setText(productType.getName());

        holder.binding.rowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.chkBox.setChecked(true);
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position + " item selected." , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayingProductTypes.size() + 1;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        RowFilterBinding binding;

        public ListViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class GenderAgeViewHolder extends RecyclerView.ViewHolder{

        LayoutFilterTypeBinding binding;

        public GenderAgeViewHolder(@NonNull LayoutFilterTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Listener {
        void onGenderAgeChanged(int gender, int ageGroup);
    }
}
