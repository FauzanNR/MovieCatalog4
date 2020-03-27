package com.example.favapp.database;

import android.database.Cursor;

import com.example.favapp.Movie;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor cursor){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW));
            String photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMG));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTE));
            String model_jenis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.JENIS));
            String date_rilis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TANGGAL));

            movieArrayList.add(new Movie(name,rating,description,model_jenis,photo,id,date_rilis));
        }
        return movieArrayList;
    }
}