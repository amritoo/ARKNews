package com.example.arknews.client;

import android.content.Context;
import android.util.Log;

import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;
import com.example.arknews.utility.Methods;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;

import java.util.Date;

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
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
        newsApiClient.getSources(
                new SourcesRequest.Builder().build(),
                new NewsApiClient.SourcesCallback() {
                    @Override
                    public void onSuccess(SourcesResponse response) {
                        for (int i = 0; i < response.getSources().size(); i++) {
                            Source source = response.getSources().get(i);
                            String apiId = source.getId();
                            if (!Constants.channels.contains(apiId))
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
                            Log.i(TAG, channel.getApiId());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "getChannels:requestError", throwable);
                    }
                }
        );
    }

    /**
     * Gets all news from the given channel
     *
     * @param channelName channel id to retrieve news from
     */
    public void getChannelNews(String channelName) {
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .sources(channelName)
                        .language("en")
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

                            Date published = Methods.convertDate(article.getPublishedAt());
                            String url = article.getUrl();
                            String urlToImage = article.getUrlToImage();
                            String content = article.getDescription();

                            News news = new News(channelId, categoryId, title, author, published, url, urlToImage, content);
                            ARKDatabase.getInstance(context).newsDao().insert(news);
                        }
                        Log.i(TAG, String.valueOf(response.getArticles().size()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "getChannelsNews:requestError", throwable);
                    }
                }
        );
    }

}
