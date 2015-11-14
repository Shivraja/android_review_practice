package com.example.shiv.reelbox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    TextView movieName;
    ImageView headImage;
    ImageView backgroundImage;
    TextView language;
    RatingBar rating;
    TextView year;
    ListView reviewList;
    Button showReviews;
    ImageView favouriteImageView;
    ReviewListAdapter reviewListAdapter;
    LinearLayout links;
    Toolbar toolbar;
    static MoviesDataRetriever moviesDataRetriever;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesDataRetriever = new MoviesDataRetriever(this, getResources());

        setContentView(R.layout.activity_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        Intent currentIntent = this.getIntent();
        movieId = currentIntent.getIntExtra("movieId", 1);

        Movie movie = moviesDataRetriever.getMovie(movieId);
        movieName.setText(movie.movieName + " (" + movie.year + ")");
        headImage.setImageBitmap(movie.headImageBitmap);
        language.setText("Language : " + movie.language);
        rating.setRating(((float) movie.rating));

        if(moviesDataRetriever.isFollowed(movieId,CONSTANTS.USER_NAME)){
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

        setTitle(movie.movieName);

        showReviews = (Button) findViewById(R.id.showReviews);
        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
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

    public void zoomImage(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                zoomImageFromThumb(view, R.drawable.rajini, R.id.expanded_image);
                break;
            case R.id.imageView2:
                zoomImageFromThumb(view, R.drawable.surya, R.id.expanded_image);
                break;
            case R.id.imageView3:
                zoomImageFromThumb(view, R.drawable.trisha, R.id.expanded_image);
                break;
            case R.id.imageView4:
                zoomImageFromThumb(view, R.drawable.unknown, R.id.expanded_image);
                break;
        }

    }

    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    private void zoomImageFromThumb(final View thumbView, int imageResId, int expandedImageId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                expandedImageId);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.movies_activity_layout)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}