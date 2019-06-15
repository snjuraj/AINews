package com.example.joboxapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.apache.commons.lang3.time.DateUtils;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    volatile int finishedGet = 0;
    volatile int finished = 0;
    private List<NewsArticles> newsArticles = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;
    private Date currentDate = new Date();
    private Date tempDate = new Date();
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<NewsArticles> list;
    private SharedPreferences.Editor prefsEditor;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout = findViewById(R.id.news_swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        init();
    }


    public void init() {

        if(!isInternetEnabled(this)){
            String jsonString = getPreferences(MODE_PRIVATE).getString("NewsArticles", null);
            Type type = new TypeToken<List<NewsArticles>>() {}.getType();
            newsArticles = new Gson().fromJson(jsonString, type);
            if(newsArticles != null && !newsArticles.isEmpty()) {
                populateRecyclerView(newsArticles);
            }
        }

        else {
            String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

            Thread getNews = new Thread(() -> {
                HTTPTask.setDates(todayDate);
                newsArticles = HTTPTask.getNews();
                finishedGet = 1;
            });
            getNews.start();
            while (finishedGet == 0) {
            }
            if(finishedGet == 1) {
                prefsEditor = getPreferences(MODE_PRIVATE).edit();
                String jsonString = new Gson().toJson(newsArticles);
                prefsEditor.putString("NewsArticles", jsonString);
                prefsEditor.apply();
                if(newsArticles != null && !newsArticles.isEmpty()) {
                    populateRecyclerView(newsArticles);
                }
            }
            finishedGet = 0;
        }
    }

    public static boolean isInternetEnabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void populateRecyclerView(List<NewsArticles> newsArticlesList){
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi();
            }
        };
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerViewAdapter = new RecyclerViewAdapter(newsArticlesList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void loadNextDataFromApi() {

        if(isInternetEnabled(this)) {

            Thread getNews = new Thread(() -> {
                Date dayBefore = DateUtils.addDays(tempDate, -1);
                HTTPTask.setDates(new SimpleDateFormat("yyyy-MM-dd").format(dayBefore));
                tempDate = dayBefore;
                list = HTTPTask.getNews();
                finished = 1;
            });
            getNews.start();
            while (finished == 0) {
            }
            if (finished == 1) {
                MainActivity.this.runOnUiThread(() -> {
                    if (!list.isEmpty()) {
                        recyclerViewAdapter.addAllItems(list);
                    }
                });
                finished = 0;
            }
        }
        else{
            Toast.makeText(this, "Please turn your internet on to load more", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        newsArticles.clear();
        tempDate = currentDate;

        init();
    }
}
