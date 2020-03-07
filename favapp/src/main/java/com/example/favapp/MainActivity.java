package com.example.favapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionViewPager sectionViewPager = new SectionViewPager(getSupportFragmentManager());

        sectionViewPager.addFragment(new MovieFragment(), getString(R.string.title_movie));

        sectionViewPager.addFragment(new TvShowFragment(), getString(R.string.title_tv_show));

        viewPager.setAdapter(sectionViewPager);
    }
}
