package com.dxminds.newsapp.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dxminds.newsapp.R;
import com.dxminds.newsapp.viewmodel.NewsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsDataArticle> mListOfNewsArticles;
    private NewsViewModel mNewsViewModel;
    MutableLiveData<NewsArticleBasedOnSource> mMutableLiveDataNewsArticleBasedOnSource;

    public void setListOfNewsData(List<NewsDataArticle> listOfNewsArticles, Activity activity) {
        mListOfNewsArticles = listOfNewsArticles;
        notifyDataSetChanged();
        mNewsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(NewsViewModel.class);
        mMutableLiveDataNewsArticleBasedOnSource = mNewsViewModel.getNewsArticleBasedOnSource();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNewsTitle;
        private final TextView mNewsSource;
        private final ImageView mNewsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsTitle = itemView.findViewById(R.id.tv_news_title);
            mNewsSource = itemView.findViewById(R.id.tv_news_source);
            mNewsImage = itemView.findViewById(R.id.iv_news_image);
        }

        public void bindData(@NotNull NewsDataArticle newsDataArticle) {
            if (newsDataArticle.getNewsDataSource().getName() != null) {
                mNewsSource.setText(newsDataArticle.getNewsDataSource().getName());
            }
            if (newsDataArticle.getTitle() != null) {
                mNewsTitle.setText(newsDataArticle.getTitle());
            }
            if (newsDataArticle.getUrlToImage() != null) {
                Glide.with(itemView.getContext()).load(newsDataArticle.getUrlToImage()).placeholder(R.drawable.default_image).into(mNewsImage);
            }
        }

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_recycler_view, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bindData(mListOfNewsArticles.get(position));

        holder.itemView.setOnClickListener(view -> {
            String source = mListOfNewsArticles.get(position).getNewsDataSource().getName();
            NewsArticleBasedOnSource newsArticleBasedOnSource = new NewsArticleBasedOnSource();

            if (mListOfNewsArticles != null) {
                newsArticleBasedOnSource.setListOfNewsArticles(mListOfNewsArticles);
            }
            if (source != null) {
                newsArticleBasedOnSource.setSource(source);
            } else {
                newsArticleBasedOnSource.setSource("");
            }

            mMutableLiveDataNewsArticleBasedOnSource.setValue(newsArticleBasedOnSource);
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_newsSourceFragment);

        });
    }

    @Override
    public int getItemCount() {
        if (mListOfNewsArticles == null) {
            return  0;
        } else  {
            return mListOfNewsArticles.size();
        }
    }

}