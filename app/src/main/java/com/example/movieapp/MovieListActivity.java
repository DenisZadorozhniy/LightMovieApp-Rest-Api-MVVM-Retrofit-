package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.ListViewModel;

import java.util.List;



public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView adapterMovieRecyclerView;
    private TextView title_popular;
    public int successful_answer = 200;

    private ListViewModel listViewModel;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Подключаем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Делаем Поиск
        SearchView();

        //  Анимация
        title_popular = (TextView) findViewById(R.id.popular_title);
        Animation heedAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        title_popular.startAnimation(heedAnimation);

        // Подключаем RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ConfigurateRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();

        listViewModel.searchMoviePopular(1);
    //   searchMovieApi("fast",1);

    }

    private void ObservePopularMovies() {
        listViewModel.getPopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for (MovieModel movieModel : movieModels){
                        Log.v("Tag","Change" + movieModel.getTitle());
                        adapterMovieRecyclerView.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    // Наблюдаем за изменениями в БД
    private void ObserveAnyChange(){
        listViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for (MovieModel movieModel : movieModels){
                        Log.v("Tag","Change" + movieModel.getTitle());
                        adapterMovieRecyclerView.setmMovies(movieModels);
                    }
                }
            }
        });
    }

  /*  private void searchMovieApi(String query, int pageNumber){
        listViewModel.searchMovieRepository(query, pageNumber);
    }*/

    private void ConfigurateRecyclerView(){
        adapterMovieRecyclerView = new MovieRecyclerView(this);
        recyclerView.setAdapter(adapterMovieRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        // Загрузка след. страницы с фильмами
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
                    listViewModel.searchNextPageMovie();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie",adapterMovieRecyclerView.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SearchView(){
        final SearchView searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              listViewModel.searchMovieRepository(
                        query,
                       1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });

    }


/*
    private void GetRetrofitReponse() {
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Keys.API_KEY,
                        "Action",
                        1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                Log.v("Tag","the response" +response.body().toString());
                if(response.code() == successful_answer){
                    Log.i("Tag","the response" +response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getList_movies());
                    for (MovieModel movie: movies){
                        Log.i("Tag","The released date" +movie.getRelease_date() );
                    }
                } else {
                    try {
                        Log.i("Tag","Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });


    }

    private void  GetRetrofitReponseID(){
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieModel> responseCall = movieApi.getMovie(
       550,
                Keys.API_KEY);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == successful_answer){
                    MovieModel movie = response.body();
                    Log.v("Tag", "The response is " +movie.getTitle() );
                    Log.v("Tag", "The response is " +movie.getRelease_date() );

                } else {
                    try {
                        Log.i("Tag", "Error" +response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

 */
}