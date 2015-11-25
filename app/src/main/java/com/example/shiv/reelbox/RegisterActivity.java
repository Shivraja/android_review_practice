package com.example.shiv.reelbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    ImageView backgroundImage;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES, MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "NULL").matches("NULL")) {
            Intent intent = new Intent(this, SlidingPageActivity.class);
            startActivity(intent);
            finish();
        }
        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);
        setContentView(R.layout.activity_register);
        backgroundImage = (ImageView) findViewById(R.id.register_background_image);
        backgroundImage.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
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
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public void login(View view){
        Intent intent = new Intent(this,SlidingPageActivity.class);
        startActivity(intent);
        finish();
    }
}