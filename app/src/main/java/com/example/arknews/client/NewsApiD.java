package com.example.arknews.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class NewsApiD {

    private static final String TAG = "NewsApi";
    private static final String NEWS_API = "https://newsapi.org/";
    private static final String API_KEY = "fd680eea1ef2449bb48ff822ba62c7f5";
    private final Context context;

    /**
     * @param context The application context
     */
    public NewsApiD(Context context) {
        this.context = context;
    }

    /**
     * Gets all channels information from NEWS_API and puts it to database after filtering.
     */
    public void getChannels() {
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
        newsApiClient.getSources(
                new SourcesRequest.Builder().build(),
                new NewsApiClient.SourcesCallback() {
                    @Override
                    public void onSuccess(SourcesResponse response) {
                        for (int i = 0; i < response.getSources().size(); i++) {
                            Source source = response.getSources().get(i);
                            String apiId = source.getId();
                            if (!Constants.channels.contains((apiId)))
                                continue;

                            String name = source.getName();
                            String description = source.getDescription();
                            String url = source.getUrl();
                            String language = source.getLanguage();
                            String country = source.getCountry();
                            String categoryName = source.getCategory();
                            String apiProvider = "https://newsapi.org/";

                            int categoryId = ARKDatabase.getInstance(context).categoryDao().getCategoryId(categoryName);
                            Channel channel = new Channel(name, apiId, description, url, language, country, categoryId, apiProvider);
                            ARKDatabase.getInstance(context).channelDao().insert(channel);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                        Log.e(TAG, "getChannels:requestError", throwable);
                    }
                }
        );
    }

//    public void getCategoryNews(String category) {
////        String SourceUrl = "https://newsapi.org/v2/top-headlines?sources={" + channelName + "}&category=" + category + "&apiKey=" + API_KEY;
//        String SourceUrl = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=" + API_KEY;
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, SourceUrl, null, response -> {
//                    try {
//                        JSONArray parentArray = response.getJSONArray("articles");
//                        for (int i = 0; i < parentArray.length(); i++) {
//
//                            JSONObject parentObject = parentArray.getJSONObject(i);
//                            JSONObject channel = parentObject.getJSONObject("source");
//                            String apiId = channel.getString("id");
//                            if (!Constants.channels.contains(apiId))
//                                continue;
//
//                            int channelId = ARKDatabase.getInstance(context).channelDao().getChannelId(apiId);
//                            int categoryId = ARKDatabase.getInstance(context).categoryDao().getCategoryId(apiId);
//                            String title = channel.getString("title");
//                            String author = channel.getString("author");
//                            Date published = Date.valueOf(channel.getString("publishedAt"));
//                            String url = channel.getString("url");
//                            String urlToImage = channel.getString("urlToImage");
//                            String content = channel.getString("content");
//
//                            News news = new News(channelId, categoryId, title, author, published, url, urlToImage, content);
//                            ARKDatabase.getInstance(context).newsDao().insert(news);
//                        }
//                    } catch (JSONException e) {
//                        Log.e(TAG, "getCategoryNews:jsonException", e);
//                    }
//                }, error -> Log.e(TAG, "getCategoryNews:requestError", error));
//        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
//    }

    public void getChannelNews(String channelName) {
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .sources(channelName)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (int i = 0; i < response.getArticles().size(); i++) {
                            Article article = response.getArticles().get(i);
                            String apiId = article.getSource().getId();
                            if (!Constants.channels.contains(apiId))
                                continue;

                            int channelId = ARKDatabase.getInstance(context).channelDao().getChannelId(apiId);
                            int categoryId = ARKDatabase.getInstance(context).channelDao().getCategoryId(apiId);
                            String title = article.getTitle();
                            String author = article.getAuthor();
                            if (author == null) author = apiId;
//                            Date published = Date.valueOf(article.getPublishedAt());;
                            Date published = new Date();
                            String url = article.getUrl();
                            String urlToImage = article.getUrlToImage();
                            String content = article.getDescription();

                            News news = new News(channelId, categoryId, title, author, published, url, urlToImage, content);
                            System.out.println(news);
                            ARKDatabase.getInstance(context).newsDao().insert(news);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "getChannelsNews:requestError", throwable);
                    }
                }
        );
    }

}
