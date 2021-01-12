package com.dxminds.newsapp.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPIService {

    @GET("v2/everything")
    public Call<NewsDataResponse> getNewsSources(@Query("q") String q, @Query("from") String date, @Query("sortBy") String sortBy,
                                             @Query("apiKey") String apiKey);

    @GET("v2/everything?q=bitcoin&from=2020-12-11&sortBy=publishedAt&apiKey=0730609125ab4032a2001f3eb96a7fb1")
    public Call<ResponseBody> test();

    @GET("books/v1/volumes?")
    public Call<ResponseBody> test2(@Query("q") String q, @Query("inauthor") String row);
}
