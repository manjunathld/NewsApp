package com.dxminds.newsapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dxminds.newsapp.model.NewsAdapter;
import com.dxminds.newsapp.model.NewsArticleBasedOnSource;
import com.dxminds.newsapp.model.NewsDataArticle;
import com.dxminds.newsapp.model.NewsDataResponse;
import com.dxminds.newsapp.model.NewsSourcePosition;
import com.dxminds.newsapp.model.NewsWevURL;
import com.dxminds.newsapp.repository.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<NewsDataResponse> mMutableLiveDataNewsResponse;
    private NewsAdapter mNewsAdapter;
    private MutableLiveData<NewsArticleBasedOnSource> mMutableLiveDataNewsArticleBasedOnSource;
    private MutableLiveData<List<NewsDataArticle>> mMutableLiveDataSortedNewsDataArticle;
    private MutableLiveData<NewsSourcePosition> mMutableLiveDataNewsSourcePosition;
    private MutableLiveData<NewsWevURL> mMutableLiveDataNewsWevURL;

    public void getNewsDataAPI(String que, String date, String publishedAt, String apipKey) {
        if (mMutableLiveDataNewsResponse == null) {
            NewsRepository mNewsRepository = NewsRepository.getNewsArticlesRepositoryInstance();
            mMutableLiveDataNewsResponse = mNewsRepository.getNewsDataResponse(que, date, publishedAt, apipKey);
            mNewsAdapter = mNewsRepository.getNewsAdapterInstance();
            mMutableLiveDataNewsArticleBasedOnSource = mNewsRepository.getNewsNewsArticleBasedOnSource();
            mMutableLiveDataSortedNewsDataArticle = mNewsRepository.getSortedNewsDataArticle();
            mMutableLiveDataNewsSourcePosition = mNewsRepository.getNewsSourcePosition();
            mMutableLiveDataNewsWevURL = mNewsRepository.getNewsWevURL();
        }
    }

    public LiveData<NewsDataResponse> getNewsDataResponse() {
        return mMutableLiveDataNewsResponse;
    }

    public NewsAdapter getNewsAdapter() {
        return mNewsAdapter;
    }

    public MutableLiveData<NewsArticleBasedOnSource> getNewsArticleBasedOnSource() {
        return mMutableLiveDataNewsArticleBasedOnSource;
    }

    public MutableLiveData<List<NewsDataArticle>> getSortedNewsDataArticle() {
        return mMutableLiveDataSortedNewsDataArticle;
    }

    public MutableLiveData<NewsSourcePosition> getNewsSourcePosition() {
        return mMutableLiveDataNewsSourcePosition;
    }

    public MutableLiveData<NewsWevURL> getNewsWevURL() {
        return mMutableLiveDataNewsWevURL;
    }

}
