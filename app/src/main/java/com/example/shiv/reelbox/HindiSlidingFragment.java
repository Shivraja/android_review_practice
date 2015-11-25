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
public class HindiSlidingFragment extends Fragment {

    static Bitmap popularBitmap[] = new Bitmap[5];
    static Bitmap ratedBitmap[] = new Bitmap[5];
    static Bitmap recentBitmap[] = new Bitmap[5];
    static boolean isBitmapCalculated = false;
    ViewFlipper hindi_popular, hindi_recent, hindi_rated;
    ImageView indicatorImageView[] = new ImageView[5];
    LinearLayout hindi_popular_indicator;
    int headImages[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head, R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

    public void calculateBitmap(){
        int start;
        for(int i=0;i<5;i++) {
            start = (int)(Math.random()*10);
            popularBitmap[i] = ImageOptimizer.getCorrespondingBitmap(getResources(), headImages[(start + i) % 13], 200, 150);
            start = (int)(Math.random()*10);
            ratedBitmap[i] = ImageOptimizer.getCorrespondingBitmap(getResources(), headImages[(start + i) % 13], 200, 150);
            start = (int)(Math.random()*10);
            recentBitmap[i] = ImageOptimizer.getCorrespondingBitmap(getResources(), headImages[(start + i) % 13], 200, 150);
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
        View view = inflater.inflate(R.layout.hindi_sliding_fragment, container, false);
        hindi_popular = (ViewFlipper)view.findViewById(R.id.movie_flipper_hindi_popular);
        hindi_rated = (ViewFlipper)view.findViewById(R.id.movie_flipper_hindi_rated);
        hindi_recent = (ViewFlipper)view.findViewById(R.id.movie_flipper_hindi_recent);
        hindi_popular_indicator = (LinearLayout)view.findViewById(R.id.hindi_popular_indicator);

        if(!isBitmapCalculated){
            calculateBitmap();
        }

        for(int i=0;i<5;i++){
            indicatorImageView[i] = new ImageView(getContext());
            indicatorImageView[i].setImageResource(android.R.drawable.radiobutton_off_background);
            hindi_popular_indicator.addView(indicatorImageView[i]);
        }
       // indicatorImageView[0].setImageResource(android.R.drawable.btn_radio);

        hindi_popular.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                Log.w("Visibility", i + "");
            }
        });

        hindi_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("hindi Popular");
            }
        });
        hindi_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("hindi Rated");
            }
        });

        hindi_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("hindi Recent");
            }
        });


        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(popularBitmap[i]);
            hindi_popular.addView(imageView);
        }

        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(ratedBitmap[i]);
            hindi_rated.addView(imageView);
        }

        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(recentBitmap[i]);
            hindi_recent.addView(imageView);
        }


        hindi_popular.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        hindi_popular.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        hindi_rated.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        hindi_rated.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);

        hindi_recent.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
        hindi_recent.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hindi_popular.setAutoStart(true);
        hindi_popular.setFlipInterval(2500);
        hindi_popular.startFlipping();

        hindi_rated.setAutoStart(true);
        hindi_rated.setFlipInterval(2800);
        hindi_rated.startFlipping();

        hindi_recent.setAutoStart(true);
        hindi_recent.setFlipInterval(3000);
        hindi_recent.startFlipping();
    }

    @Override
    public void onStop() {
        super.onPause();
        hindi_popular.stopFlipping();
        hindi_rated.stopFlipping();
        hindi_recent.stopFlipping();
    }

    public void startMovieListActivity(String listName){
        Intent movieListIntent = new Intent(getContext(),MovieListActivity.class);
        movieListIntent.putExtra("Name",listName);
        startActivity(movieListIntent);
    }
}
