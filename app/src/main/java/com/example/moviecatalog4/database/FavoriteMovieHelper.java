package com.example.moviecatalog4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.JENIS;
import static com.example.moviecatalog4.database.DatabaseContract.TABLE_NAME;

public class FavoriteMovieHelper {
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private static FavoriteMovieHelper INSTANCE;

    private FavoriteMovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }
    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public long insert(ContentValues values) {

        return database.insert(TABLE_NAME, null, values);
    }

    public Cursor getDataByName(String nama) {
        return database.query(
                TABLE_NAME, null,
                JENIS + " LIKE ?",
                new String[]{nama},
                null,
                null,
                null,
                null
        );
    }
    public int deleteById(String id) {
        return database.delete(TABLE_NAME, _ID + " = ?", new String[]{id});
    }
    public int queryById( int id){
        Cursor cursor = database.query(
                TABLE_NAME, null,
                _ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        if (cursor.getCount()>0){
            return 1;
        }
        else return 0;
    }
}

