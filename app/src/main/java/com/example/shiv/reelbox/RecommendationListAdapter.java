package com.example.shiv.reelbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shiv on 27-Nov-15.
 */
public class RecommendationListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ImageView backgroundImageView, iconImageView;
    TextView movieNameView, languageView, yearView, recommender;
    RECOMMEND[] recommends;

    RecommendationListAdapter(Context context, RECOMMEND[] recommends) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recommends = recommends;
    }

    @Override
    public int getCount() {
        return recommends.length;
    }

    @Override
    public Object getItem(int i) {
        return recommends[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.recommendation_list_layout, viewGroup, false);

        backgroundImageView = (ImageView) view.findViewById(R.id.recomm_background);
        iconImageView = (ImageView) view.findViewById(R.id.recomm_icon);
        movieNameView = (TextView) view.findViewById(R.id.recomm_movie_name);
        languageView = (TextView) view.findViewById(R.id.recomm_movie_language);
        yearView = (TextView) view.findViewById(R.id.recomm_movie_year);
        recommender = (TextView) view.findViewById(R.id.recomm_name);
        Log.w("RECOMMEND LIST", i+" "+recommends[i].movie.movieId+" "+recommends[i].movie.headImageBitmap+" "+recommends[i].username);

        backgroundImageView.setImageBitmap(recommends[i].movie.headImageBitmap);
        iconImageView.setImageBitmap(recommends[i].movie.iconImageBitmap);
        movieNameView.setText(recommends[i].movie.movieName);
        languageView.setText(recommends[i].movie.language);
        yearView.setText(recommends[i].movie.year + "");
        recommender.setText(recommends[i].username);

        return view;
    }
}
