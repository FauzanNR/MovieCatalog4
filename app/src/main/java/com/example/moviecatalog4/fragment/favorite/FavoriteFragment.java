package com.example.moviecatalog4.fragment.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviecatalog4.R;
import com.example.moviecatalog4.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {
    public FavoriteFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getActivity(),getParentFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return view;

    }
}