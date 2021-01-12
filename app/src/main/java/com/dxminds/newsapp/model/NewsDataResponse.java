package com.dxminds.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDataResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private String totalResults;
    @SerializedName("articles")
    private List<NewsDataArticle> newsArticles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsDataArticle> getNewsArticles() {
        return newsArticles;
    }

    public void setNewsArticles(List<NewsDataArticle> newsArticles) {
        this.newsArticles = newsArticles;
    }

}
