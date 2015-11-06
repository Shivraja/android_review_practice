package com.example.shiv.list_view_practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MoviesActivity extends AppCompatActivity {

    TextView movieName;
    ImageView headImage;
    TextView language;
    RatingBar rating;
    TextView year;
    ListView reviewList;
    TextView showReviews;
    ReviewAdapter reviewAdapter;
    LinearLayout links;

    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movieName = (TextView) findViewById(R.id.movie_movie_name);
        headImage = (ImageView) findViewById(R.id.movie_head_Image);
        language = (TextView) findViewById(R.id.movie_movie_language);
        rating = (RatingBar) findViewById(R.id.movie_rate);
        links = (LinearLayout)findViewById(R.id.links);

        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId", 1);

        Movies movies = MoviesData.movieList[movieId];
        movieName.setText(movies.movieName+" ("+movies.year+")");
        headImage.setImageResource(movies.headImageId);
        language.setText("Language : "+movies.language);
        rating.setRating(((float) movies.rating));

        for(String link : movies.links){
            TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            String linkText = "Link <a href='"+link+"'>"+link+"</a> .";
            textView.setText(Html.fromHtml(linkText));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            links.addView(textView);
        }

        setTitle(movies.movieName);

        ReviewData reviewData = new ReviewData();
        Reviews reviews[] = reviewData.getReviews(movieId+"");

        showReviews = (TextView)findViewById(R.id.showReviews);
        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });
    }

    public void startReviewActivity(){
        Intent reviewIntent = new Intent(getBaseContext(),ReviewActivity.class);
        reviewIntent.putExtra("movieId",movieId);
        startActivity(reviewIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    public  void SubmitReview(View view){
        EditText text = (EditText)findViewById(R.id.user_review);
        Button button = (Button)findViewById(R.id.review_submit_button);
        button.setVisibility(View.INVISIBLE);
        text.setClickable(false);
        text.setCursorVisible(false);
    }

    public void addFavourite(View view){
        view.setBackgroundColor(Color.RED);
    }
}