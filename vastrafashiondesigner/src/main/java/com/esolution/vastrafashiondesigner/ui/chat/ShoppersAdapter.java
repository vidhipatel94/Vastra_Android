package com.esolution.vastrafashiondesigner.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.chat.ChatActivity;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.RowShopperBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShoppersAdapter extends RecyclerView.Adapter<ShoppersAdapter.ViewHolder> {

    private final Designer designer;
    private final List<User> shoppers;

    public ShoppersAdapter(Designer designer, List<User> shoppers) {
        this.designer = designer;
        this.shoppers = shoppers;
    }

    @NotNull
    @Override
    public ShoppersAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowShopperBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ShoppersAdapter.ViewHolder holder, int position) {
        User shopper = shoppers.get(position);

        Context context = holder.binding.getRoot().getContext();

        holder.binding.name.setText(shopper.getFirstName().concat(" " + shopper.getLastName()));
        String initials = String.valueOf(shopper.getFirstName().charAt(0)) + shopper.getLastName().charAt(0);
        holder.binding.bgImageProfilePic.setText(initials);
//        ImageUtils.loadImageUrl(holder.binding.imageProfilePic, shopper.getAvatarURL(),
//                ContextCompat.getDrawable(context, R.drawable.designer));

        holder.binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(ChatActivity.createIntent(v.getContext(), designer, shopper,
                        ChatActivity.getChatId(designer.getUserId(), shopper.getUserId())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RowShopperBinding binding;

        public ViewHolder(@NotNull RowShopperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
