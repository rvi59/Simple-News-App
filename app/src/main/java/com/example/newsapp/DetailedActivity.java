package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DetailedActivity extends AppCompatActivity {

    private WebView mWebView;
    ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mWebView = findViewById(R.id.webView);
        //mProgressBar = findViewById(R.id.detailLoader);
        //mProgressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String URL = intent.getStringExtra("url");

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        //mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(URL);
        if (mWebView.isShown()){
            //mProgressBar.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = getIntent();
        String URL = intent.getStringExtra("url");
        int id = item.getItemId();
        if (id==R.id.share){
            try {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared From News App");
                sendIntent.setType("text/plain");
                String body = ""+URL;
                sendIntent.putExtra(Intent.EXTRA_TEXT,body);
                Intent shareIntent = Intent.createChooser(sendIntent, "Share With: ");
                startActivity(shareIntent);
            }
            catch (Exception e){
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        if (id==android.R.id.home) {
            startActivity(new Intent(DetailedActivity.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}