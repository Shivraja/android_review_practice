package com.example.shiv.reelbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

public class ReviewActivity extends AppCompatActivity {

    ListView reviewList;
    ReviewListAdapter reviewListAdapter;
    ImageView headImage;
    ImageView backgroundImage;
    int movieId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = this.getIntent();
        int movieId = intent.getIntExtra("movieId",1);
        headImage = (ImageView)findViewById(R.id.review_head_Image);
        backgroundImage = (ImageView)findViewById(R.id.review_background_image);
        reviewList = (ListView)findViewById(R.id.review_review_list_view);
        ReviewDataRetriever reviewDataRetriever = new ReviewDataRetriever(this,getResources());
        Review reviews[] = reviewDataRetriever.getReviews(movieId);
        reviewListAdapter = new ReviewListAdapter(reviews,this);
        reviewList.setAdapter(reviewListAdapter);
        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);
        backgroundImage.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reviewList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.w("REVIEW",i+"");
                if(i==1){
                    headImage.getLayoutParams().height=0;
                    headImage.setMaxHeight(0);
                    headImage.setMinimumHeight(0);
                    headImage.setVisibility(View.INVISIBLE);
                //    headImage.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),android.R.anim.fade_in));
                }
                else if(i==0){
                    headImage.setMaxHeight(300);
                    headImage.setMinimumHeight(300);
                    headImage.getLayoutParams().height=300;
                    headImage.setVisibility(View.VISIBLE);
                    headImage.setEnabled(true);
                 //   headImage.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_out));
                }
            }
        });

        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId", 1);
        Movie movie = new MoviesDataRetriever(this,getResources()).getMovie(movieId);
        setTitle(movie.movieName);
        headImage.setImageBitmap(movie.headImageBitmap);
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
