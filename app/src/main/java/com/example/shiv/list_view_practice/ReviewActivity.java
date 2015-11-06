package com.example.shiv.list_view_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

public class ReviewActivity extends AppCompatActivity {

    ListView reviewList;
    ReviewAdapter reviewAdapter;
    ImageView headImage;
    int movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        headImage = (ImageView)findViewById(R.id.review_head_Image);
        reviewList = (ListView)findViewById(R.id.review_review_list_view);
        ReviewData reviewData = new ReviewData();
        Reviews reviews[] = reviewData.getReviews(1+"");
        reviewAdapter = new ReviewAdapter(reviews,this);
        reviewList.setAdapter(reviewAdapter);
        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId", 1);
        Movies movies = MoviesData.movieList[movieId];
        setTitle(movies.movieName);
        headImage.setImageResource(movies.headImageId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
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

    /*
    public void likeReview(View view){
        view.setBackgroundColor(Color.GREEN);
    }

    public void unlikeReview(View view){
        view.setBackgroundColor(Color.RED);
    }
    */

}
