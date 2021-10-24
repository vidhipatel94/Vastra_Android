package com.esolution.vastrashopper.ui.designers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesignersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DesignersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is designers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}