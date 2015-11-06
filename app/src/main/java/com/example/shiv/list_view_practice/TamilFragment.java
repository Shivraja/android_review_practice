package com.example.shiv.list_view_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Created by Shiv on 31-Oct-15.
 */
public class TamilFragment extends Fragment {

    ViewFlipper tamil_popular, tamil_recent, tamil_rated, english_popular, english_recent, english_rated;

    int headImages[] = {R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,R.drawable.head_9,R.drawable.head_10,R.drawable.head_abi,R.drawable.head_aranmanai,R.drawable.head_saivam};


    public TamilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tamil_sliding_fragment, container, false);
        tamil_popular = (ViewFlipper)view.findViewById(R.id.movie_flipper_tamil_popular);
        tamil_rated = (ViewFlipper)view.findViewById(R.id.movie_flipper_tamil_rated);
        tamil_recent = (ViewFlipper)view.findViewById(R.id.movie_flipper_tamil_recent);
        tamil_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Popular");
            }
        });
        tamil_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Rated");
            }
        });
        tamil_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Recent");
            }
        });

        int start;
        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_popular.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_rated.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_recent.addView(imageView);
        }

        tamil_popular.setAutoStart(true);
        tamil_popular.setFlipInterval(1700);
        tamil_popular.startFlipping();
        tamil_popular.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_popular.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_rated.setAutoStart(true);
        tamil_rated.setFlipInterval(1800);
        tamil_rated.startFlipping();
        tamil_rated.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_rated.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_recent.setAutoStart(true);
        tamil_recent.setFlipInterval(1900);
        tamil_recent.startFlipping();
        tamil_recent.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_recent.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        tamil_popular.stopFlipping();
        tamil_rated.stopFlipping();
        tamil_recent.stopFlipping();
    }

    public void startMovieListActivity(String listName){
        Intent movieListIntent = new Intent(getContext(),MainActivity.class);
        startActivity(movieListIntent);
    }
}
