package com.example.favapp.database;

import android.net.Uri;
import android.provider.BaseColumns;
public class DatabaseContract {
    public static final String Author = "com.example.moviecatalog4";
    static String TABLE_NAME = "table_movie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(Author).appendPath(TABLE_NAME).build();
    public static final class FavoriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String TANGGAL = "tanggal";
        public static String VOTE = "vote";
        public static String OVERVIEW = "overview";
        public static String JENIS = "jenis";
        public static String IMG = "img_film";

    }
}
