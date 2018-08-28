package com.alikazi.codetestaim.viewmodel;

import com.alikazi.codetestaim.models.PlayoutItem;
import com.alikazi.codetestaim.network.AppRepository;
import com.alikazi.codetestaim.network.RequestsQueueHelper;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private AppRepository mAppRepository;
    private RequestsQueueHelper mRequestQueueHelper;

    public MainViewModel(@NonNull RequestsQueueHelper requestsQueueHelper, @NonNull AppRepository appRepository) {
        mAppRepository = appRepository;
        mRequestQueueHelper = requestsQueueHelper;
    }

    private MutableLiveData<AppRepository.ApiResponseModel> mResponseLiveData = new MutableLiveData<>();
    private LiveData<AppRepository.ApiResponseModel> mFeedResult = Transformations.map(mResponseLiveData,
            new Function<AppRepository.ApiResponseModel, AppRepository.ApiResponseModel>() {
                @Override
                public AppRepository.ApiResponseModel apply(AppRepository.ApiResponseModel input) {
                    return mAppRepository.loadFeed(mRequestQueueHelper);
                }
            });

    public LiveData<ArrayList<PlayoutItem>> mFeed = Transformations.switchMap(mFeedResult,
            new Function<AppRepository.ApiResponseModel, LiveData<ArrayList<PlayoutItem>>>() {
                @Override
                public LiveData<ArrayList<PlayoutItem>> apply(AppRepository.ApiResponseModel input) {
                    return input._feed;
                }
            });

    public LiveData<String> mNetworkErrors = Transformations.switchMap(mFeedResult,
            new Function<AppRepository.ApiResponseModel, LiveData<String>>() {
                @Override
                public LiveData<String> apply(AppRepository.ApiResponseModel input) {
                    return input._networkErrors;
                }
            });

    public void loadFeed() {
        DLog.i(LOG_TAG, "loadFeed");
        mResponseLiveData.postValue(null);
    }

    public void  clearDatabase() {
//        mAppRepository.clearDatabase();
    }

}
