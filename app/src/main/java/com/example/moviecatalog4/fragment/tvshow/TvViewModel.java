package com.example.moviecatalog4.fragment.tvshow;

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


public class TvViewModel extends ViewModel {

    private String  url;
    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<Movie>> listData = new MutableLiveData<>();

    public void setDataTV(String search){
        if (search.equals("")) {
            url =  "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        }else{
            url = "https://api.themoviedb.org/3/search/tv?api_key="+ API_KEY +"&language=en-US&query="+search;

        }
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMovie = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setName(object.getString("name"));
                        movie.setDate_rilis(object.getString("first_air_date"));
                        movie.setId(object.getInt("id"));
                        movie.setDescription(object.getString("overview"));
                        String img = object.getString("poster_path");
                        movie.setPhoto("https://image.tmdb.org/t/p/w154/"+img);
                        movie.setRating(object.optString("vote_average"));
                        movie.setModel_jenis("tv");
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

    LiveData<ArrayList<Movie>> getData() {
        return listData;
    }

}
