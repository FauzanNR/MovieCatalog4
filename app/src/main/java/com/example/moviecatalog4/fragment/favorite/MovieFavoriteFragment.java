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
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadMovieCallbakc {
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private FavoriteMovieHelper movieHelper;
    private RecyclerView recyclerView;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        movieHelper = FavoriteMovieHelper.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        movieHelper.open();

        new LoadMovieASync(movieHelper, this).execute();
        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.EXTRA_MOVIE,movie);
                startActivity(intent);
            }
        });
    }

    @Override
    public void preExecute(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies){
        progressBar.setVisibility(View.VISIBLE);;
        if (movies.size() > 0){
            movieAdapter.setData(movies);
        }
        else {
            movieAdapter.setData(new ArrayList<Movie>());
            showSnackBarMessage("tidak ada data saat ini");
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        movieHelper.close();
    }

    private static class LoadMovieASync extends AsyncTask<Void,Void,ArrayList<Movie>>{
        private final WeakReference<FavoriteMovieHelper> weakReference;
        private  final WeakReference<LoadMovieCallbakc> weakCallback;
        private LoadMovieASync(FavoriteMovieHelper favoriteMovieHelper, LoadMovieCallbakc loadMovieCallbakc) {
            weakReference = new WeakReference<>(favoriteMovieHelper);
           weakCallback = new WeakReference<>(loadMovieCallbakc);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = weakReference.get().getDataByName("movie");
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies){
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }


    }

    private void showSnackBarMessage(String message){
        Snackbar.make(recyclerView,message,Snackbar.LENGTH_SHORT).show();
    }

}
interface LoadMovieCallbakc {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}