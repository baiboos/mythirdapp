package com.zjt.mythirdapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

public class ViewPagerrActivity extends AppCompatActivity {
    private String TAG="viewPager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pagerr);

        Log.i(TAG,"111111");
        ViewPager viewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        Log.i(TAG,"333333");
        viewPager.setAdapter(pageAdapter);
        Log.i(TAG,"444444");

    }


}
