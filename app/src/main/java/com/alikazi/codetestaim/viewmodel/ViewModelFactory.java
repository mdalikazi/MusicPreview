package com.alikazi.codetestaim.viewmodel;

import com.alikazi.codetestaim.network.AppRepository;
import com.alikazi.codetestaim.network.RequestsQueueHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Ali on 28/8/18 <br>
 * Factory for ViewModels <br>
 * Why do we need to do it? Read
 * <a href="https://medium.com/@dpreussler/add-the-new-viewmodel-to-your-mvvm-36bfea86b159">this</a>
 *
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private RequestsQueueHelper mRequestQueueHelper;
    private AppRepository mAppRepository;

    public ViewModelFactory(RequestsQueueHelper requestsQueueHelper, AppRepository appRepository) {
        mRequestQueueHelper = requestsQueueHelper;
        mAppRepository = appRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
//            @Suppress("UNCHECKED_CAST")
            return (T) new MainViewModel(mRequestQueueHelper, mAppRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
