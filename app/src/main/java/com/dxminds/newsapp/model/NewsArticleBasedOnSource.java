package com.dxminds.newsapp.model;

import java.util.List;

public class NewsArticleBasedOnSource {
    List<NewsDataArticle> listOfNewsArticles;
    String source;

    public List<NewsDataArticle> getListOfNewsArticles() {
        return listOfNewsArticles;
    }

    public void setListOfNewsArticles(List<NewsDataArticle> listOfNewsArticles) {
        this.listOfNewsArticles = listOfNewsArticles;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
