package com.example.favapp;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    private final ArrayList<Movie> listMovie = new ArrayList<>();




    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {

        holder.bind(listMovie.get(position));

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imgMovie;
        TextView tvRating, tvRelease,tvTitle;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating =itemView.findViewById(R.id.rating2);
            imgMovie = itemView.findViewById(R.id.img_film);
            tvRelease = itemView.findViewById(R.id.release2);
            tvTitle = itemView.findViewById(R.id.tv_judul);
        }
        void bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvRelease.setText(movie.getRelease_date());
            tvRating.setText(movie.getVote_average());

            Glide.with(itemView.getContext())
                    .load(movie.getImg_film())
                    .apply(new RequestOptions())
                    .into(imgMovie);
        }
    }
}
