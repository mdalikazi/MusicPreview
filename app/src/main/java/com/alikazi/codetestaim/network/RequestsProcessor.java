package com.alikazi.codetestaim.network;

import android.net.Uri;
import android.util.Log;

import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.NetConstants;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class RequestsProcessor {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private BufferedInputStream mBufferedInputStream;
    private InputStreamReader mInputStreamReader;

    public void getFeedFromApi(final RequestQueueHelper requestQueueHelper,
                               final FeedRequestListener feedRequestListener) {
        Log.i(LOG_TAG, "getFeedFromApi onPreExecute");
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(NetConstants.SCHEME_HTTP)
                .authority(NetConstants.URL_AUTHORITY)
                .appendPath(NetConstants.URL_PATH_SERVICES)
                .appendPath(NetConstants.URL_PATH_NOW_PLAYING)
                .appendPath(NetConstants.URL_PATH_NOVA)
                .appendPath(NetConstants.URL_PATH_NOVA_100)
                .appendPath(NetConstants.URL_PATH_ON_AIR)
                .appendPath(NetConstants.URL_QUERY_XML);

        try {
            String url = new URL(uriBuilder.build().toString()).toString();
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                            val photosResponse: PhotosResponse? = Gson().fromJson(response.toString(), PhotosResponse::class.java)
//                            onSuccess(photosResponse?.photos ?: emptyList())
                            if (feedRequestListener != null) {
                                feedRequestListener.onSuccess();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (feedRequestListener != null) {
                                feedRequestListener.onFailure(error.getMessage());
                            }
//                            onFailure(error.message ?: "Unknown Error");
                        }
                    });
            requestQueueHelper.addToRequestQueue(objectRequest);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception in makeRequest: " + e.toString());
            if (feedRequestListener != null) {
                feedRequestListener.onFailure("Internal error!");
            }
        }

    }

    public interface FeedRequestListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
