package com.example.newsapp;

import java.util.prefs.BackingStoreException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static ApiClient sApiClient;
    private static Retrofit sRetrofit;

    private ApiClient(){
        sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiClient getInstance(){
        if (sApiClient == null){
            sApiClient = new ApiClient();
        }
        return sApiClient;
    }


    public ApiInterace getApi(){
        return sRetrofit.create(ApiInterace.class);
    }

}


