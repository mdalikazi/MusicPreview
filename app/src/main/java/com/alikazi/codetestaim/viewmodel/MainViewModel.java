package com.alikazi.codetestaim.viewmodel;

import com.alikazi.codetestaim.models.ApiResponseModel;
import com.alikazi.codetestaim.network.AppRepository;
import com.alikazi.codetestaim.network.RequestQueueHelper;
import com.alikazi.codetestaim.utils.AppConstants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private RequestQueueHelper mRequestQueueHelper;
    private AppRepository mAppRepository;


    private MutableLiveData<ApiResponseModel> mResponseLiveData = new MutableLiveData<>();
    private LiveData<ApiResponseModel> mLoadResult = Transformations.map(mResponseLiveData, {mAppRepository.loadPhotos(mRequestQueueHelper)});

    public void getPhotosFromDb() {
        mResponseLiveData.postValue(null);
    }

    public void  clearDatabase() {
        mAppRepository.clearDatabase();
    }

}
