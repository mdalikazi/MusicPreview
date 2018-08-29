package com.alikazi.codetestaim.utils;

import android.content.Context;

import com.alikazi.codetestaim.network.AppRepository;
import com.alikazi.codetestaim.network.RequestsQueueHelper;
import com.alikazi.codetestaim.viewmodel.ViewModelFactory;

public class Injector {

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RequestsQueueHelper requestQueueHelper = RequestsQueueHelper.getInstance(context);
        return new ViewModelFactory(requestQueueHelper, provideAppRepository(context));
    }

    public static AppRepository provideAppRepository(Context context) {
//        return AppRepository(provideCache(context));
        return new AppRepository();
    }

//    fun provideCache(context: Context): LocalCache {
//        val database: AppDatabase = AppDatabase.getDatabaseInstance(context)
//        return LocalCache(database.photoDao(), Executors.newSingleThreadExecutor())
//    }
}
