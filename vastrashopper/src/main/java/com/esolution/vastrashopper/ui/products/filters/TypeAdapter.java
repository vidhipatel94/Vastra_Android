package com.esolution.vastrashopper.ui.products.filters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_GENDER_AGE = 1;
    private static final int TYPE_LIST_ITEM = 2;

    private final ArrayList<ProductType> displayingProductTypes;
    protected final ArrayList<Integer> selectedDisplayingProductTypes = new ArrayList<>();
    private List<Integer> prevSelectedTypes;
    public Listener listener;

    public TypeAdapter(ArrayList<ProductType> displayingProductTypes,
                       List<Integer> prevSelectedTypes, Listener listener) {
        this.displayingProductTypes = displayingProductTypes;
        this.prevSelectedTypes = prevSelectedTypes;
        this.listener = listener;
    }

    public ArrayList<Integer> getSelectedProductTypes() {
        return selectedDisplayingProductTypes;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_GENDER_AGE;
        } else {
            return TYPE_LIST_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_GENDER_AGE) {
            return new GenderAgeViewHolder(LayoutFilterTypeBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new ListViewHolder(RowFilterBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == TYPE_GENDER_AGE) {
            GenderAgeViewHolder holder1 = (GenderAgeViewHolder) holder;
            onBindGenderAgeViewHolder(holder1);
        } else {
            ListViewHolder holder2 = (ListViewHolder) holder;
            onBindListViewHolder(holder2, position);
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
        int gender = -1;
        if (holder.binding.rbFemale.isChecked()) {
            gender = ProductType.GENDER_FEMALE;
        } else if (holder.binding.rbMale.isChecked()) {
            gender = ProductType.GENDER_MALE;
        } else if (holder.binding.rbUnisex.isChecked()) {
            gender = ProductType.GENDER_BOTH;
        }

        int age = -1;
        if (holder.binding.rbBaby.isChecked()) {
            age = ProductType.AGE_GROUP_BABY;
        } else if (holder.binding.rbKids.isChecked()) {
            age = ProductType.AGE_GROUP_KIDS;
        } else if(holder.binding.rbAdult.isChecked()){
            age = ProductType.AGE_GROUP_ADULTS;
        }

        if (listener != null) {
            listener.onGenderAgeChanged(gender, age);
            selectedDisplayingProductTypes.clear();
        }
    }

    private void onBindListViewHolder(ListViewHolder holder, int position) {
        /*if(position >= displayingProductTypes.size()) {
            return;
        }*/
        ProductType productType = displayingProductTypes.get(position - 1);

        holder.binding.chkBox.setText(productType.getName());

        holder.binding.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!selectedDisplayingProductTypes.contains(productType.getId())) {
                        selectedDisplayingProductTypes.add(productType.getId());
                        //Log.i("A", "onItemAdded: " + position + " " + productType.getId());
                    }
                } else {
                    //Log.i("R", "onItemRemoved: " + position + " " + productType.getId());
                    selectedDisplayingProductTypes.remove((Integer) productType.getId());
                }
            }
        });

        if (selectedDisplayingProductTypes.contains(productType.getId())) {
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.chkBox.setChecked(false);
        }

        if(prevSelectedTypes != null) {
            if(prevSelectedTypes.contains(productType.getId())) {
                holder.binding.chkBox.setChecked(true);
            } else {
                holder.binding.chkBox.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return displayingProductTypes.size() + 1;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        RowFilterBinding binding;

        public ListViewHolder(@NonNull RowFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class GenderAgeViewHolder extends RecyclerView.ViewHolder {

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
