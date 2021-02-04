package com.example.movieapp.utils;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //("./")
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
      @Query("api_key") String key,
      @Query("query") String query,
      @Query("page") int page
    );
    //Поиск по id  https://api.themoviedb.org/3/movie/550?api_key=7414ea7f10c694e408abdd65b47eb59e  "/3/movie/{id_movie}?"
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
        @Path("movie_id") int movie_id,
        @Query("api_key") String api_key
    );

    //Для блока Популярные
    // https://api.themoviedb.org/3/movie/popular ?api_key=7414ea7f10c694e408abdd65b47eb59&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularFilms(
      @Query("api_key") String key,
      @Query("page") int page
    );
}
