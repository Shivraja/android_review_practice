package com.example.shiv.list_view_practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    String[] movieNames;
    int[] iconImage, headImage;
    double[] rating;
    int[] year;
    String[] language;

    ImageView backgroundImageView, iconImageView;
    TextView movieNameView,languageView, yearView;
    public CustomAdapter(String[] movieNames,int[] iconImage, int[] headImage, double[] rating, int[] year, String[] language, Context context){
        this.movieNames = movieNames;
        this.iconImage = iconImage;
        this.headImage = headImage;
        this.rating = rating;
        this.year = year;
        this.language = language;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return movieNames.length-1;
    }

    @Override
    public Object getItem(int i) {
        return movieNames[i+1];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = layoutInflater.inflate(R.layout.list_view, viewGroup, false);

        backgroundImageView = (ImageView)view.findViewById(R.id.background);
        iconImageView =(ImageView)view.findViewById(R.id.icon);
        movieNameView = (TextView)view.findViewById(R.id.movie_name);
        languageView = (TextView)view.findViewById(R.id.movie_language);
        yearView = (TextView)view.findViewById(R.id.movie_year);
        i++;
        backgroundImageView.setImageResource(headImage[i]);
        iconImageView.setImageResource(iconImage[i]);
        movieNameView.setText(movieNames[i]);
        languageView.setText(language[i]);
        yearView.setText(year[i]+"");
        return view;
    }
}