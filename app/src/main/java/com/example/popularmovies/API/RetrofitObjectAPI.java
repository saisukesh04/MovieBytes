package com.example.popularmovies.API;

import com.example.popularmovies.model.Results;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitObjectAPI {

    @GET("top_rated?api_key=c41221690b478c3d63b47a900a44eb68")
    Call<Results> getTopRatedJson();

    @GET("popular?api_key=c41221690b478c3d63b47a900a44eb68")
    Call<Results> getPopularJson();

    @GET("videos?api_key=c41221690b478c3d63b47a900a44eb68")
    Call<Trailer> getTrailerJson();

    @GET("reviews?api_key=c41221690b478c3d63b47a900a44eb68")
    Call<Review> getReviewJson();
}
