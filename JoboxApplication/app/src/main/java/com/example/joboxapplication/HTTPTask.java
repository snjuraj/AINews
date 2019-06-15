package com.example.joboxapplication;

import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HTTPTask {
    private static final String baseURL =
            "https://newsapi.org/v2/everything?q=ai";

    private static String getNewsFromDate = "&from=";
    private static String getNewsToDate = "&to=";
    private static final String getSorted = "&sortBy=publishedAt";
    private static final String apiKey = "&apiKey=d1d760ed1c1e4b5189e8b810108ac762";

    public static void setDates(String date){
        getNewsFromDate = "&from=" + date;
        getNewsToDate = "&to=" + date;
    }

    public static List<NewsArticles> getNews() {
        try {
            HttpURLConnection connection = (HttpURLConnection)
                    new URL(baseURL + getNewsFromDate + getNewsToDate + getSorted + apiKey).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer();
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                json.append(buffer).append("\n");
            }
            reader.close();

            Gson gson = new Gson();
            NewsData data1 = gson.fromJson(json.toString() , NewsData.class);
            List<NewsArticles> articles = data1.getArticles();

            return articles;
        } catch (Exception e) {
            return null;
        }
    }
}
