package com.example.joboxapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<NewsArticles> newsArticlesList;

    public RecyclerViewAdapter(List<NewsArticles> newsArticlesList) {
        this.newsArticlesList = new ArrayList<>(newsArticlesList);
    }

    @Override
    public int getItemViewType(int position) {
        int retVal;
        NewsArticles newsArticles = newsArticlesList.get(position);

        if(newsArticles.getRandomViewType() == null){
            int min = 1;
            int max = 6;

            Random random = new Random();
            retVal = random.nextInt(max - min + 1) + min;
            newsArticles.setRandomViewType(retVal);
        }
        else{
            retVal = newsArticles.getRandomViewType();
        }

        return retVal;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        switch (viewType) {
            case 1:
            case 4: {
                View view = LayoutInflater.from(context).inflate(R.layout.first_ui, parent, false);
                return new FirstUIViewHolder(context, view);
            }

            case 2:
            case 5: {
                View view = LayoutInflater.from(context).inflate(R.layout.second_ui, parent, false);
                return new SecondUIViewHolder(context, view);
            }

            case 3:
            case 6: {
                View view = LayoutInflater.from(context).inflate(R.layout.third_ui, parent, false);
                return new ThirdUIViewHolder(context, view);
            }

            default:
                View view = LayoutInflater.from(context).inflate(R.layout.first_ui, parent, false);
                return new FirstUIViewHolder(context, view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        NewsArticles newsArticles = this.newsArticlesList.get(position);

        if (holder instanceof FirstUIViewHolder) {
            FirstUIViewHolder firstUIViewHolder = (FirstUIViewHolder) holder;
            firstUIViewHolder.populateData(newsArticles);
        } else if (holder instanceof SecondUIViewHolder) {
            SecondUIViewHolder secondUIViewHolder = (SecondUIViewHolder) holder;
            secondUIViewHolder.populateData(newsArticles);
        } else if (holder instanceof ThirdUIViewHolder) {
            ThirdUIViewHolder thirdUIViewHolder = (ThirdUIViewHolder) holder;
            thirdUIViewHolder.populateData(newsArticles);
        }
    }

    @Override
    public int getItemCount() {
        return this.newsArticlesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(NewsArticles newsArticles) {
        int position = newsArticlesList.indexOf(newsArticles);
        newsArticlesList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, NewsArticles newsArticles) {
        this.newsArticlesList.add(position, newsArticles);
        notifyItemInserted(position);
    }

    public void addAllItems(List<NewsArticles> newsArticles){
        final int positionStart = newsArticlesList.size();
        newsArticlesList.addAll(newsArticles);
        notifyItemRangeInserted(positionStart, newsArticles.size());
    }

}
