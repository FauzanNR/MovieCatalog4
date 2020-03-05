package com.example.moviecatalog4.fragment.favorite;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviecatalog4.ItemDetailActivity;
import com.example.moviecatalog4.Movie;
import com.example.moviecatalog4.R;
import com.example.moviecatalog4.adapter.MovieAdapter;
import com.example.moviecatalog4.database.FavoriteMovieHelper;
import com.example.moviecatalog4.dbhelper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements LoadTvsCallback{
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private FavoriteMovieHelper Helper;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.tv_favorite_tvShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        Helper = FavoriteMovieHelper.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        Helper.open();


        new LoadTvsAsync(Helper, this).execute();



        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.EXTRA_MOVIE,data);
                startActivity(intent);
            }
        });



    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            movieAdapter.setData(movies);
        } else {
            movieAdapter.setData(new ArrayList<Movie>());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Helper.close();
    }

    private static class LoadTvsAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<FavoriteMovieHelper> weakNoteHelper;
        private final WeakReference<LoadTvsCallback> weakCallback;
        private LoadTvsAsync(FavoriteMovieHelper moviesHelper, LoadTvsCallback callback) {
            weakNoteHelper = new WeakReference<>(moviesHelper);
            weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor cursor = weakNoteHelper.get().getDataByName("tv");
            return MappingHelper.mapCursorToArrayList(cursor);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }


}

interface LoadTvsCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
