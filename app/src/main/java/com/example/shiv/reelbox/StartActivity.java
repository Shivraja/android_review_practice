package com.example.shiv.reelbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    ImageView backgroundImage;
    MoviesDataRetriever moviesDataRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();
        moviesDataRetriever = new MoviesDataRetriever(this,getResources());

        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);

        backgroundImage = (ImageView) findViewById(R.id.start_background_image);
        backgroundImage.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
        moviesDataRetriever.getTopPopularMovies("Tamil");
        moviesDataRetriever.getTopRatedMovies("Tamil");
        moviesDataRetriever.getRecentMovies("Tamil");
        moviesDataRetriever.getPopularMovies("Tamil");
        moviesDataRetriever.getRatedMovies("Tamil");
        moviesDataRetriever.getRecentMovies("Tamil");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES, MODE_PRIVATE);
        final Intent intent;
        Log.w("START","started");
        String username = sharedPreferences.getString("username", "NULL");
        if (username.matches("NULL")) {
            intent = new Intent(this, RegisterActivity.class);
        }else{
            intent = new Intent(this,SlidingPageActivity.class);
            CONSTANTS.USER_NAME = username;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);
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