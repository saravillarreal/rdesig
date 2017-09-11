package rdesign.android.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import rdesign.android.util.MySingletonUtil;

/**
 * Created by saravillarreal on 9/6/17.
 */

public class UrlHit {
    private static final String LOG_TAG = "UrlFlickr";


    public final static String GET_ELEMENTS_URL = "v1/search_by_date?query=android";
    public final static String FORMAT = "format";
    public final static String API_KEY = "api_key";




    public static JsonArrayRequest jsonObjectRequest = null;

    public static void getElements(Context context, int page, Response
            .Listener<JsonArrayRequest> listener, Response.ErrorListener errorListener) {

        JSONObject mVideoJsonObject = null;
        try {
            mVideoJsonObject = new JSONObject();
            mVideoJsonObject.put(FORMAT, "json");
            mVideoJsonObject.put(API_KEY, UrlBase.API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String photosUrl = UrlBase.createUrl(GET_ELEMENTS_URL);

        Log.i(LOG_TAG, photosUrl);
        Log.i(LOG_TAG, mVideoJsonObject.toString());

        //jsonObjectRequest = new JsonArrayRequest(photosUrl, listener, errorListener);
        jsonObjectRequest.setShouldCache(false);
        MySingletonUtil.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
