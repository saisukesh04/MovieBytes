package com.example.popularmovies.API;

import com.example.popularmovies.model.Results;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitObjectAPI {

    @GET("top_rated?api_key=<INSERT-API_KEY>")
    Call<Results> getTopRatedJson();

    @GET("popular?api_key=<INSERT-API_KEY>")
    Call<Results> getPopularJson();

    @GET("videos?api_key=<INSERT-API_KEY>")
    Call<Trailer> getTrailerJson();

    @GET("reviews?api_key=<INSERT-API_KEY>")
    Call<Review> getReviewJson();
}
