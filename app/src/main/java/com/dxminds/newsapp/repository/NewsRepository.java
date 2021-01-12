package com.dxminds.newsapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.dxminds.newsapp.model.NetworkAPI;
import com.dxminds.newsapp.model.NetworkAPIService;
import com.dxminds.newsapp.model.NewsAdapter;
import com.dxminds.newsapp.model.NewsArticleBasedOnSource;
import com.dxminds.newsapp.model.NewsDataArticle;
import com.dxminds.newsapp.model.NewsDataResponse;
import com.dxminds.newsapp.model.NewsSourcePosition;
import com.dxminds.newsapp.model.NewsWevURL;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    public static NewsRepository mNewsDataRepository = null;
    private NetworkAPIService mNetworkAPIService;
    private NewsAdapter mNewsAdapter = null;
    private MutableLiveData<NewsArticleBasedOnSource> mMutableLiveDataNewsArticleBasedOnSource = null;
    private MutableLiveData<List<NewsDataArticle>> mMutableLiveDataSortedNewsDataArticle = null;
    private MutableLiveData<NewsSourcePosition> mMutableLiveDataNewsSourcePosition = null;
    private MutableLiveData<NewsWevURL> mMutableLiveDataNewsWevURL = null;

    public NewsRepository() {
        mNetworkAPIService = NetworkAPI.createNetworkAPIService();
    }

    public MutableLiveData<NewsDataResponse> getNewsDataResponse(String q, String date, String sortBy, String apiKey) {
        MutableLiveData<NewsDataResponse> newsDataResponseMutableLiveData = new MutableLiveData<NewsDataResponse>();
        mNetworkAPIService.getNewsSources(q, date, sortBy, apiKey).enqueue(new Callback<NewsDataResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsDataResponse> call, @NotNull Response<NewsDataResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        newsDataResponseMutableLiveData.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<NewsDataResponse> call, @NotNull Throwable t) {
                    newsDataResponseMutableLiveData.setValue(null);
            }
        });

        return newsDataResponseMutableLiveData;
    }

    public static NewsRepository getNewsArticlesRepositoryInstance() {
        if (mNewsDataRepository == null) {
            mNewsDataRepository = new NewsRepository();
        }
        return mNewsDataRepository;
    }

    public NewsAdapter getNewsAdapterInstance() {
        if (mNewsAdapter == null) {
            mNewsAdapter = new NewsAdapter();
        }
        return mNewsAdapter;
    }

    public MutableLiveData<NewsArticleBasedOnSource> getNewsNewsArticleBasedOnSource() {
        if (mMutableLiveDataNewsArticleBasedOnSource == null) {
            mMutableLiveDataNewsArticleBasedOnSource = new MutableLiveData<NewsArticleBasedOnSource>();
        }
        return mMutableLiveDataNewsArticleBasedOnSource;
    }

    public MutableLiveData<List<NewsDataArticle>> getSortedNewsDataArticle() {
        if (mMutableLiveDataSortedNewsDataArticle == null) {
            mMutableLiveDataSortedNewsDataArticle = new MutableLiveData<List<NewsDataArticle>>();
        }
        return mMutableLiveDataSortedNewsDataArticle;
    }

    public MutableLiveData<NewsSourcePosition> getNewsSourcePosition() {
        if (mMutableLiveDataNewsSourcePosition == null) {
            mMutableLiveDataNewsSourcePosition = new MutableLiveData<NewsSourcePosition>();
        }
        return mMutableLiveDataNewsSourcePosition;
    }

    public MutableLiveData<NewsWevURL> getNewsWevURL() {
        if (mMutableLiveDataNewsWevURL == null) {
            mMutableLiveDataNewsWevURL = new MutableLiveData<NewsWevURL>();
        }
        return mMutableLiveDataNewsWevURL;
    }

}
