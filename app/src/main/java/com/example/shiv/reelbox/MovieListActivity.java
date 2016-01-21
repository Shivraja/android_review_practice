package com.example.shiv.reelbox;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MovieListActivity extends AppCompatActivity {

    static int headId;
    static MOVIE[] movies;
    static MOVIE[] tamilPopular = null, tamilRecent = null, tamilRated = null, englishPopular = null, englishRated = null, englishRecent = null, hindiPopular=null, hindiRated = null, hindiRecent = null, others = null;

    MoviesDataRetriever moviesData;
    NotificationManager notificationManager;
    NotificationCompat.Builder notificationCompat;
    ImageView headView;
    TextView headName;
    String activityName = null;
    int listType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        moviesData = new MoviesDataRetriever(this, getResources());
        Intent intent = this.getIntent();
        activityName = intent.getStringExtra("Name");
        listType = intent.getIntExtra("Type", 1);
        setTitle(activityName);
        setContentView(R.layout.activity_movie_list);

        if (activityName == null) {
            activityName = "Tamil Popular";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CONSTANTS.ACTION_BAR_COLOR));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        notificationCompat = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        headView = (ImageView) findViewById(R.id.headimage);
        headName = (TextView) findViewById(R.id.head_name);

        ListView listView = (ListView) findViewById(R.id.list);

        switch (listType) {
            case CONSTANTS.TAMIL_POPULAR:
                if (tamilPopular == null)
                    tamilPopular = moviesData.getPopularMovies("Tamil");
                movies = tamilPopular;
                break;
            case CONSTANTS.TAMIL_RATED:
                if (tamilRated == null)
                    tamilRated = moviesData.getRatedMovies("Tamil");
                movies = tamilRated;
                break;
            case CONSTANTS.TAMIL_RECENT:
                if (tamilRecent == null)
                    tamilRecent = moviesData.getRecentMovies("Tamil");
                movies = tamilRecent;
                break;
            case CONSTANTS.ENGLISH_POPULAR:
                if (englishPopular == null)
                    englishPopular = moviesData.getPopularMovies("English");
                movies = englishPopular;
                break;
            case CONSTANTS.ENGLISH_RATED:
                if (englishRated == null)
                    englishRated = moviesData.getPopularMovies("English");
                movies = englishRated;
                break;
            case CONSTANTS.ENGLISH_RECENT:
                if (englishRecent == null)
                    englishRecent = moviesData.getPopularMovies("English");
                movies = englishRecent;
                break;
            case CONSTANTS.HINDI_POPULAR:
                if (hindiPopular == null)
                    hindiPopular = moviesData.getPopularMovies("Hindi");
                movies = hindiPopular;
                break;
            case CONSTANTS.HINDI_RATED:
                if (hindiRated == null)
                    hindiRated = moviesData.getPopularMovies("Hindi");
                movies = hindiRated;
                break;
            case CONSTANTS.HINDI_RECENT:
                if (hindiRecent == null)
                    hindiRecent = moviesData.getPopularMovies("Hindi");
                movies = hindiRecent;
                break;
        }

        MovieListAdapter movieListAdapter = new MovieListAdapter(movies, getResources(), this);
        listView.setAdapter(movieListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /*
                notificationCompat.setSmallIcon(android.R.drawable.sym_def_app_icon);
                notificationCompat.setContentTitle("List View Practice");
                notificationCompat.setContentText("The User Pressed " + i);
                notificationManager.notify(1, notificationCompat.build());

                Toast.makeText(getBaseContext(), i + " ", Toast.LENGTH_LONG).show();
                */
                startMoviesActivity(movies[i].movieId);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //   Log.w("STATE CHANGED", i + "");
                //   Toast.makeText(getBaseContext(),"STATE CHANGED  "+i+"",Toast.LENGTH_SHORT);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //headView.smoothScrollToPosition(i);
                headView.setImageBitmap(movies[i].headImageBitmap);
                headName.setText(movies[i].movieName);
                //      cast1.setImageResource(headImage[(i+4)%16]);
                //     cast2.setImageResource(headImage[(i+5)%16]);
                headId = i;
            }
        });
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMoviesActivity(movies[headId].movieId);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
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
        moviesData.incrementViewCount(movieId);
        startActivity(moviesActivity);
    }
}