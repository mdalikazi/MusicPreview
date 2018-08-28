package com.alikazi.codetestaim.network;

import android.net.Uri;
import android.util.Log;

import com.alikazi.codetestaim.models.PlayoutItem;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;
import com.alikazi.codetestaim.utils.NetConstants;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class RequestsProcessor {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    public static void getFeedFromApi(final RequestsQueueHelper requestQueueHelper,
                               final FeedRequestListener feedRequestListener) {
        Log.i(LOG_TAG, "getFeedFromApi");
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
                            DLog.i(LOG_TAG, "onResponse: " + response.toString());
//                            ApiResponseModel apiResponseModel = new Gson().fromJson(response.toString(), ApiResponseModel.class);
//                            onSuccess(photosResponse?.feed ?: emptyList())
                            parseXml(response.toString());
                            if (feedRequestListener != null) {
                                feedRequestListener.onSuccess();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            DLog.i(LOG_TAG, "onErrorResponse: " + error.getMessage());
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

    private static void parseXml(String xml) {
        XmlParserCreator parserCreator = new XmlParserCreator() {
            @Override
            public XmlPullParser createParser() {
                try {
                    return XmlPullParserFactory.newInstance().newPullParser();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        GsonXml gsonXml = new GsonXmlBuilder()
                .setXmlParserCreator(parserCreator)
                .create();

//        String xml = "<model><playoutdata><playoutitem>item</playoutitem></<playoutdata>></model>";
        PlayoutItem playoutItem = gsonXml.fromXml(xml, PlayoutItem.class);
        DLog.d(LOG_TAG, "playoutItem.artist: " + playoutItem.artist);
        DLog.d(LOG_TAG, "playoutItem.title: " + playoutItem.title);

//        assertEquals("my name", model.getName());
//        assertEquals("my description", model.getDescription());
    }

    public interface FeedRequestListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
