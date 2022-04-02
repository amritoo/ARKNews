package com.example.arknews.utility;

import java.util.Arrays;
import java.util.List;

public interface Constants {

    String NewsApi = "https://newsapi.org/";
    List<String> channels = Arrays
            .asList("abc-news", "associated-press", "bbc-news", "bbc-sport",
                    "business-insider", "cbc-news", "cnn", "engadget",
                    "espn", "fox-news", "fox-sports", "google-news",
                    "ign", "independent", "national-geographic", "reuters",
                    "the-washington-times");

    String NEWS_ID = "newsId";

    String THEME = "theme";
    String LIGHT = "light";
    String DARK = "dark";
}
