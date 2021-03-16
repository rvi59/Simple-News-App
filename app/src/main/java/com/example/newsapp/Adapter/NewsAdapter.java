package com.example.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.DetailedActivity;
import com.example.newsapp.Models.Articles;
import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    Context mContext;
    List<Articles> mArticlesList;

    public NewsAdapter(Context context, List<Articles> articlesList) {
        mContext = context;
        mArticlesList = articlesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_items,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Articles articles = mArticlesList.get(position);
        holder.mTextViewTitle.setText(articles.getTitle());
        holder.mTextViewSource.setText(articles.getSource().getName());
        //holder.mTextViewDate.setText(articles.getPublishedAt());
        holder.mTextViewDate.setText(DateFormat(articles.getPublishedAt()));



        String url = articles.getUrl();

        String imgURL = articles.getUrlToImage();

        Picasso.get().load(imgURL).into(holder.mImageViewBack);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailedActivity.class);
//                intent.putExtra("title",articles.getTitle());
//                intent.putExtra("source",articles.getSource().getName());
//                intent.putExtra("time",articles.getPublishedAt());
//                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mArticlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle, mTextViewSource, mTextViewDate;
        ImageView mImageViewBack;
        CardView mCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.tvTitle);
            mTextViewSource = itemView.findViewById(R.id.tvSource);
            mTextViewDate = itemView.findViewById(R.id.tvDate);
            mImageViewBack = itemView.findViewById(R.id.backImg);
            mCardView = itemView.findViewById(R.id.myCard);
        }
    }


    public String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy",Locale.ENGLISH);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }


    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


}
