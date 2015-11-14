package com.example.shiv.list_view_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MovieHomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper tamil_popular, tamil_recent, tamil_rated, english_popular, english_recent, english_rated;

    int headImages[] = {R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,R.drawable.head_9,R.drawable.head_10,R.drawable.head_abi,R.drawable.head_aranmanai,R.drawable.head_saivam};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_home);
        tamil_popular = (ViewFlipper)findViewById(R.id.movie_flipper_tamil_popular);
        tamil_rated = (ViewFlipper)findViewById(R.id.movie_flipper_tamil_rated);
        tamil_recent = (ViewFlipper)findViewById(R.id.movie_flipper_tamil_recent);
        english_popular = (ViewFlipper)findViewById(R.id.movie_flipper_english_popular);
        english_rated = (ViewFlipper)findViewById(R.id.movie_flipper_english_rated);
        english_recent = (ViewFlipper)findViewById(R.id.movie_flipper_english_recent);

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
        english_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Popular");
            }
        });
        english_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Rated");
            }
        });
        english_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMovieListActivity("English Recent");
            }
        });
        int start;
        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_popular.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_rated.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            tamil_recent.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            english_popular.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+ i)%13]);
            english_rated.addView(imageView);
        }

        start = (int)(Math.random()*10);
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(headImages[(start+i)%13]);
            english_recent.addView(imageView);
        }


        tamil_popular.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_popular.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_rated.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_rated.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);

        tamil_recent.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        tamil_recent.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);

        english_popular.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        english_popular.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);

        english_rated.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        english_rated.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);

        english_recent.setAutoStart(true);
        english_recent.setFlipInterval(1900);
        english_recent.startFlipping();
        english_recent.setInAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_in);
        english_recent.setOutAnimation(this, android.support.v7.appcompat.R.anim.abc_fade_out);
    }

    public void startMovieListActivity(String listName){
        Intent movieListIntent = new Intent(this,MainActivity.class);
        startActivity(movieListIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies_home, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tamil_popular.setAutoStart(true);
        tamil_popular.setFlipInterval(1700);
        tamil_popular.startFlipping();

        tamil_rated.setAutoStart(true);
        tamil_rated.setFlipInterval(1800);
        tamil_rated.startFlipping();

        tamil_recent.setAutoStart(true);
        tamil_recent.setFlipInterval(1900);
        tamil_recent.startFlipping();

        english_popular.setAutoStart(true);
        english_popular.setFlipInterval(1700);
        english_popular.startFlipping();

        english_rated.setAutoStart(true);
        english_rated.setFlipInterval(1800);
        english_rated.startFlipping();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tamil_popular.stopFlipping();
        tamil_rated.stopFlipping();
        tamil_recent.stopFlipping();
        english_popular.stopFlipping();
        english_rated.stopFlipping();
        english_recent.stopFlipping();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
