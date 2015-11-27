package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shiv on 16-Nov-15.
 */
public class FavouriteMovieListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    MOVIE[] movies;

    ImageView backgroundImageView, iconImageView;
    TextView movieNameView, languageView, yearView;

    public FavouriteMovieListAdapter(MOVIE[] movies, Resources resources, Context context) {

        this.context = context;
        this.movies = movies;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int i) {
        return movies[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(R.layout.favourite_list_layout, viewGroup, false);
        backgroundImageView = (ImageView) view.findViewById(R.id.favourite_background);
        iconImageView = (ImageView) view.findViewById(R.id.favourite_icon);
        movieNameView = (TextView) view.findViewById(R.id.favourite_movie_name);
        languageView = (TextView) view.findViewById(R.id.favourite_movie_language);
        yearView = (TextView) view.findViewById(R.id.favourite_movie_year);
        backgroundImageView.setImageBitmap(movies[i].headImageBitmap);
        iconImageView.setImageBitmap(movies[i].iconImageBitmap);
        movieNameView.setText(movies[i].movieName);
        languageView.setText(movies[i].language);
        yearView.setText(movies[i].year + "");
        return view;
    }
}