package com.example.shiv.reelbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class EnglishSlidingFragment extends Fragment {

    static Bitmap popularBitmap[];
    static Bitmap ratedBitmap[];
    static Bitmap recentBitmap[];
    static boolean isBitmapCalculated = false;
    MoviesDataRetriever moviesDataRetriever;
    ViewFlipper english_popular, english_recent, english_rated;
    ImageView indicatorImageView[] = new ImageView[5];
    LinearLayout english_popular_indicator;

    public void calculateBitmap() {
        moviesDataRetriever = new MoviesDataRetriever(getContext(), getResources());

        MOVIE[] popularMovies = moviesDataRetriever.getTopPopularMovies("English");
        MOVIE[] ratedMovies = moviesDataRetriever.getTopRatedMovies("English");
        MOVIE[] recentMovies = moviesDataRetriever.getTopRecentMovies("English");

        popularBitmap = new Bitmap[popularMovies.length];

        for (int i = 0; i < popularMovies.length; i++) {
            popularBitmap[i] = popularMovies[i].headImageBitmap;
        }
        ratedBitmap = new Bitmap[ratedMovies.length];
        for (int i = 0; i < ratedMovies.length; i++) {
            ratedBitmap[i] = ratedMovies[i].headImageBitmap;
        }
        recentBitmap = new Bitmap[recentMovies.length];
        for (int i = 0; i < recentMovies.length; i++) {
            recentBitmap[i] = recentMovies[i].headImageBitmap;
        }
        isBitmapCalculated = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.english_sliding_fragment, container, false);
        english_popular = (ViewFlipper) view.findViewById(R.id.movie_flipper_english_popular);
        english_rated = (ViewFlipper) view.findViewById(R.id.movie_flipper_english_rated);
        english_recent = (ViewFlipper) view.findViewById(R.id.movie_flipper_english_recent);

        if (!isBitmapCalculated) {
            calculateBitmap();
        }

        english_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Popular", CONSTANTS.ENGLISH_POPULAR);
            }
        });

        english_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Rated", CONSTANTS.ENGLISH_RATED);
            }
        });

        english_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Recent", CONSTANTS.ENGLISH_RECENT);
            }
        });

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(popularBitmap[i]);
            english_popular.addView(imageView);
        }

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(ratedBitmap[i]);
            english_rated.addView(imageView);
        }

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(recentBitmap[i]);
            english_recent.addView(imageView);
        }

        english_popular.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        english_popular.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        english_rated.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        english_rated.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        english_recent.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        english_recent.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        english_popular.setAutoStart(true);
        english_popular.setFlipInterval(2500);
        english_popular.startFlipping();

        english_rated.setAutoStart(true);
        english_rated.setFlipInterval(2800);
        english_rated.startFlipping();

        english_recent.setAutoStart(true);
        english_recent.setFlipInterval(3000);
        english_recent.startFlipping();
    }

    @Override
    public void onStop() {
        super.onPause();
        english_popular.stopFlipping();
        english_rated.stopFlipping();
        english_recent.stopFlipping();
    }

    public void startMovieListActivity(String listName, int type) {
        Intent movieListIntent = new Intent(getContext(), MovieListActivity.class);
        movieListIntent.putExtra("Name", listName);
        movieListIntent.putExtra("Type", type);
        startActivity(movieListIntent);
    }
}
