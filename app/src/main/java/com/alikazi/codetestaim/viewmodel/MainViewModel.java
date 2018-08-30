package com.alikazi.codetestaim.viewmodel;

import com.alikazi.codetestaim.models.ApiRequestModel;
import com.alikazi.codetestaim.models.ApiResponseModel;
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

    private MutableLiveData<ApiRequestModel> mResponseLiveData = new MutableLiveData<>();
    private LiveData<ApiResponseModel> mFeedResult = Transformations.map(mResponseLiveData,
            new Function<ApiRequestModel, ApiResponseModel>() {
                @Override
                public ApiResponseModel apply(ApiRequestModel input) {
                    return mAppRepository.loadFeed(mRequestQueueHelper);
                }
            });

    public LiveData<ArrayList<PlayoutItem>> mFeed = Transformations.switchMap(mFeedResult,
            new Function<ApiResponseModel, LiveData<ArrayList<PlayoutItem>>>() {
                @Override
                public LiveData<ArrayList<PlayoutItem>> apply(ApiResponseModel input) {
                    return input._feed;
                }
            });

    public LiveData<String> mNetworkErrors = Transformations.switchMap(mFeedResult,
            new Function<ApiResponseModel, LiveData<String>>() {
                @Override
                public LiveData<String> apply(ApiResponseModel input) {
                    return input._networkErrors;
                }
            });

    public void loadFeed() {
        DLog.i(LOG_TAG, "loadFeed");
        mResponseLiveData.postValue(new ApiRequestModel());
    }
}
