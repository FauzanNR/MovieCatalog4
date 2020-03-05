package com.example.moviecatalog4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalog4.database.FavoriteMovieHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.IMG;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.JENIS;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.TANGGAL;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.moviecatalog4.database.DatabaseContract.FavoriteColumns.VOTE;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private FloatingActionButton favorite;

    private int idb;
    private Movie movie;
    private FavoriteMovieHelper moviesHelper;

    private int id;
    private String nama, deskripsi, rating, model_jenis, photo, rilis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView namaM = findViewById(R.id.item_nama_movie);
        TextView ratingM = findViewById(R.id.item_rating);
        TextView descM = findViewById(R.id.item_deskirpsi);
        TextView rilisM = findViewById(R.id.item_rilis);
        ImageView posterM = findViewById(R.id.item_poster);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        id = movie.getId();
        model_jenis = movie.getModel_jenis();

        nama = movie.getName();
        namaM.setText(nama);

        rilis = movie.getDate_rilis();
        rilisM.setText(rilis);

        rating=movie.getRating();
        ratingM.setText(rating);

        deskripsi = movie.getDescription();
        descM.setText(deskripsi);

        photo = movie.getPhoto();
        Glide.with(ItemDetailActivity.this)
                .load(photo)
                .apply(new RequestOptions())
                .into(posterM);

        moviesHelper = FavoriteMovieHelper.getInstance(getApplicationContext());
        moviesHelper.open();

        Button favorite = findViewById(R.id.btn_favorite);
        idb = moviesHelper.queryById(id);
        if (idb == 0){
            favorite.setText(getString(R.string.add_favorite));
        }
        else{
            favorite.setText(getString(R.string.delete_fav));
        }
        favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_favorite) {
            if (idb == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(_ID, id);
                contentValues.put(TITLE, nama);
                contentValues.put(OVERVIEW, deskripsi);
                contentValues.put(VOTE, rating);
                contentValues.put(IMG, photo);
                contentValues.put(TANGGAL, rilis);
                contentValues.put(JENIS, model_jenis);
                long res = moviesHelper.insert(contentValues);

                if (res > 0) {
                    Toast.makeText(ItemDetailActivity.this, getString(R.string.add_favorite) + " " + nama, Toast.LENGTH_SHORT).show();
                }
            } else {
                long res = moviesHelper.deleteById(String.valueOf(movie.getId()));
                if (res > 0) {
                    Toast.makeText(ItemDetailActivity.this, getString(R.string.delete_fav) + " " + nama, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}