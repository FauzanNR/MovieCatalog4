package com.example.moviecatalog4.fragment.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalog4.ItemDetailActivity;
import com.example.moviecatalog4.Movie;
import com.example.moviecatalog4.R;
import com.example.moviecatalog4.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class TvShowFragment extends Fragment {
    private MovieAdapter adapter;
    private ProgressBar progressBar;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tv_shows);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        TvViewModel tvViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvViewModel.class);
        tvViewModel.setDataTV();
        tvViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null){
                    adapter.setData(movies);
                    Loading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.EXTRA_MOVIE, movie);
                startActivity(intent);
            }
        });

        return view;
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
