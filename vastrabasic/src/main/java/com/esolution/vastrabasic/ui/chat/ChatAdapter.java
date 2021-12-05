package com.esolution.vastrabasic.ui.chat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.databinding.ListMessageBinding;
import com.esolution.vastrabasic.models.Message;
import com.esolution.vastrabasic.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private int userId;

    public ChatAdapter(@NotNull List<Message> messages, int userId) {
        this.messages.addAll(messages);
        this.userId = userId;
    }

    public void addMessage(Message message) {
        int size = messages.size();
        this.messages.add(message);
        notifyItemInserted(size);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.binding.spaceLeft.setVisibility(message.getSenderId() == userId ? View.VISIBLE : View.GONE);
        holder.binding.spaceRight.setVisibility(message.getSenderId() == userId ? View.GONE : View.VISIBLE);

        holder.binding.message.setText(message.getMessage());
        holder.binding.message.setGravity(message.getSenderId() == userId ? Gravity.END : Gravity.START);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.message.getLayoutParams();
        layoutParams.gravity = message.getSenderId() == userId ? Gravity.END : Gravity.START;

        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) holder.binding.messageLayout.getLayoutParams();
        layoutParams1.leftMargin = message.getSenderId() == userId ? Utils.getPixels(50) : 0;
        layoutParams1.rightMargin = message.getSenderId() == userId ? 0 : Utils.getPixels(50);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = formatter.format(message.getTimestamp());
        holder.binding.time.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListMessageBinding binding;

        public ViewHolder(@NotNull ListMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
