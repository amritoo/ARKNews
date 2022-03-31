package com.example.arknews.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "channel",
        foreignKeys = {@ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = CASCADE)},
        indices = {@Index(value = "category_id")}
)
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
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "api_provider")
    private String apiProvider;
    @ColumnInfo(name = "is_selected")
    private boolean selected;

    public Channel(int id, String name, String apiId, String description, String url, String language, String country, int categoryId, String apiProvider) {
        this.id = id;
        this.name = name;
        this.apiId = apiId;
        this.description = description;
        this.url = url;
        this.language = language;
        this.country = country;
        this.categoryId = categoryId;
        this.apiProvider = apiProvider;
    }

    @Ignore
    public Channel(String name, String apiId, String description, String url, String language, String country, int categoryId, String apiProvider) {
        this.name = name;
        this.apiId = apiId;
        this.description = description;
        this.url = url;
        this.language = language;
        this.country = country;
        this.categoryId = categoryId;
        this.apiProvider = apiProvider;
        this.selected = false;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(String apiProvider) {
        this.apiProvider = apiProvider;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
