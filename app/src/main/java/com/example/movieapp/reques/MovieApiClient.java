package com.example.movieapp.reques;

import android.net.Credentials;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.MovieAppExecutors;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Keys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    //Live Data
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    public int successful_answer = 200;

    // Global Runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //Live Data for popular block
    private MutableLiveData<List<MovieModel>> mMoviesPopular;

    // Global Runnable for popular block
    private RetrieveMoviesRunnablePopular retrieveMoviesRunnablePopular;


    public static MovieApiClient getInstance(){
        if (instance == null ){
            instance = new MovieApiClient();
        } return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPopular(){
        return mMoviesPopular;
    }

    public void SearchMoviesApi (String query, int pageNumber){

        if (retrieveMoviesRunnable !=null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = MovieAppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        MovieAppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 4000, TimeUnit.MILLISECONDS );
    }

    public void SearchMoviesPopular (int pageNumber){

        if (retrieveMoviesRunnablePopular !=null){
            retrieveMoviesRunnablePopular = null;
        }
        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePopular(pageNumber);

        final Future myHandlerPopular = MovieAppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePopular);

        MovieAppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS );
    }

    private class RetrieveMoviesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response<MovieSearchResponse> response = getMovies(query,pageNumber).execute();       //getMovies(query, pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code() == successful_answer){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getList_movies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else{
                    String errorMessage = response.body().toString();
                    Log.i("Tag","Error code" +errorMessage);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
         }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Service.getMovieApi().searchMovie(
                    Keys.API_KEY,
                    query,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.i("Tag","Cancelling search request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnablePopular implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePopular( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response<MovieSearchResponse> responsePopular = getPopular(pageNumber).execute();       //getMovies(query, pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (responsePopular.code() == successful_answer){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)responsePopular.body()).getList_movies());
                    if(pageNumber == 1){
                        mMoviesPopular.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }
                } else{
                    String errorMessage = responsePopular.errorBody().toString();
                    Log.i("Tag","Error code" +errorMessage);
                    mMoviesPopular.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getPopular(int pageNumber){
            return Service.getMovieApi().getPopularFilms(
                    Keys.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest(){
            Log.i("Tag","Cancelling search request");
            cancelRequest = true;
        }
    }

}

