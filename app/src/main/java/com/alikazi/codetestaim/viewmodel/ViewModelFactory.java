package com.alikazi.codetestaim.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Ali on 28/8/18
 * Factory for ViewModels
 * Why do we need to do this? Read [this] (https://medium.com/@dpreussler/add-the-new-viewmodel-to-your-mvvm-36bfea86b159)
 *
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private RequestQueueHelper mRequestQueueHelper;
    private AppRepository mAppRepository;

    private static ViewModelFactory mInstance;

    public static ViewModelFactory getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ViewModelFactory(context);
        }

        return mInstance;
    }

    private ViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
//            @Suppress("UNCHECKED_CAST")
            return new MainViewModel(requestQueueHelper, repository) as T
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
