package com.example.shiv.reelbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SlidingPageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(CONSTANTS.PREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_page);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CONSTANTS.ACTION_BAR_COLOR));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TamilSlidingFragment(), "Tamil");
        adapter.addFragment(new TamilSlidingFragment(), "English");
        adapter.addFragment(new TamilSlidingFragment(), "Hindi");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sliding_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_notify:
                showNotifications();
                break;
            case R.id.action_search:
                showSearch();
                break;
            case R.id.action_recommendation:
                showRecommendations();
                break;
            case R.id.action_settings:
                showSettings();
                break;
            case R.id.action_favourite:
                showFavourite();
                break;
            case R.id.action_logout:
                logout();
                break;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getString("username", "NULL").matches("NULL")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void showNotifications() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void showRecommendations() {
        Intent intent = new Intent(this, RecommendationActivity.class);
        startActivity(intent);
    }

    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showFavourite() {
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void showSearch() {
        Toast.makeText(this, "This Feature will be added soon :P", Toast.LENGTH_SHORT).show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}