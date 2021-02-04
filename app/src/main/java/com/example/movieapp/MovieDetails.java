package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.models.MovieModel;

public class MovieDetails extends AppCompatActivity {
    private ImageView imageView;
    private TextView title;
    private TextView decription;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageView = (ImageView) findViewById(R.id.main_imageView_details);
        title = (TextView) findViewById(R.id.title_details);
        decription = (TextView) findViewById(R.id.description_details);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar_details);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            title.setText(movieModel.getTitle());
            decription.setText(movieModel.getMovie_overview());
            ratingBar.setRating((movieModel.getVote_average())/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" +movieModel.getPoster_path())
                    .into(imageView);
        }
    }
}