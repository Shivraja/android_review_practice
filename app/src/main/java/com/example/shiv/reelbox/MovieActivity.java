package com.example.shiv.reelbox;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieActivity extends AppCompatActivity {

    static CASTS[] casts;
    static MoviesDataRetriever moviesDataRetriever;
    static int movieId;
    TextView movieName;
    ImageView headImage;
    ImageView backgroundImage;
    TextView language;
    TextView description;
    RatingBar rating;
    Button showReviews;
    ImageView favouriteImageView;
    LinearLayout links;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button postButton,cancelButton;
    ImageView reviewEditImage, reviewDeleteImage;
    EditText userReview;
    static boolean fragmentsAdded = false;
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
        description = (TextView)findViewById(R.id.movie_description);
        postButton = (Button)findViewById(R.id.review_post_button);
        cancelButton = (Button)findViewById(R.id.review_cancel_button);
        reviewEditImage = (ImageView)findViewById(R.id.review_edit_button);
        reviewDeleteImage = (ImageView)findViewById(R.id.review_delete_button);
        userReview = (EditText) findViewById(R.id.user_review);

        fragmentManager = this.getFragmentManager();

        if (!moviesDataRetriever.isRated(movieId, CONSTANTS.USER_NAME)) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rating_fragment, new RatingFragment());
            fragmentTransaction.commit();
        }

        MOVIE movie = moviesDataRetriever.getMovie(movieId);
        movieName.setText(movie.movieName + " (" + movie.year + ")");
        headImage.setImageBitmap(movie.headImageBitmap);
        language.setText("Language : " + movie.language);
        rating.setRating((movie.rating));
        description.setText(movie.description);
        casts = movie.casts;

        if(!fragmentsAdded) {
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
            fragmentsAdded = true;
        }

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
            String linkText = "Link &nbsp;&nbsp;<a href='" + link + "'>" + link + "</a> .";
            textView.setText(Html.fromHtml(linkText));
            float density = this.getResources().getDisplayMetrics().density;
            float px = 8 * density;
            textView.setTextSize(px);
            textView.setPadding((int)(15*density),(int)(5*density),(int)(5*density),(int)(10*density));
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

        performUserReviewOperations();


    }

    public void performUserReviewOperations(){
        String review = moviesDataRetriever.getReview(movieId,CONSTANTS.USER_NAME);

        if(review.equals("-1")){ // No Reviews Posted Already - Only post button
            postButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.GONE);
            reviewEditImage.setVisibility(View.GONE);
            reviewDeleteImage.setVisibility(View.GONE);

        }else{ // Reviews Posted Already
            userReview.setText(review);
            userReview.setEnabled(false);
           // userReview.setFocusable(false);
            postButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            reviewEditImage.setVisibility(View.VISIBLE);
            reviewDeleteImage.setVisibility(View.VISIBLE);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userReview.setText("");
                cancelButton.setVisibility(View.INVISIBLE);
                String review = moviesDataRetriever.getReview(movieId, CONSTANTS.USER_NAME);

                if (review.equals("-1")) {
                    postButton.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.GONE);
                    reviewEditImage.setVisibility(View.GONE);
                    reviewDeleteImage.setVisibility(View.GONE);
                    userReview.setEnabled(false);
                    userReview.setEnabled(true);
                } else {
                    userReview.setText(review);
                    userReview.setEnabled(false);
                    postButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                    reviewEditImage.setVisibility(View.VISIBLE);
                    reviewDeleteImage.setVisibility(View.VISIBLE);
                }
            }
        });

        userReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cancelButton.setVisibility(View.VISIBLE);
                return false;
            }
        });

        reviewEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewEditImage.setVisibility(View.GONE);
                reviewDeleteImage.setVisibility(View.GONE);
                postButton.setText("UPDATE");
                postButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                userReview.setEnabled(true);
                //userReview.setFocusable(true);
                //userReview.requestFocus();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = userReview.getText().toString();
                if(review.equals("")){
                    Toast.makeText(getBaseContext(),"Review is Empty",Toast.LENGTH_SHORT).show();
                }else{
                    moviesDataRetriever.addOrUpdateMovieReview(movieId, CONSTANTS.USER_NAME, review);
                    postButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                    reviewEditImage.setVisibility(View.VISIBLE);
                    reviewDeleteImage.setVisibility(View.VISIBLE);
                    userReview.setEnabled(false);
                    //userReview.setFocusable(false);
                    //Toast.makeText(getBaseContext(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

        reviewDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moviesDataRetriever.removeMovieReview(movieId, CONSTANTS.USER_NAME);
                userReview.setText("");
                userReview.setEnabled(true);
                //userReview.setFocusable(true);
                reviewDeleteImage.setVisibility(View.GONE);
                reviewEditImage.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                postButton.setText("POST");
                postButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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