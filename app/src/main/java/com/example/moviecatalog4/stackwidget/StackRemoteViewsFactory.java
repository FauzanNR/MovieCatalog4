package com.example.moviecatalog4.stackwidget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalog4.Movie;
import com.example.moviecatalog4.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.example.moviecatalog4.database.DatabaseContract.CONTENT_URI;


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private static final String TAG = "TAG";
    private Cursor cur;
    private final List<Movie> mWidgetItems = new ArrayList<>();

    private final Context mContext;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public RemoteViews getViewAt(int position) {


        Movie item = new Movie();
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_favorite_widget_item);


        Bitmap bitmap = null;
        Log.d(TAG, "get mWidget: " + mWidgetItems.get(position).getPhoto());
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(mWidgetItems.get(position).getPhoto())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView2, bitmap);

        Log.d(TAG, "getViewAt: " + bitmap);
        Bundle extras = new Bundle();


        extras.putInt(FavoriteWidgetProvider.EXTRA_ITEM, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView2, fillInIntent);
        return rv;
    }

    private void setItem() {


        if (cur != null) {
            cur.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        cur = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Log.d(TAG, "SetMovieFav: " + cur.getString(2));
                Movie set = new Movie();
                set.setName(cur.getString(1));
                set.setPhoto(cur.getString(6));
                mWidgetItems.add(set);
            } while (cur.moveToNext());

            Binder.restoreCallingIdentity(identityToken);

        }
    }

    @Override
    public void onCreate() {
        setItem();
    }

    @Override
    public void onDataSetChanged() {
//        setItem();

    }

    @Override
    public void onDestroy() {
        //required
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
