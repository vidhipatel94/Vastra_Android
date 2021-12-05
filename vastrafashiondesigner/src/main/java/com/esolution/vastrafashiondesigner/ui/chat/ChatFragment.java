package com.esolution.vastrafashiondesigner.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseFragment;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.FragmentChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatFragment extends BaseFragment {

    private FragmentChatBinding binding;

    private DatabaseReference databaseReference;

    private Designer designer;

    private ProgressDialogHandler progressDialogHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("chat-list");
        designer = DesignerLoginPreferences.createInstance(requireContext()).getDesigner();

        progressDialogHandler = new ProgressDialogHandler(getActivity());

        fetchChats();

        return binding.getRoot();
    }

    private void fetchChats() {
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase------", "Error getting data", task.getException());
                } else {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot != null) {
                        List<Integer> list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String chatId = ds.getKey();
                            if (chatId != null && chatId.startsWith("C" + designer.getUserId() + "-")) {
                                String[] split = chatId.split("-");
                                if (split.length >= 2) {
                                    String shopperId = split[1];
                                    try {
                                        int id = Integer.parseInt(shopperId);
                                        list.add(id);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        }
                        getShoppers(list);
                    }
                }
            }
        });
    }

    private void getShoppers(List<Integer> shoppers) {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs()
                .getUsersBasicInfo(DesignerLoginPreferences.createInstance(requireContext()).getSessionToken(), shoppers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            loadChats(response.getData());
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void loadChats(List<User> shoppers) {
        ShoppersAdapter shoppersAdapter = new ShoppersAdapter(designer, shoppers);
        binding.chats.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.chats.setAdapter(shoppersAdapter);
    }
}
