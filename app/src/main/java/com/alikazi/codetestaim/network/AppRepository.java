package com.alikazi.codetestaim.network;

import com.alikazi.codetestaim.utils.AppConstants;

import androidx.lifecycle.MutableLiveData;

public class AppRepository {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    public ApiResponseModel loadFeed(RequestsQueueHelper requestsQueueHelper) {
        final ApiResponseModel apiResponseModel = new ApiResponseModel();
        RequestsProcessor.getFeedFromApi(requestsQueueHelper, new RequestsProcessor.FeedRequestListener() {
            @Override
            public void onSuccess() {
//                apiResponseModel._feed.postValue();
            }

            @Override
            public void onFailure(String errorMessage) {
                apiResponseModel._networkErrors.postValue(errorMessage);
            }
        });
        return apiResponseModel;
    }

    public class ApiResponseModel {
        public MutableLiveData<String> _feed;
        public MutableLiveData<String> _networkErrors;
    }

}
