package com.example.newsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsHeadlines {

    //The @SerializedName annotation is needed for Gson to map the JSON keys with our fields.
    // In keeping with Java's camelCase naming convention for class member properties, it is not recommended to use underscores to separate words in a variable.
    // @SerializedName helps translate between the two.
    //The @Expose annotation indicates that this member should be exposed for JSON serialization or deserialization.
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    private List<Articles> articles;

    //Generating Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
