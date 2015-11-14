package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    Movie[] movies;

    ImageView backgroundImageView, iconImageView;
    TextView movieNameView, languageView, yearView;

    public MovieListAdapter(Movie[] movies, Resources resources, Context context) {

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
        return movies.length - 1;
    }

    @Override
    public Object getItem(int i) {
        return movies[i + 1];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(R.layout.movie_list_layout, viewGroup, false);

        backgroundImageView = (ImageView) view.findViewById(R.id.background);
        iconImageView = (ImageView) view.findViewById(R.id.icon);
        movieNameView = (TextView) view.findViewById(R.id.movie_name);
        languageView = (TextView) view.findViewById(R.id.movie_language);
        yearView = (TextView) view.findViewById(R.id.movie_year);
        i++;
        backgroundImageView.setImageBitmap(movies[i].headImageBitmap);
        iconImageView.setImageBitmap(movies[i].iconImageBitmap);
        movieNameView.setText(movies[i].movieName);
        languageView.setText(movies[i].language);
        yearView.setText(movies[i].year + "");
        return view;
    }
}