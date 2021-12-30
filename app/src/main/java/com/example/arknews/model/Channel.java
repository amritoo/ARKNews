package com.example.arknews.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "channel")
public class Channel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "api_id")
    private String apiId;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "api_provider")
    private String apiProvider;

    public Channel(int id, String name, String apiId, String description, String url, String language, String country, String category, String apiProvider) {
        this.id = id;
        this.name = name;
        this.apiId = apiId;
        this.description = description;
        this.url = url;
        this.language = language;
        this.country = country;
        this.category = category;
        this.apiProvider = apiProvider;
    }

    @Ignore
    public Channel(String name, String apiId, String description, String url, String language, String country, String category, String apiProvider) {
        this.name = name;
        this.apiId = apiId;
        this.description = description;
        this.url = url;
        this.language = language;
        this.country = country;
        this.category = category;
        this.apiProvider = apiProvider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(String apiProvider) {
        this.apiProvider = apiProvider;
    }

}
