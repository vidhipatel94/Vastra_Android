package com.esolution.vastrashopper.ui.designers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.databinding.RowDesignerBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DesignersAdapter extends RecyclerView.Adapter<DesignersAdapter.ViewHolder> {

    private final List<Designer> designersList;

    public DesignersAdapter(List<Designer> designersList) {
        this.designersList = designersList;
    }

    @NotNull
    @Override
    public DesignersAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowDesignerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull DesignersAdapter.ViewHolder holder, int position) {
        Designer designer = designersList.get(position);

        Context context = holder.binding.getRoot().getContext();

        holder.binding.textViewFDName.setText(designer.getFirstName().concat(" " + designer.getLastName()));
        holder.binding.textViewTagLine.setText(designer.getTagline());
        ImageUtils.loadImageUrl(holder.binding.imageProfilePic, designer.getAvatarURL());

        holder.binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Fashion Designer " + position + " Selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return designersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowDesignerBinding binding;

        public ViewHolder(@NotNull RowDesignerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
