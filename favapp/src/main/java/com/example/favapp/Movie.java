package com.example.favapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {


    private String title;
    private String release_date;
    private String vote_average;
    private String overView;
    private String jenis;
    private int id;


    public Movie(String title, String vote_average, String overView, String jenis, String img_film, int id, String release) {
        this.title = title;
        this.vote_average = vote_average;
        this.overView = overView;
        this.jenis = jenis;
        this.img_film = img_film;
        this.release_date = release;
        this.id = id;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        overView = in.readString();
        img_film = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getOverView() {
        return overView;
    }

    public String getImg_film() {
        return img_film;
    }

    private String img_film;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(vote_average);
        dest.writeString(overView);
        dest.writeString(img_film);
    }
}
