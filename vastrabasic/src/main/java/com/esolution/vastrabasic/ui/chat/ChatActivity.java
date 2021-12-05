package com.esolution.vastrabasic.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.databinding.ActivityChatBinding;
import com.esolution.vastrabasic.models.Message;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {

    private static final String EXTRA_SENDER = "extra_sender";
    private static final String EXTRA_RECEIVER = "extra_receiver";
    private static final String EXTRA_CHAT_ID = "extra_chat_id";

    public static Intent createIntent(Context context, User sender, User receiver, String chatId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_SENDER, sender);
        intent.putExtra(EXTRA_RECEIVER, receiver);
        intent.putExtra(EXTRA_CHAT_ID, chatId);
        return intent;
    }

    public static String getChatId(int designerUserId, int shopperUserId) {
        return "C" + designerUserId + "-" + shopperUserId;
    }

    private String chatId;

    private User sender;
    private User receiver;

    private ActivityChatBinding binding;
    private ChatAdapter chatAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(receiver.getFirstName() + " " + receiver.getLastName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        databaseReference = FirebaseDatabase.getInstance().getReference("chats").child(chatId);

        fetchMessages();

        chatAdapter = new ChatAdapter(new ArrayList<>(), sender.getUserId());
        binding.messages.setLayoutManager(new LinearLayoutManager(this));
        binding.messages.setAdapter(chatAdapter);

        binding.btnSend.setOnClickListener(v -> {
            String newMessage = validateNewMessage();
            if (!TextUtils.isEmpty(newMessage)) {
                sendMessage(newMessage);
            }
        });
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            chatId = getIntent().getStringExtra(EXTRA_CHAT_ID);
            sender = (User) getIntent().getSerializableExtra(EXTRA_SENDER);
            receiver = (User) getIntent().getSerializableExtra(EXTRA_RECEIVER);
            return !TextUtils.isEmpty(chatId) && sender != null && receiver != null;
        }
        return false;
    }

    private ChildEventListener childEventListener;

    private void fetchMessages() {
//        Query recentMessagesQuery = databaseReference.limitToLast(100);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Message> list = new ArrayList<>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String comment = JsonUtils.toJson(ds.getValue());
//                    Message message = JsonUtils.fromJson(comment, Message.class);
//                    list.add(message);
//                }
//                Log.d("------", "onDataChange: " + JsonUtils.toJson(list));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("----", "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        recentMessagesQuery.addValueEventListener(valueEventListener);

        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                chatAdapter.addMessage(message);
                binding.messages.scrollToPosition(chatAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("----", databaseError.getMessage());
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    private String validateNewMessage() {
        return binding.messageBox.getText().toString().trim();
    }

    private void sendMessage(String newMessage) {
        Message message = new Message(sender.getUserId(), newMessage);
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(message);

        binding.messageBox.setText(null);
        closeKeyboard();
    }

    @Override
    protected void onDestroy() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
        }
        super.onDestroy();
    }
}
