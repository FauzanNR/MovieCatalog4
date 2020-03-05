package com.example.moviecatalog4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.IMG;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.JENIS;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.TANGGAL;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.VOTE;
import static com.example.moviecatalog4.database.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovies";


    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_MOVIE = "create table " + TABLE_NAME +
            " (" + _ID + " integer primary key, " +
            TITLE + " text not null, " +
            TANGGAL + " text not null, " +
            VOTE + " text not null, " +
            OVERVIEW + " text not null, "+
            JENIS + " text not null, " +
            IMG + " text not null );";


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
