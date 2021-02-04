package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.repositories.MovieRepository;

import java.util.List;

public class ListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public ListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies (){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular (){
        return movieRepository.getPopular();
    }

    public void searchMovieRepository (String query, int pageNumber) {
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchMoviePopular (int pageNumber) {
        movieRepository.searchMoviePopular(pageNumber);
    }

    public void searchNextPageMovie(){
        movieRepository.searchNextPageMovie();
    }
}
