package com.example.joboxapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;

public class FirstUIViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = FirstUIViewHolder.class.getSimpleName();

    private ImageView imageView;
    private TextView timestamp;
    private TextView textViewTitle;
    private TextView textViewSummary;

    public FirstUIViewHolder(Context context, View view) {
        super(view);

        this.imageView = view.findViewById(R.id.firstUI_image);
        this.timestamp = view.findViewById(R.id.firstUI_timestamp);
        this.textViewTitle = view.findViewById(R.id.firstUI_title);
        this.textViewSummary = view.findViewById(R.id.firstUI_summary);

    }

    public void populateData(NewsArticles newsArticles) {
        String url = newsArticles.getUrlToImage();
        if(url != null && !url.isEmpty()){
            Picasso.get().load(url).into(imageView);
        }
        else{
            imageView.setVisibility(GONE);
        }
        String publishedAt = newsArticles.getPublishedAt();
        String dateTime = publishedAt.replaceAll("[TZ]", " ");

        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        try {
            Date date = sdfSource.parse(dateTime);
            String dateString = new SimpleDateFormat("MMM dd, yyyy hh:mm aa").format(date);
            timestamp.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            timestamp.setVisibility(GONE);
        }

        textViewTitle.setText(newsArticles.getTitle());
        textViewSummary.setText(newsArticles.getDescription());
    }
}
