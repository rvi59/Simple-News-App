package com.example.newsapp;

import com.example.newsapp.Models.NewsHeadlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterace {



    @GET("top-headlines")
    Call<NewsHeadlines> getHeadlines(
            @Query("country") String country,
            @Query("apikey") String apikey
    );



    @GET("everything")
    Call<NewsHeadlines> getSpecificData(
            @Query("q") String query,
            @Query("apikey") String apikey
    );


}
