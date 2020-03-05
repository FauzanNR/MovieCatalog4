package com.example.moviecatalog4.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "table_movie";
    public static final class FavoriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String TANGGAL = "tanggal";
        public static String VOTE = "vote";
        public static String OVERVIEW = "overview";
        public static String JENIS = "jenis";
        public static String IMG = "img_film";

    }
}
