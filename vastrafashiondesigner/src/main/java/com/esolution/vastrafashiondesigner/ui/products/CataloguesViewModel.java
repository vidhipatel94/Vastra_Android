package com.esolution.vastrafashiondesigner.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CataloguesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CataloguesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is catalogues fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}