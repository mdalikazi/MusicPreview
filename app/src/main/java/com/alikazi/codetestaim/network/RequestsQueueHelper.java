package com.alikazi.codetestaim.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.alikazi.codetestaim.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestsQueueHelper {

    private Context mContext;
    private RequestQueue mRequestQueue;
    private static RequestsQueueHelper mInstance;

    public RequestsQueueHelper(Context context) {
        mContext = context;
    }

    public static RequestsQueueHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestsQueueHelper(context);
        }

        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public void addToRequestQueue(Request request) {
        if (isNetworkConnected()) {
            getRequestQueue().add(request);
        } else {
            Toast.makeText(mContext.getApplicationContext(), mContext.getString(R.string.network_error_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    public void cancelAllRequests() {
        getRequestQueue().cancelAll(mContext.getApplicationContext());
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
