package com.dxminds.newsapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dxminds.newsapp.viewmodel.NewsViewModel;
import com.dxminds.newsapp.model.NewsWevURL;
import com.dxminds.newsapp.R;

public class NewsArticleFragment extends Fragment {

    private WebView mNewsWebView;
    private MutableLiveData<NewsWevURL> mMutableLiveDataNewsWevURL;
    private NewsViewModel mNewsViewModel;

    public NewsArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        mMutableLiveDataNewsWevURL = mNewsViewModel.getNewsWevURL();
        mMutableLiveDataNewsWevURL.observe(this, newsWevURL -> {
            mNewsWebView.loadUrl(newsWevURL.getNewsURL());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.news_article_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNewsWebView = view.findViewById(R.id.wv_news_web_view_news_article_fragment);

        WebSettings webSettings = mNewsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mNewsWebView.setWebViewClient(new WebViewClient());
        mNewsWebView.setOnKeyListener((view1, i, keyEvent) -> {
         if (keyEvent.getAction() == keyEvent.ACTION_DOWN) {
             if (i == KeyEvent.KEYCODE_BACK) {
                 if (mNewsWebView.canGoBack()) {
                     mNewsWebView.goBack();
                 }
             }
         }
            return false;
        });

    }


}