package com.example.moviecatalog4;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int Id;
    private String Photo;
    private String date_rilis;
    private String name;
    private String Rating;
    private String description;

    public Movie(String name, String rating, String description, String model_jenis, String photo, int id, String date_rilis) {
        this.name = name;
        Rating = rating;
        this.description = description;
        this.model_jenis = model_jenis;
        Photo = photo;
        this.date_rilis = date_rilis;
        Id = id;
    }

    private String model_jenis;

    public Movie(Parcel in) {
        Id = in.readInt();
        Photo = in.readString();
        date_rilis = in.readString();
        name = in.readString();
        Rating = in.readString();
        description = in.readString();
        model_jenis = in.readString();
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

    public Movie() {

    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDate_rilis() {
        return date_rilis;
    }

    public void setDate_rilis(String date_rilis) {
        this.date_rilis = date_rilis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel_jenis() {
        return model_jenis;
    }

    public void setModel_jenis(String model_jenis) {
        this.model_jenis = model_jenis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Photo);
        dest.writeString(date_rilis);
        dest.writeString(name);
        dest.writeString(Rating);
        dest.writeString(description);
        dest.writeString(model_jenis);
    }
}

