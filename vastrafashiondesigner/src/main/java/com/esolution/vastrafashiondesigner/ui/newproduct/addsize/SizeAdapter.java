package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.GridSizeMeasurementBinding;

import java.util.ArrayList;

public class SizeAdapter extends BaseAdapter {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_VALUE = 2;

    private final ArrayList<String> titleList;
    private final ArrayList<String> measurementList;

    public SizeAdapter(@NonNull ArrayList<String> titleList, @NonNull ArrayList<String> measurementList) {
        this.titleList = titleList;
        this.measurementList = measurementList;
    }

    @Override
    public int getCount() {
        return titleList.size() + measurementList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position < titleList.size() ? TYPE_TITLE : TYPE_VALUE;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            GridSizeMeasurementBinding itemBinding =
                    GridSizeMeasurementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            holder = new ViewHolder(itemBinding);
            holder.view = itemBinding.getRoot();
            holder.view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String text;
        if (getItemViewType(position) == TYPE_TITLE) {
            text = titleList.get(position);
        } else {
            text = measurementList.get(position - titleList.size());
        }
        holder.binding.textView.setText(text);

        if (getItemViewType(position) == TYPE_TITLE) {
            holder.binding.textView.setTextAppearance(parent.getContext(), R.style.Vastra_MediumText);
            holder.binding.textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.text_primary_black));
            holder.binding.addIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.textView.setTextAppearance(parent.getContext(), R.style.Vastra_RegularText);
            holder.binding.textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.text_secondary_black));
            holder.binding.addIcon.setVisibility(TextUtils.isEmpty(text) ? View.VISIBLE : View.INVISIBLE);
        }
        return holder.view;
    }

    private static class ViewHolder {
        private View view;
        private GridSizeMeasurementBinding binding;

        ViewHolder(GridSizeMeasurementBinding binding) {
            this.view = binding.getRoot();
            this.binding = binding;
        }
    }
}
