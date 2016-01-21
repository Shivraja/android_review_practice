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

public class TamilSlidingFragment extends Fragment {

    static Bitmap popularBitmap[];
    static Bitmap ratedBitmap[];
    static Bitmap recentBitmap[];
    static boolean isBitmapCalculated = false;
    MoviesDataRetriever moviesDataRetriever;
    ViewFlipper tamil_popular, tamil_recent, tamil_rated;
    ImageView popularIndicatorImageView[] = new ImageView[5];
    ImageView ratedIndicatorImageView[] = new ImageView[5];
    ImageView recentIndicatorImageView[] = new ImageView[5];
    LinearLayout tamil_popular_indicator;
    LinearLayout tamil_rated_indicator;
    LinearLayout tamil_recent_indicator;

    static int current_popular_index = 0;
    static int current_rated_index = 0;
    static int current_recent_index = 0;

    public void calculateBitmap() {
        moviesDataRetriever = new MoviesDataRetriever(getContext(), getResources());
        MOVIE[] popularMovies = moviesDataRetriever.getTopPopularMovies("Tamil");
        MOVIE[] ratedMovies = moviesDataRetriever.getTopRatedMovies("Tamil");
        MOVIE[] recentMovies = moviesDataRetriever.getTopRecentMovies("Tamil");
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
        View view = inflater.inflate(R.layout.tamil_sliding_fragment, container, false);
        tamil_popular = (ViewFlipper) view.findViewById(R.id.movie_flipper_tamil_popular);
        tamil_rated = (ViewFlipper) view.findViewById(R.id.movie_flipper_tamil_rated);
        tamil_recent = (ViewFlipper) view.findViewById(R.id.movie_flipper_tamil_recent);
        tamil_popular_indicator = (LinearLayout) view.findViewById(R.id.tamil_popular_indicator);
        tamil_rated_indicator = (LinearLayout) view.findViewById(R.id.tamil_rated_indicator);
        tamil_recent_indicator = (LinearLayout) view.findViewById(R.id.tamil_recent_indicator);

        if (!isBitmapCalculated) {
            calculateBitmap();
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8,5,5,5);

        for (int i = 0; i < 5; i++) {
            popularIndicatorImageView[i] = new ImageView(getContext());
            popularIndicatorImageView[i].setLayoutParams(params);
            tamil_popular_indicator.addView(popularIndicatorImageView[i]);
        }

        for (int i = 0; i < 5; i++) {
            ratedIndicatorImageView[i] = new ImageView(getContext());
            ratedIndicatorImageView[i].setLayoutParams(params);
            tamil_rated_indicator.addView(ratedIndicatorImageView[i]);
        }

        for (int i = 0; i < 5; i++) {
            recentIndicatorImageView[i] = new ImageView(getContext());
            recentIndicatorImageView[i].setLayoutParams(params);
            tamil_recent_indicator.addView(recentIndicatorImageView[i]);
        }

        for (int i = 0; i < 5; i++) {
            popularIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            ratedIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            recentIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
        }

        tamil_popular.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                popularIndicatorImageView[current_popular_index].setImageResource(R.drawable.indicator_off);
                current_popular_index++;
                current_popular_index %= 5;
                popularIndicatorImageView[current_popular_index].setImageResource(R.drawable.indicator_on);
            }
        });

        tamil_rated.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                ratedIndicatorImageView[current_rated_index].setImageResource(R.drawable.indicator_off);
                current_rated_index++;
                current_rated_index %= 5;
                ratedIndicatorImageView[current_rated_index].setImageResource(R.drawable.indicator_on);
            }
        });

        tamil_recent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                recentIndicatorImageView[current_recent_index].setImageResource(R.drawable.indicator_off);
                current_recent_index++;
                current_recent_index %= 5;
                recentIndicatorImageView[current_recent_index].setImageResource(R.drawable.indicator_on);
            }
        });


        tamil_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Popular", CONSTANTS.TAMIL_POPULAR);
            }
        });
        tamil_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Rated", CONSTANTS.TAMIL_RATED);
            }
        });

        tamil_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("Tamil Recent", CONSTANTS.TAMIL_RECENT);
            }
        });


        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(popularBitmap[i]);
            tamil_popular.addView(imageView);
        }

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(ratedBitmap[i]);
            tamil_rated.addView(imageView);
        }

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(recentBitmap[i]);
            tamil_recent.addView(imageView);
        }


        tamil_popular.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_popular.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_rated.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_rated.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_recent.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_recent.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tamil_popular.setAutoStart(true);
        tamil_popular.setFlipInterval(2500);
        tamil_popular.startFlipping();

        tamil_rated.setAutoStart(true);
        tamil_rated.setFlipInterval(2900);
        tamil_rated.startFlipping();

        tamil_recent.setAutoStart(true);
        tamil_recent.setFlipInterval(2700);
        tamil_recent.startFlipping();

        restartIndicators();
    }

    @Override
    public void onPause() {
        super.onPause();
        for (int i = 0; i < 5; i++) {
            popularIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            ratedIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            recentIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
        }
    }

    public void restartIndicators() {
        for (int i = 0; i < 5; i++) {
            popularIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            ratedIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
            recentIndicatorImageView[i].setImageResource(R.drawable.indicator_off);
        }

        popularIndicatorImageView[0].setImageResource(R.drawable.indicator_on);
        ratedIndicatorImageView[0].setImageResource(R.drawable.indicator_on);
        recentIndicatorImageView[0].setImageResource(R.drawable.indicator_on);
        current_popular_index = 0;
        current_rated_index = 0;
        current_recent_index = 0;
    }

    @Override
    public void onStop() {
        super.onPause();
        tamil_popular.stopFlipping();
        tamil_rated.stopFlipping();
        tamil_recent.stopFlipping();
    }

    public void startMovieListActivity(String listName, int type) {
        Intent movieListIntent = new Intent(getContext(), MovieListActivity.class);
        movieListIntent.putExtra("Name", listName);
        movieListIntent.putExtra("Type", type);
        startActivity(movieListIntent);
    }
}