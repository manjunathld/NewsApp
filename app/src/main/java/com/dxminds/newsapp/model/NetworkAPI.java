package com.dxminds.newsapp.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAPI {

    private static NetworkAPIService mNetworkAPIService = null;
    private static Retrofit mRetrofit = null;

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://newsapi.org/")
                    //.baseUrl("https://www.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    public static NetworkAPIService createNetworkAPIService() {
        if (mNetworkAPIService == null) {
            mNetworkAPIService = getRetrofit().create(NetworkAPIService.class);
        }
        return mNetworkAPIService;
    }

}

// http://newsapi.org/v2/everything?&q=bitcoin&from=2020-12-11&sortBy=publishedAt&apiKey=0730609125ab4032a2001f3eb96a7fb1
// http://newsapi.org/v2/everything?q=bitcoin&from=2020-12-11&sortBy=publishedAt&apiKey=0730609125ab4032a2001f3eb96a7fb1
