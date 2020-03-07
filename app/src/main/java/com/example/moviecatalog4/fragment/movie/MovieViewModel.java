package com.example.moviecatalog4.fragment.movie;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalog4.BuildConfig;
import com.example.moviecatalog4.Movie;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listData = new MutableLiveData<>();
    private String url;
    private static final String apiKey = BuildConfig.API_KEY;

    public void setDataMovie(String search){
        if (search.equals("")) {
            url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=en-US";
        }else{
            url = "https://api.themoviedb.org/3/search/movie?api_key="+ apiKey +"&language=en-US&query="+search;

        }
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMovie = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray array = responseObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(object.getInt("id"));
                        movie.setName(object.optString("title"));
                        movie.setDescription(object.optString("overview"));
                        movie.setRating(object.optString("vote_average"));
                        movie.setDate_rilis(object.optString("release_date"));
                        String img = object.getString("poster_path");
                        movie.setPhoto("https://image.tmdb.org/t/p/w154/" + img);
                        movie.setModel_jenis("movie");
                        listMovie.add(movie);
                    }
                    listData.postValue(listMovie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Movie>> getData() {
        return listData;
    }

}
