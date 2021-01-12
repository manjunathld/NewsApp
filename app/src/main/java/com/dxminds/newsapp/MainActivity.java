  package com.dxminds.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dxminds.newsapp.model.NewsAdapter;
import com.dxminds.newsapp.model.NewsDataArticle;
import com.dxminds.newsapp.model.NewsDataResponse;
import com.dxminds.newsapp.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

  public class MainActivity extends AppCompatActivity {
      private NewsViewModel mNewsViewModel;
      private NewsAdapter mNewsAdapter;
      private LiveData<NewsDataResponse> mLiveDataNewsDataResponse;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        mNewsAdapter = mNewsViewModel.getNewsAdapter();
        mLiveDataNewsDataResponse = mNewsViewModel.getNewsDataResponse();

        if (mLiveDataNewsDataResponse != null) {
            mLiveDataNewsDataResponse.observe(this, newsDataResponse -> {
                List<NewsDataArticle> listOfNewsArticles = newsDataResponse.getNewsArticles();
                //HashMap<String, List<NewsDataArticle>> listNewsArticlesBaseOnSource = getListNewsArticlesBaseOnSource(newsDataResponse);
                mNewsAdapter.setListOfNewsData(listOfNewsArticles, this);
            });
        }

      }

      public HashMap<String, List<NewsDataArticle>> getListNewsArticlesBaseOnSource(NewsDataResponse newsDataResponse) {
          HashMap<String, List<NewsDataArticle>> newsDataHashMap = new HashMap<String, List<NewsDataArticle>>();
          List<NewsDataArticle> listOfNewsArticles = newsDataResponse.getNewsArticles();
          List<NewsDataArticle> listOfNewsArticlesBasedOnSource = new ArrayList<NewsDataArticle>();

          for (int index = 0; index<=listOfNewsArticles.size()-1; index++) {
              String source = listOfNewsArticles.get(index).getNewsDataSource().getName();

              if (source != null && newsDataHashMap.containsKey(source)) {
                  listOfNewsArticlesBasedOnSource = newsDataHashMap.get(source);
                  listOfNewsArticlesBasedOnSource.add(listOfNewsArticles.get(index));
                  newsDataHashMap.put(source, listOfNewsArticlesBasedOnSource);
              } else {
                  newsDataHashMap.put(source, listOfNewsArticlesBasedOnSource);
              }
          }
          return newsDataHashMap;
      }

}