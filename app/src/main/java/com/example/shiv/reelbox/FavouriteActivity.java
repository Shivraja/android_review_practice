package com.example.shiv.reelbox;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class FavouriteActivity extends AppCompatActivity {

    static MOVIE[] favouriteMovies = null;
    static FavouriteMovieListAdapter listAdapter;
    static MoviesDataRetriever moviesDataRetriever;
    ListView listView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        setTitle("Favourites");
        moviesDataRetriever = new MoviesDataRetriever(this, getResources());
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CONSTANTS.ACTION_BAR_COLOR));
        listView = (ListView) findViewById(R.id.favourite_list_view);
        imageView = (ImageView) findViewById(R.id.favourite_background_image);

        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);

        imageView.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startMoviesActivity(favouriteMovies[i].movieId);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CONSTANTS.FAVOURITE_ALTERED || favouriteMovies == null) {
            favouriteMovies = moviesDataRetriever.getFollowedMovies(CONSTANTS.USER_NAME);
            CONSTANTS.FAVOURITE_ALTERED = false;
            listAdapter = new FavouriteMovieListAdapter(favouriteMovies, getResources(), this);
        }
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
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

    public void startMoviesActivity(int movieId) {
        Intent moviesActivity = new Intent(this, MovieActivity.class);
        moviesActivity.putExtra("movieId", movieId);
        moviesDataRetriever.incrementViewCount(movieId);
        startActivity(moviesActivity);
    }
}
