package com.example.favapp;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.favapp.database.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favapp.database.DatabaseContract.CONTENT_URI;

public class LoadMoviesAsync extends AsyncTask<Void, Void , ArrayList<Movie>> {
    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadMoviesCallback> weakCallback;
    private String jenis;

    LoadMoviesAsync(Context context, LoadMoviesCallback callback, String jenis) {
        weakContext = new WeakReference<>(context);
        weakCallback = new WeakReference<>(callback);
        this.jenis = jenis;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weakCallback.get().preExecute();
    }
    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        Context context = weakContext.get();
        Cursor dataCursor = context.getContentResolver().query(CONTENT_URI, null, jenis, null, null);
        return MappingHelper.mapCursorToArrayList(dataCursor);
    }
    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        weakCallback.get().postExecute(movies);
    }



}


interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}

