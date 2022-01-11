package com.example.arknews.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class NewsApi {

    private static final String TAG = "NewsApi";
    private static final String NEWS_API = "https://newsapi.org/";
    private static final String API_KEY = "fd680eea1ef2449bb48ff822ba62c7f5";
    private final Context context;

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
                            String categoryName = source.getString("categoryName");
                            String apiProvider = NEWS_API;

                            int categoryId = ARKDatabase.getInstance(context).categoryDao().getCategoryId(categoryName);
                            Channel channel = new Channel(name, apiId, description, url, language, country, categoryId, apiProvider);
                            ARKDatabase.getInstance(context).channelDao().insert(channel);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "getChannels:jsonException", e);
                    }
                }, error -> Log.e(TAG, "getChannels:requestError", error));
        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getCategoryNews(String category) {
//        String SourceUrl = "https://newsapi.org/v2/top-headlines?sources={" + channelName + "}&category=" + category + "&apiKey=" + API_KEY;
        String SourceUrl = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SourceUrl, null, response -> {
                    try {
                        JSONArray parentArray = response.getJSONArray("articles");
                        for (int i = 0; i < parentArray.length(); i++) {

                            JSONObject parentObject = parentArray.getJSONObject(i);
                            JSONObject channel = parentObject.getJSONObject("source");
                            String apiId = channel.getString("id");
                            if (!Constants.channels.contains(apiId))
                                continue;

                            int channelId = ARKDatabase.getInstance(context).channelDao().getChannelId(apiId);
                            int categoryId = ARKDatabase.getInstance(context).categoryDao().getCategoryId(apiId);
                            String title = channel.getString("title");
                            String author = channel.getString("author");
                            Date published = Date.valueOf(channel.getString("publishedAt"));
                            String url = channel.getString("url");
                            String urlToImage = channel.getString("urlToImage");
                            String content = channel.getString("content");

                            News news = new News(channelId, categoryId, title, author, published, url, urlToImage, content);
                            ARKDatabase.getInstance(context).newsDao().insert(news);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "getCategoryNews:jsonException", e);
                    }
                }, error -> Log.e(TAG, "getCategoryNews:requestError", error));
        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getChannelNews(String channelName) {

        String SourceUrl = "https://newsapi.org/v2/everything?sources=" + channelName + "&apiKey=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SourceUrl, null, response -> {
                    try {
                        JSONArray parentArray = response.getJSONArray("articles");
                        for (int i = 0; i < parentArray.length(); i++) {

                            JSONObject parentObject = parentArray.getJSONObject(i);
                            JSONObject channel = parentObject.getJSONObject("source");
                            String apiId = channel.getString("id");
                            if (!Constants.channels.contains(apiId))
                                continue;

                            int channelId = ARKDatabase.getInstance(context).channelDao().getChannelId(apiId);
                            int categoryId = ARKDatabase.getInstance(context).channelDao().getCategoryId(apiId);
                            String title = channel.getString("title");
                            String author = channel.getString("author");
                            if (author == null) author = apiId;
                            Date published = Date.valueOf(channel.getString("publishedAt"));
                            String url = channel.getString("url");
                            String urlToImage = channel.getString("urlToImage");
                            String content = channel.getString("content");

                            News news = new News(channelId, categoryId, title, author, published, url, urlToImage, content);
//                            System.out.println(news);
//                            System.out.println("hisa dsad sad s");
                            ARKDatabase.getInstance(context).newsDao().insert(news);
//                            System.out.println("arewr ew  we");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "getChannelsNews:jsonException", e);
                    }
                }, error -> Log.e(TAG, "getChannelsNews:requestError", error));
        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

}
