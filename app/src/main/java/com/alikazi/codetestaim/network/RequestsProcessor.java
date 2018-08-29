package com.alikazi.codetestaim.network;

import android.net.Uri;
import android.util.Log;

import com.alikazi.codetestaim.models.FeedResponseModel;
import com.alikazi.codetestaim.models.PlayoutItem;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;
import com.alikazi.codetestaim.utils.NetConstants;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class RequestsProcessor {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    public static void getFeedFromApi(final RequestsQueueHelper requestQueueHelper, final FeedRequestListener feedRequestListener) {
        DLog.i(LOG_TAG, "getFeedFromApi");
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(NetConstants.SCHEME_HTTP)
                .authority(NetConstants.URL_AUTHORITY)
                .appendPath(NetConstants.URL_PATH_SERVICES)
                .appendPath(NetConstants.URL_PATH_NOW_PLAYING)
                .appendPath(NetConstants.URL_PATH_NOVA)
                .appendPath(NetConstants.URL_PATH_NOVA_100)
                .appendPath(NetConstants.URL_PATH_ON_AIR);

        try {
            String url = new URL(uriBuilder.build().toString()).toString();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<PlayoutItem> items = parseXml(response);
                            if (feedRequestListener != null) {
                                feedRequestListener.onSuccess(items != null ? items : new ArrayList<PlayoutItem>());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (feedRequestListener != null) {
                                feedRequestListener.onFailure(error != null ? error.getMessage() : "Unknown error!");
                            }
                        }
                    }
            );
            requestQueueHelper.addToRequestQueue(request);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception in makeRequest: " + e.toString());
            if (feedRequestListener != null) {
                feedRequestListener.onFailure("Internal error!");
            }
        }
    }

    private static ArrayList parseXml(String xml) {
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

        GsonXml gsonXml = new GsonXmlBuilder().setXmlParserCreator(parserCreator).create();
        FeedResponseModel feedResponseModel = gsonXml.fromXml(xml, FeedResponseModel.class);
        return feedResponseModel.playoutData;
    }

    public interface FeedRequestListener {
        void onSuccess(ArrayList<PlayoutItem> items);

        void onFailure(String errorMessage);
    }
}
