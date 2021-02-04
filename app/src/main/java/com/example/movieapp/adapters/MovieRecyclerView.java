package com.example.movieapp.adapters;

import android.net.Credentials;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Keys;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POPULAR = 1;
    private static final int DISPLAY_SEARCH = 2;

    private Popular_View_Holder popular_view_holder;
    private  MovieModel movieModel;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    /*    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                parent, false);
        return new MovieViewHolder(view,onMovieListener); */
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false);
            return new MovieViewHolder(view,onMovieListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_list_item,
                    parent, false);
            return new Popular_View_Holder(view,onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == DISPLAY_SEARCH){
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

            //Под ImageView использую Glide Library
            Glide.with(holder.itemView.getContext())
                    .load( "https://image.tmdb.org/t/p/w500/"+ mMovies.get(position).getPoster_path())
                    .into(((MovieViewHolder)holder).imageView);
        } else {
            ((Popular_View_Holder)holder).ratingBarPopular.setRating((mMovies.get(position).getVote_average())/2);

            //Под ImageView использую Glide Library
            Glide.with(holder.itemView.getContext())
                    .load( "https://image.tmdb.org/t/p/w500/"+ mMovies.get(position).getPoster_path())
                    .into(((Popular_View_Holder)holder).imageViewPopular);

        }



   /*     ((MovieViewHolder)holder).title.setText(String.valueOf(mMovies.get(position).getTitle()));

       ((MovieViewHolder)holder).release_date.setText(mMovies.get(position).getRelease_date());
       ((MovieViewHolder)holder).duration.setText("" +mMovies.get(position).getRuntime());
       ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

        //Под ImageView использую Glide Library
        Glide.with(holder.itemView.getContext())
                .load( "https://image.tmdb.org/t/p/w500/"+ mMovies.get(position).getPoster_path())
                .into(((MovieViewHolder)holder).imageView);   */
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    // Для получения id фильма на который нажали
    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if (mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position){
        if (Keys.POPULAR){
            return DISPLAY_POPULAR;
        } else return DISPLAY_SEARCH;
    }

}
