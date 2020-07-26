package com.example.popularmovies.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public ViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        movies = movieDatabase.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
