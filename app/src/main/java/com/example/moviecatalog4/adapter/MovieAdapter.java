package com.example.moviecatalog4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalog4.Movie;
import com.example.moviecatalog4.R;


import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();

    public MovieAdapter() {

    }

    public void setData(ArrayList<Movie> items){
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
    }


    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private OnItemClickCallback onItemClickCallback;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(movies.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(movies.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaM,descM,RilisM;
        private ImageView img_poster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaM = itemView.findViewById(R.id.txt_name);
            descM = itemView.findViewById(R.id.txt_desc);
            RilisM = itemView.findViewById(R.id.txt_rilis);
            img_poster = itemView.findViewById(R.id.img_photo);
        }
        void bind(Movie movie){
            namaM.setText(movie.getName());
            descM.setText(movie.getDescription());
            RilisM.setText(movie.getDate_rilis());
            Glide.with(itemView.getContext())
                    .load(movie.getPhoto())
                    .apply(new RequestOptions())
                    .into(img_poster);
        }
    }

    public interface OnItemClickCallback {
        void  onItemClicked(Movie movie);
    }
}


