package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.API.RetrofitObjectAPI;
import com.example.popularmovies.Adapter.ReviewAdapter;
import com.example.popularmovies.Adapter.TrailerAdapter;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.popularmovies.MainActivity.baseURL;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    public static String MOVIE_KEY;
    private MovieDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDatabase = MovieDatabase.getInstance(getApplicationContext());

        TextView movieTitle = findViewById(R.id.movieTitleDetails);
        TextView movieRelease = findViewById(R.id.movieRelease);
        ImageView moviePoster = findViewById(R.id.moviePosterDetails);
        TextView movieOverview = findViewById(R.id.movieOverview);
        TextView movieRating = findViewById(R.id.movieRatingDetails);
        TextView moviePopularity = findViewById(R.id.moviePopularity);
        final ImageButton favoriteBtn = findViewById(R.id.favoriteBtn);

        trailerRecyclerView = findViewById(R.id.trailerRecyclerView);
        trailerRecyclerView.setHasFixedSize(true);

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        reviewRecyclerView.setHasFixedSize(true);

        Intent movieDetails = getIntent();
        final Movie movie = (Movie) movieDetails.getSerializableExtra("movie");

        movieTitle.setText(movie.getTitle());
        movieRelease.setText(movie.getRelease_date());
        movieRating.setText(movie.getVote_average() + " / 10");
        moviePopularity.setText(movie.getPopularity());
        movieOverview.setText(movie.getOverview());
        Picasso.get().load("http://image.tmdb.org/t/p/w342/" + movie.getPoster_path()).into(moviePoster);
        MOVIE_KEY = movie.getId();

        if(mDatabase.movieDao().loadMovieById(movie.getId()) != null){
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite);
            favoriteBtn.setImageDrawable(img);
        }else{
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_unfavorite);
            favoriteBtn.setImageDrawable(img);
        }

        getTrailers();
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);

        getReviews();
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabase.movieDao().loadMovieById(movie.getId()) != null) {
                    mDatabase.movieDao().deleteMovie(movie);
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_unfavorite);
                    favoriteBtn.setImageDrawable(img);
                    Toast.makeText(DetailsActivity.this, "Movie removed from Favorites", Toast.LENGTH_SHORT).show();
                }else{
                    Movie favMovie = new Movie(movie.getId(), movie.getTitle(), movie.getPoster_path(),
                            movie.getOverview(), movie.getRelease_date(), movie.getPopularity(), movie.getVote_average());
                    mDatabase.movieDao().insertMovie(favMovie);
                    Toast.makeText(DetailsActivity.this, "Movie added as Favorite", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void getTrailers() {

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseURL + MOVIE_KEY + "/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);
        final Call<Trailer> trailerCall = service.getTrailerJson();

        trailerCall.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                assert response.body() != null;
                List<Trailer> trailers = response.body().getResultsTrailer();
                trailerRecyclerView.setAdapter(new TrailerAdapter(getApplicationContext(),trailers));
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Log.i("Info", t.getMessage());
            }
        });
    }

    private void getReviews() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL + MOVIE_KEY + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);
        final Call<Review> reviewCall = service.getReviewJson();

        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                assert response.body() != null;
                List<Review> reviews = response.body().getResultsReview();
                reviewRecyclerView.setAdapter(new ReviewAdapter(getApplicationContext(),reviews));
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.i("Info", t.getMessage());
            }
        });
    }

}