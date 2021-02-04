package com.example.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {

    private int movie_id;
    private String title;
    private String poster_path;
    private String release_date;
    private float vote_average;
    private int runtime;

    @SerializedName("overview")
    private String movie_overview;

    public MovieModel(int movie_id, String title, String poster_path,
                      String release_date, float vote_average, String movie_overview, int runtime) {
        this.movie_id = movie_id;
        this.title= title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.movie_overview = movie_overview;
        this.runtime = runtime;
    }

    protected MovieModel(Parcel in) {
        movie_id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        vote_average = in.readFloat();
        movie_overview = in.readString();
        runtime = in.readInt();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movie_id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeFloat(vote_average);
        dest.writeString(movie_overview);
    }
}
