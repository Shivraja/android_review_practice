package com.example.shiv.list_view_practice;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MoviesData moviesData = new MoviesData();
    int iconImage[], headImage[] ;
    String[] moviesName;
    double[] rating;
    int[] year;
    String[] language;
    static int headId;
    NotificationManager notificationManager;
    NotificationCompat.Builder notificationCompat;
    ImageView headView, cast1, cast2;
    TextView headName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationCompat = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        headView = (ImageView)findViewById(R.id.headimage);
        headName = (TextView)findViewById(R.id.head_name);
        //cast1 = (ImageView)findViewById(R.id.cast1);
        //cast2 = (ImageView)findViewById(R.id.cast2);
        iconImage = moviesData.getIconImages();
        headImage = moviesData.getHeadImages();
        moviesName = moviesData.getMovieNames();
        rating = moviesData.getMovieRatings();
        year = moviesData.getMovieYear();
        language = moviesData.getMovieLanguage();

        ListView listView = (ListView) findViewById(R.id.list);
        CustomAdapter movieListAdapter = new CustomAdapter(moviesName,iconImage,headImage,rating,year,language, this);
        listView.setAdapter(movieListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notificationCompat.setSmallIcon(android.R.drawable.sym_def_app_icon);
                notificationCompat.setContentTitle("List View Practice");
                notificationCompat.setContentText("The User Pressed " + i);
                notificationManager.notify(1, notificationCompat.build());
                Toast.makeText(getBaseContext(), i + " ", Toast.LENGTH_LONG).show();
                startMoviesActivity(i + 1);
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
                headView.setImageResource(headImage[i]);
                headName.setText(moviesName[i]);
          //      cast1.setImageResource(headImage[(i+4)%16]);
           //     cast2.setImageResource(headImage[(i+5)%16]);
                headId = i;
                // Log.w("ON SCROLL", i + "  " + i1 + "  " + i2);
                // Toast.makeText(getBaseContext(), "ON SCROLL " + i+"  "+i1+"  "+i2, Toast.LENGTH_SHORT);
            }
        });
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMoviesActivity(headId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
    public void startMoviesActivity(int movieId){
        Intent moviesActivity = new Intent(this,MoviesActivity.class);
        moviesActivity.putExtra("movieId",movieId);
        startActivity(moviesActivity);
    }
}