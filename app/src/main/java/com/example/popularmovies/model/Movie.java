package com.example.popularmovies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("serial")
@Entity(tableName = "favorites")
public class Movie implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String poster_path;
    private String overview;
    private String release_date;
    private String popularity;
    private String vote_average;

    public Movie(){
    }

    public Movie(String id, String title, String poster_path, String overview, String release_date, String popularity, String vote_average) {

        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.popularity = popularity;
        this.vote_average = vote_average;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public static final Comparator<Movie> BY_POPULARITY = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {
            return movie.popularity.compareTo(t1.popularity);
        }
    };

    public static final Comparator<Movie> BY_RATING = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {
            return movie.vote_average.compareTo(t1.vote_average);
        }
    };
}

/*
Use Parcelable instead of Serializable

I suggest using parcelable instead.

Parcelable process is much faster than Serializable. One of the reasons for this is that we are
being explicit about the serialization process instead of using reflection to infer it. It also
stands to reason that the code has been heavily optimized for this purpose.
 */
