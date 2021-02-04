package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    RatingBar ratingBar;

    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        imageView = itemView.findViewById(R.id.img_movie);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
