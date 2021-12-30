package com.example.arknews.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.example.arknews.utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsApi {

    private static final String TAG = "NewsApi";
    private static final String NEWS_API = "https://newsapi.org/";
    private static final String API_KEY = "fd680eea1ef2449bb48ff822ba62c7f5";
    private Context context;

    /**
     * @param context The application context
     */
    public NewsApi(Context context) {
        this.context = context;
    }

    /**
     * Gets all channels information from NEWS_API and puts it to database after filtering.
     */
    public void getChannels() {
        String SourceUrl = "https://newsapi.org/v2/top-headlines/sources?apiKey=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SourceUrl, null, response -> {
                    try {
                        JSONArray sources = response.getJSONArray("sources");
                        for (int i = 0; i < sources.length(); i++) {
                            JSONObject source = sources.getJSONObject(i);
                            String apiId = source.getString("id");
                            if (!Constants.channels.contains((apiId)))
                                continue;

                            String name = source.getString("name");
                            String description = source.getString("description");
                            String url = source.getString("url");
                            String language = source.getString("language");
                            String country = source.getString("country");
                            String category = source.getString("category");
                            String apiProvider = NEWS_API;

                            Channel channel = new Channel(name, apiId, description, url, language, country, category, apiProvider);
                            ARKDatabase.getInstance(context).channelDao().insert(channel);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "getChannels:jsonError", e);
                    }
                }, error -> Log.e(TAG, "getChannels:requestError", error));
        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
