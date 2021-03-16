package com.example.newsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Models.Articles;
import com.example.newsapp.Models.NewsHeadlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    List<Articles> mList = new ArrayList<>();
    final String API_KEY = "76bc9f5077a648948245e65c3209586d";
    private SwipeRefreshLayout mRefreshLayout;
    EditText mEditTextQuery;
    Button mButtonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.swipeRefresh);
        mEditTextQuery = findViewById(R.id.etQuery);
        mButtonSearch = findViewById(R.id.btnSearch);





        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountry();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetriveJson("", country, API_KEY);
            }
        });
        RetriveJson("", country, API_KEY);


        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditTextQuery.getText().toString().equals("")) {
                    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            RetriveJson(mEditTextQuery.getText().toString(),country,API_KEY);
                        }
                    });
                    RetriveJson(mEditTextQuery.getText().toString(),country,API_KEY);
                }
                else {
                    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            RetriveJson("",country,API_KEY);
                        }
                    });

                    RetriveJson("",country,API_KEY);
                }
            }
        });


    }

    public void RetriveJson(String query, String country, String apiKey) {

        mRefreshLayout.setRefreshing(true);
        Call<NewsHeadlines> call;
        if (!mEditTextQuery.getText().toString().equals("")) {
            call = ApiClient.getInstance().getApi().getSpecificData(query, apiKey);
        }
        else {
            call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        }


        call.enqueue(new Callback<NewsHeadlines>() {
            @Override
            public void onResponse(Call<NewsHeadlines> call, Response<NewsHeadlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    mRefreshLayout.setRefreshing(false);
                    mList.clear();
                    mList = response.body().getArticles();
                    mNewsAdapter = new NewsAdapter(MainActivity.this, mList);
                    mRecyclerView.setAdapter(mNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsHeadlines> call, Throwable t) {
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("News App")
                .setMessage("Do you want to Exit")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }
}