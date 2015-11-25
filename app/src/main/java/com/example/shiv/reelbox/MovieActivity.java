package com.example.shiv.reelbox;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    static CASTS[] casts;
    static MoviesDataRetriever moviesDataRetriever;
    static int movieId;
    TextView movieName;
    ImageView headImage;
    ImageView backgroundImage;
    TextView language;
    RatingBar rating;
    Button showReviews;
    ImageView favouriteImageView;
    LinearLayout links;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesDataRetriever = new MoviesDataRetriever(this, getResources());

        setContentView(R.layout.activity_movie);
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CONSTANTS.ACTION_BAR_COLOR));

        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId", 1);

        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);

        movieName = (TextView) findViewById(R.id.movie_movie_name);
        headImage = (ImageView) findViewById(R.id.movie_head_Image);
        backgroundImage = (ImageView) findViewById(R.id.movie_background_image);
        backgroundImage.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
        favouriteImageView = (ImageView) findViewById(R.id.favourite_image);
        language = (TextView) findViewById(R.id.movie_movie_language);
        rating = (RatingBar) findViewById(R.id.movie_rate);
        links = (LinearLayout) findViewById(R.id.links);
        fragmentManager = this.getFragmentManager();

        if (!moviesDataRetriever.isRated(movieId, CONSTANTS.USER_NAME)) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.rating_fragment, new RatingFragment());
            fragmentTransaction.commit();
        }

        MOVIE movie = moviesDataRetriever.getMovie(movieId);
        movieName.setText(movie.movieName + " (" + movie.year + ")");
        headImage.setImageBitmap(movie.headImageBitmap);
        language.setText("Language : " + movie.language);
        rating.setRating((movie.rating));

        casts = movie.casts;

        int length = casts.length;
            FragmentTransaction castTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < length; i++) {
                CastsFragment castsFragment = new CastsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("castIndex", i);
                castsFragment.setArguments(bundle);
                castTransaction.add(R.id.movie_cast_fragment, castsFragment, movieId + "" + i);
            }
            castTransaction.commit();

        setTitle(movie.movieName);
        if (moviesDataRetriever.isFollowed(movieId, CONSTANTS.USER_NAME)) {
            favouriteImageView.setBackgroundColor(Color.parseColor("#b9d4081d"));
        } else {
            favouriteImageView.setBackgroundColor(0x00000000);
        }

        for (String link : movie.links) {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            String linkText = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Link &nbsp;&nbsp;<a href='" + link + "'>" + link + "</a> .";
            textView.setText(Html.fromHtml(linkText));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            links.addView(textView);
        }
        showReviews = (Button) findViewById(R.id.showReviews);
        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });
    }

    public void startReviewActivity() {
        Intent reviewIntent = new Intent(getBaseContext(), ReviewActivity.class);
        reviewIntent.putExtra("movieId", movieId);
        startActivity(reviewIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getString("username", "NULL").matches("NULL")) {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }

    public void SubmitReview(View view) {
        EditText text = (EditText) findViewById(R.id.user_review);
        Button button = (Button) findViewById(R.id.review_submit_button);
        button.setVisibility(View.INVISIBLE);
        text.setClickable(false);
        text.setCursorVisible(false);
    }

    public void addFavourite(View view) {
        if (moviesDataRetriever.isFollowed(movieId, CONSTANTS.USER_NAME)) {
            view.setBackgroundColor(0x00000000);
            moviesDataRetriever.removeFollow(movieId, CONSTANTS.USER_NAME);
        } else {
            view.setBackgroundColor(Color.parseColor("#b9d4081d"));
            moviesDataRetriever.addFollow(movieId, CONSTANTS.USER_NAME);
        }
    }
}