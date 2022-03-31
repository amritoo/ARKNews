package com.example.arknews.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "news",
        foreignKeys = {
                @ForeignKey(entity = Channel.class,
                        parentColumns = "id",
                        childColumns = "channel_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = CASCADE)
        },
        indices = {
                @Index(value = "url", unique = true),
                @Index(value = "channel_id"),
                @Index(value = "category_id")
        }
)
public class News {

    @PrimaryKey(autoGenerate = true)
    int id;

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
    @ColumnInfo(name = "updated")
    private Date updated;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "url_image")
    private String urlToImage;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "pinned")
    private Boolean pinned;

    @Ignore
    private String urlToChannelImage;

    public News(int id, int channelId, int categoryId, String title, String author, Date published, Date updated, String url, String urlToImage, String content, Boolean pinned) {
        this.id = id;
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.published = published;
        this.updated = updated;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
        this.pinned = pinned;
    }

    @Ignore
    public News(int channelId, int categoryId, String title, String author, Date published, Date updated, String url, String urlToImage, String content, Boolean pinned) {
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.published = published;
        this.updated = updated;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
        this.pinned = pinned;
    }

    @Ignore
    public News(int channelId, int categoryId, String title, String author, Date published, String url, String urlToImage, String content) {
        this(channelId, categoryId, title, author, published, null, url, urlToImage, content, false);
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public String getUrlToChannelImage() {
        return urlToChannelImage;
    }

    public void setUrlToChannelImage(String urlToChannelImage) {
        this.urlToChannelImage = urlToChannelImage;
    }

}
