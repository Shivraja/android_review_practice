package com.example.shiv.reelbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RecommendationActivity extends AppCompatActivity {

    ListView recommendationList;
    MoviesDataRetriever moviesDataRetriever;
    static RECOMMEND[] recommends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        moviesDataRetriever = new MoviesDataRetriever(this,getResources());
        recommendationList = (ListView)findViewById(R.id.recommendation_list);
        recommends = moviesDataRetriever.getRecommendations();
        Log.w("RECOMMEND", recommends.length + " ");
        recommendationList.setAdapter(new RecommendationListAdapter(this, recommends));
        recommendationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startMoviesActivity(recommends[i].movie.movieId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommendation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            clearRecommendations();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearRecommendations(){
        moviesDataRetriever.clearRecommendations();
        finish();
        startActivity(getIntent());
    }

    public void startMoviesActivity(int movieId) {
        Intent moviesActivity = new Intent(this, MovieActivity.class);
        moviesActivity.putExtra("movieId", movieId);
        moviesDataRetriever.incrementViewCount(movieId);
        startActivity(moviesActivity);
    }
}