package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.API.RetrofitObjectAPI;
import com.example.popularmovies.Adapter.MoviesAdapter;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Results;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView posterGridView;
    public static int choice;
    private ProgressDialog loadingDialog;
    public static final String baseURL = "https://api.themoviedb.org/3/movie/";
    private MovieDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int spanCount;
        choice = 1;
        loadingDialog = new ProgressDialog(this);

        posterGridView = findViewById(R.id.posterGridView);
        posterGridView.setHasFixedSize(true);

        executeTask(choice);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }else{
            spanCount = 2;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),spanCount);
        posterGridView.setLayoutManager(gridLayoutManager);

        mDatabase = MovieDatabase.getInstance(getApplicationContext());
    }

    private void executeTask(final int integer) {

        loadingDialog.setMessage("\tLoading...");
        loadingDialog.setCancelable(true);
        loadingDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);
        Call<Results> movieCall;

        if(integer == 1) {
            movieCall = service.getTopRatedJson();
        }else{
            movieCall = service.getPopularJson();
        }
        movieCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                assert response.body() != null;
                List<Movie> movies = response.body().getResults();
                if (integer == 2) {
                    Collections.sort(movies, Collections.reverseOrder(Movie.BY_POPULARITY));
                } else {
                    Collections.sort(movies, Collections.reverseOrder(Movie.BY_RATING));
                }
                posterGridView.setAdapter(new MoviesAdapter(getApplicationContext(),movies));
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.i("info: Error: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.sortBy_popularity:
                choice = 2;
                executeTask(choice);
                break;
            case R.id.sortBy_rating:
                choice = 1;
                executeTask(choice);
                break;
            case R.id.sortBy_favorites:
                choice = 3;
                getFavorites();
                break;
        }
        return true;
    }

    private void getFavorites() {
        posterGridView.setAdapter(new MoviesAdapter(getApplicationContext(),mDatabase.movieDao().loadAllMovies()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (choice == 1 || choice == 2) {
        }else {
            List<Movie> movies = mDatabase.movieDao().loadAllMovies();
            posterGridView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
        }
    }
}
