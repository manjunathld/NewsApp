package com.dxminds.newsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dxminds.newsapp.model.Constants;
import com.dxminds.newsapp.model.NewsArticleBasedOnSource;
import com.dxminds.newsapp.model.NewsDataArticle;
import com.dxminds.newsapp.model.NewsSourcePosition;
import com.dxminds.newsapp.viewmodel.NewsViewModel;
import com.dxminds.newsapp.model.NewsWevURL;
import com.dxminds.newsapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsSourceFragment extends Fragment {

    private NewsViewModel mNewsViewModel;
    private MutableLiveData<NewsArticleBasedOnSource> mMutableLiveDataNewsArticleBasedOnSource;
    private MutableLiveData<List<NewsDataArticle>> mMutableLiveDataSortedNewsDataArticle;
    private MutableLiveData<NewsWevURL> mMutableLiveDataNewsWevURL;
    //private MutableLiveData<NewsSourcePosition> mMutableLiveDataNewsSourcePosition;
    private List<NewsDataArticle> mSortedNewsArticles;
    private TextView mNewsSource;
    private TextView mNewsTitle;
    private TextView mNewPublishedAt;
    private TextView mNewsDescription;
    private TextView mNewsContent;
    private ConstraintLayout mNewsWebLink;
    private ImageView mNewsImage;
    private Button mNext;
    private Button mPrev;
    private int mCounter;
    private NavController mNavController;
    private String nNewsWebURL = "";

    public NewsSourceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        mMutableLiveDataNewsArticleBasedOnSource = mNewsViewModel.getNewsArticleBasedOnSource();
        mMutableLiveDataSortedNewsDataArticle = mNewsViewModel.getSortedNewsDataArticle();
        mMutableLiveDataNewsWevURL = mNewsViewModel.getNewsWevURL();
        //mMutableLiveDataNewsSourcePosition = mNewsViewModel.getNewsSourcePosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.news_source_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNewsSource = view.findViewById(R.id.tv_news_source_news_source_fragment);
        mNewsTitle = view.findViewById(R.id.tv_news_title_news_source_fragment);
        mNewPublishedAt = view.findViewById(R.id.tv_publishedAt_news_source_fragment);
        mNewsDescription = view.findViewById(R.id.tv_description_text_news_article_fragment);
        mNewsContent = view.findViewById(R.id.tv_content_text_news_article_fargment);
        mNewsWebLink = view.findViewById(R.id.cl_news_source_fragment);
        mNewsImage = view.findViewById(R.id.iv_news_image_news_source_fragment);
        mNext = view.findViewById(R.id.btn_next_news_source_fragment);
        mPrev = view.findViewById(R.id.btn_prev_news_source_fragment);
        mNavController = Navigation.findNavController(view);

        mNext.setOnClickListener(mOnCLickListener);
        mPrev.setOnClickListener(mOnCLickListener);
        mNewsWebLink.setOnClickListener(mOnCLickListener);

        mMutableLiveDataNewsArticleBasedOnSource.observe(requireActivity(), newsArticleBasedOnSource -> {
            String source = newsArticleBasedOnSource.getSource();
            List<NewsDataArticle> listOfNewsArticle = newsArticleBasedOnSource.getListOfNewsArticles();
            HashMap<String, List<NewsDataArticle>> listNewsArticlesBaseOnSource = getListOfNewsArticlesBaseOnSource(listOfNewsArticle);
            mSortedNewsArticles = listNewsArticlesBaseOnSource.get(source);
            //setNewsSourceDataToView(mSortedNewsArticles, 0);
            //mMutableLiveDataSortedNewsDataArticle.setValue(listNewsArticlesBaseOnSource.get(source));
            //setMutableLiveDataSortedNewsDataArticle(listNewsArticlesBaseOnSource, source);
        });

        setNewsSourceDataToView(mSortedNewsArticles, 0);
       /* mMutableLiveDataSortedNewsDataArticle.observe(requireActivity(), newsDataArticleList -> {
            mSortedNewsArticles = newsDataArticleList;
            setNewsSourceDataToView(mSortedNewsArticles, mCounter);
        });*/

        /*mMutableLiveDataNewsSourcePosition.observe(requireActivity(), newsSourcePosition -> {
            setNewsSourceDataToView(mSortedNewsArticles, newsSourcePosition.getPosition());
        });*/

    }

    private final View.OnClickListener mOnCLickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            NewsSourcePosition newsSourcePosition = new NewsSourcePosition();
            switch(view.getId()) {
                case R.id.btn_next_news_source_fragment:
                    if (mCounter < mSortedNewsArticles.size()-1) {
                        mCounter++;
                        setNewsSourceDataToView(mSortedNewsArticles, mCounter);
                        /*newsSourcePosition.setPosition(mCounter);
                        mMutableLiveDataNewsSourcePosition.setValue(newsSourcePosition);*/
                    }
                    break;
                case R.id.btn_prev_news_source_fragment:
                    if (mCounter-1 < mSortedNewsArticles.size()-1 && mCounter > 0) {
                        mCounter--;
                        setNewsSourceDataToView(mSortedNewsArticles, mCounter);
                       /* newsSourcePosition.setPosition(mCounter);
                        mMutableLiveDataNewsSourcePosition.setValue(newsSourcePosition);*/
                    }
                    break;
                case R.id.cl_news_source_fragment:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.WEB_VIEW_URL, nNewsWebURL);
                        NewsWevURL newsWevURL = new NewsWevURL();
                        newsWevURL.setNewsURL(nNewsWebURL);
                        mMutableLiveDataNewsWevURL.setValue(newsWevURL);
                        mNavController.navigate(R.id.action_newsSourceFragment_to_newsArticleFragment);
                default:
                    break;
            }
        }
    };

    /*private void setMutableLiveDataSortedNewsDataArticle(HashMap<String, List<NewsDataArticle>> listNewsArticlesBaseOnSource, String source){
        mMutableLiveDataSortedNewsDataArticle.setValue(listNewsArticlesBaseOnSource.get(source));
    }*/

    private void setNewsSourceDataToView(List<NewsDataArticle> sortedNewsArticles, int counter) {
        if (sortedNewsArticles.size() >= 1) {
            String newsSource = sortedNewsArticles.get(counter).getNewsDataSource().getName();
            String newsTitle = sortedNewsArticles.get(counter).getTitle();
            String newsPublishedAt = sortedNewsArticles.get(counter).getPublishedAt();
            String newsDescription = sortedNewsArticles.get(counter).getDescription();
            String newsContent = sortedNewsArticles.get(counter).getContent();
            nNewsWebURL = sortedNewsArticles.get(counter).getUrl();
            String newsImage = sortedNewsArticles.get(counter).getUrlToImage();

            if (newsImage != null) {
                Glide.with(this).load(newsImage).placeholder(R.drawable.default_image).into(mNewsImage);
            }
            if (newsSource != null) {
                mNewsSource.setText(newsSource);
            }
            if (newsTitle != null) {
                mNewsTitle.setText(newsTitle);
            }
            if (newsPublishedAt != null) {
                mNewPublishedAt.setText(newsPublishedAt);
            }
            if (newsDescription != null) {
                mNewsDescription.setText(newsDescription);
            }
            if (newsContent != null) {
                mNewsContent.setText(newsContent);
            }

        } else {

        }
    }

    public HashMap<String, List<NewsDataArticle>> getListOfNewsArticlesBaseOnSource(List<NewsDataArticle> newsDataArticleList) {
        HashMap<String, List<NewsDataArticle>> newsDataHashMap = new HashMap<String, List<NewsDataArticle>>();
        List<NewsDataArticle> oldData = new ArrayList<NewsDataArticle>();

        for (int index = 0; index<=newsDataArticleList.size()-1; index++) {
            String source = newsDataArticleList.get(index).getNewsDataSource().getName();

            if (source != null && newsDataHashMap.containsKey(source)) {
                oldData = newsDataHashMap.get(source);
                if (oldData != null) {
                    oldData.add(newsDataArticleList.get(index));
                }
                newsDataHashMap.put(source, oldData);
            } else {
                List<NewsDataArticle> newData = new ArrayList<NewsDataArticle>();
                newData.add(newsDataArticleList.get(index));
                newsDataHashMap.put(source,  newData);
            }
        }
        return newsDataHashMap;
    }

}