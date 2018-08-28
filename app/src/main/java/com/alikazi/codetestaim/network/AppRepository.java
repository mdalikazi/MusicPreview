package com.alikazi.codetestaim.network;

import com.alikazi.codetestaim.models.PlayoutItem;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class AppRepository {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    public ApiResponseModel loadFeed(RequestsQueueHelper requestsQueueHelper) {
        DLog.i(LOG_TAG, "loadFeed");
        final ApiResponseModel apiResponseModel = new ApiResponseModel();
        RequestsProcessor.getFeedFromApi(requestsQueueHelper, new RequestsProcessor.FeedRequestListener() {
            @Override
            public void onSuccess() {
                DLog.i(LOG_TAG, "onSuccess");
//                apiResponseModel._feed.postValue();
            }

            @Override
            public void onFailure(String errorMessage) {
                DLog.i(LOG_TAG, "onFailure: " + errorMessage);
                apiResponseModel._networkErrors.postValue(errorMessage);
            }
        });
        return apiResponseModel;
    }

    public class ApiResponseModel {
        public MutableLiveData<ArrayList<PlayoutItem>> _feed;
        public MutableLiveData<String> _networkErrors;
    }

}
