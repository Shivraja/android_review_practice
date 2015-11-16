package com.example.shiv.reelbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * Created by Shiv on 31-Oct-15.
 */
public class TamilSlidingFragment extends Fragment {

    MoviesDataRetriever moviesDataRetriever;
    ViewFlipper tamil_popular, tamil_recent, tamil_rated;
    ImageView indicatorImageView[] = new ImageView[5];
    LinearLayout tamil_popular_indicator;
    int headImages[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head, R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

    static Bitmap popularBitmap[];
    static Bitmap ratedBitmap[];
    static Bitmap recentBitmap[];

    static boolean isBitmapCalculated = false;

    public void calculateBitmap() {
        moviesDataRetriever = new MoviesDataRetriever(getContext(), getResources());
        Movie[] popularMovies = moviesDataRetriever.getTopPopularMovies("Tamil");
        Movie[] ratedMovies = moviesDataRetriever.getTopRatedMovies("Tamil");
        Movie[] recentMovies = moviesDataRetriever.getTopRecentMovies("Tamil");
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

        if (isBitmapCalculated == false) {
            calculateBitmap();
        }

        tamil_popular.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Log.w("INDICATOR",i+" "+i1+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7);
            }
        });

        for (int i = 0; i < 5; i++) {
            indicatorImageView[i] = new ImageView(getContext());
            indicatorImageView[i].setImageResource(android.R.drawable.radiobutton_off_background);
            tamil_popular_indicator.addView(indicatorImageView[i]);
        }


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
        tamil_rated.setFlipInterval(2800);
        tamil_rated.startFlipping();

        tamil_recent.setAutoStart(true);
        tamil_recent.setFlipInterval(3000);
        tamil_recent.startFlipping();
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
