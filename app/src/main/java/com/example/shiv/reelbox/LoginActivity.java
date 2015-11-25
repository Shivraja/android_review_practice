package com.example.shiv.reelbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageView backgroundImage;
    Button login;
    TextView username, password;
    MoviesDataRetriever moviesDataRetriever;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES,MODE_PRIVATE);

        if (!sharedPreferences.getString("username", "NULL").matches("NULL")) {
            Intent intent = new Intent(this, SlidingPageActivity.class);
            startActivity(intent);
            finish();
        }

        moviesDataRetriever = new MoviesDataRetriever(this, getResources());

        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);

        backgroundImage = (ImageView) findViewById(R.id.login_background_image);
        backgroundImage.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
        username = (TextView) findViewById(R.id.login_username);
        password = (TextView) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moviesDataRetriever.isValidUser(username.getText().toString(), password.getText().toString())) {
                    SharedPreferences sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(getBaseContext(), SlidingPageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid Login, Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if (!sharedPreferences.getString("username", "NULL").matches("NULL")) {
            Intent intent = new Intent(this, SlidingPageActivity.class);
            startActivity(intent);
            finish();
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}