package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMovieListener onMovieListener;
    ImageView imageViewPopular;
    RatingBar ratingBarPopular;

    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener ) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageViewPopular = itemView.findViewById(R.id.img_movie_popular);
        ratingBarPopular = itemView.findViewById(R.id.rating_bar_popular);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
