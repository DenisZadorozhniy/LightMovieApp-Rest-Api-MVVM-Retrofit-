package com.example.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.reques.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageCount;

    public static  MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository(){
        //mMovies = new MutableLiveData<>();
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular() {
        return movieApiClient.getMoviesPopular();
    }

    public void searchMovieApi (String query, int pageNumber){
        mQuery = query;
        mPageCount = pageNumber;
        movieApiClient.SearchMoviesApi(query, pageNumber);
    }

    public void searchMoviePopular (int pageNumber){
        mPageCount = pageNumber;
        movieApiClient.SearchMoviesPopular(pageNumber);
    }

    public void searchNextPageMovie(){
        searchMovieApi(mQuery,mPageCount + 1);
    }




}
