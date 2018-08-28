package com.alikazi.codetestaim.viewmodel;

import com.alikazi.codetestaim.network.AppRepository;
import com.alikazi.codetestaim.network.RequestsQueueHelper;
import com.alikazi.codetestaim.utils.AppConstants;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private AppRepository mAppRepository;
    private RequestsQueueHelper mRequestQueueHelper;

    public MainViewModel(AppRepository appRepository, RequestsQueueHelper requestsQueueHelper) {
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

    public LiveData<ArrayList<Model>> mFeed = Transformations.switchMap(mFeedResult,
            new Function<AppRepository.ApiResponseModel, LiveData<ArrayList<Model>>>() {
                @Override
                public LiveData<ArrayList<Model>> apply(AppRepository.ApiResponseModel input) {
                    return input._feed;
                }
            });

    public LiveData<String> networkErrors = Transformations.switchMap(mFeedResult,
            new Function<AppRepository.ApiResponseModel, LiveData<String>>() {
                @Override
                public LiveData<String> apply(AppRepository.ApiResponseModel input) {
                    return input._networkErrors;
                }
            });

    public void getPhotosFromDb() {
        mResponseLiveData.postValue(null);
    }

    public void  clearDatabase() {
//        mAppRepository.clearDatabase();
    }

}
