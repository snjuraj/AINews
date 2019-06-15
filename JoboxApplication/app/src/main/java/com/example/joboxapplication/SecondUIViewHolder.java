package com.example.joboxapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.view.View.GONE;

public class SecondUIViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = SecondUIViewHolder.class.getSimpleName();

    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewSummary;

    public SecondUIViewHolder(Context context, View view) {
        super(view);

        this.imageView = view.findViewById(R.id.secondUI_image);
        this.textViewTitle = view.findViewById(R.id.secondUI_title);
        this.textViewSummary = view.findViewById(R.id.secondUI_summary);

    }

    public void populateData(NewsArticles newsArticles) {
        String url = newsArticles.getUrlToImage();
        if(url != null && !url.isEmpty()){
            Picasso.get().load(url).into(imageView);
        }
        else{
            imageView.setVisibility(GONE);
        }
        textViewTitle.setText(newsArticles.getTitle());
        textViewSummary.setText(newsArticles.getDescription());
    }
}
