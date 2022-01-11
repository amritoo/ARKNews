package com.example.arknews.model;

import static androidx.room.ForeignKey.CASCADE;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "history",
        foreignKeys = {
                @ForeignKey(entity = Channel.class,
                        parentColumns = "id",
                        childColumns = "channel_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = News.class,
                        parentColumns = "id",
                        childColumns = "news_id",
                        onDelete = CASCADE)
        }
)
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "channel_id")
    private int channelId;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "published")
    private Date published;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "url_image")
    private String urlToImage;
    @ColumnInfo(name = "news_id")
    private int newsId;

    public History(int id, int channelId, int categoryId, String title, String author, Date published, String url, String urlToImage, int newsId) {
        this.id = id;
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.published = published;
        this.url = url;
        this.urlToImage = urlToImage;
        this.newsId = newsId;
    }

    @Ignore
    public History(int channelId, int categoryId, String title, String author, Date published, String url, String urlToImage, int newsId) {
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.published = published;
        this.url = url;
        this.urlToImage = urlToImage;
        this.newsId = newsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

}
