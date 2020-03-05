package com.example.moviecatalog4.API_HELPER;

import android.content.Context;
import android.widget.Toast;


import com.example.moviecatalog4.R;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ApiUtility {
    public static  String API_KEY = "40d4e6eaaaeba0ef05890d8799ae9d44";
    static String BASE_URL_API = "https://api.themoviedb.org/3/discover/";
    public static String _BASE_URL_IMG = "https://image.tmdb.org/t/p/";

    public static void resultConnection(Throwable throwable, Context context){
        if (throwable instanceof SocketTimeoutException){
            Toast.makeText(context, R.string.time_out, Toast.LENGTH_SHORT).show();
        }
        else if (throwable instanceof UnknownHostException){
            Toast.makeText(context, R.string.no_connection,Toast.LENGTH_SHORT).show();
        }
    }
}
