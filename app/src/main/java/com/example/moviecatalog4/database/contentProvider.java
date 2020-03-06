package com.example.moviecatalog4.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.moviecatalog4.database.DatabaseContract.Author;
import static com.example.moviecatalog4.database.DatabaseContract.CONTENT_URI;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.JENIS;
import static com.example.moviecatalog4.database.DatabaseContract.TABLE_NAME;

public class contentProvider extends android.content.ContentProvider {


    SQLiteDatabase liteDatabase;
    private static final int MOVIE = 1;

    private static final int MOVIE_ID = 2;

    private FavoriteMovieHelper dbhelp;


    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(Author, TABLE_NAME, MOVIE);

        URI_MATCHER.addURI(Author, TABLE_NAME + "/#", MOVIE_ID);

    }

    @Override
    public boolean onCreate() {
        Context context = getContext();

        DatabaseHelper favoriteMovieHelper = new DatabaseHelper(context);
        liteDatabase = favoriteMovieHelper.getWritableDatabase();
        return (liteDatabase != null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                deleted = dbhelp.delete(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        int updated;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                updated = dbhelp.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                added = liteDatabase.insert(TABLE_NAME, null, values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                cursor = liteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
                break;
            case MOVIE_ID:
                cursor = liteDatabase.query(TABLE_NAME,
                        null,
                        JENIS + "= " + "\"" + projection + "\"",
                        null,
                        null,
                        null,
                        null);
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }
}
