package com.alikazi.codetestaim.models;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class ApiResponseModel {

    public MutableLiveData<ArrayList<PlayoutItem>> _feed = new MutableLiveData<>();
    public MutableLiveData<String> _networkErrors = new MutableLiveData<>();

}
