package com.example.shiv.list_view_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MoviesActivity extends AppCompatActivity {

    TextView movieName;
    ImageView headImage;
    TextView language;
    RatingBar rating;
    TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movieName = (TextView)findViewById(R.id.movie_movie_name);
        headImage = (ImageView)findViewById(R.id.movie_head_Image);
        language = (TextView)findViewById(R.id.movie_movie_language);
        rating = (RatingBar)findViewById(R.id.movie_rate);
     //   year = (TextView)findViewById(R.id.movie_year);


        int movieId ;
        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId",1);

        Movies movies = MoviesData.movieList[movieId];
        movieName.setText(movies.movieName);
        headImage.setImageResource(movies.headImageId);
        language.setText(movies.language);
        rating.setRating(((float) movies.rating));
        setTitle(movies.movieName);
      //  year.setText(movies.year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
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
