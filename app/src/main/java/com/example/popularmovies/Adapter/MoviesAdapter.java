package com.example.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.DetailsActivity;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import static com.example.popularmovies.MainActivity.choice;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> data;
    private Context context;

    public MoviesAdapter(Context context,List<Movie> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final Movie movie = data.get(position);
        String posterURL = "http://image.tmdb.org/t/p/w342/" + movie.getPoster_path();

        holder.movieTitle.setText(movie.getTitle());
        if(choice == 1) {
            holder.movieRating.setText(movie.getVote_average());
        }else{
            holder.movieRating.setText(movie.getPopularity());
        }
        Picasso.get().load(posterURL).into(holder.moviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieDetails = new Intent(context, DetailsActivity.class);
                movieDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                movieDetails.putExtra("movie", movie);
                context.startActivity(movieDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null? data.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView movieTitle, movieRating;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieRating = itemView.findViewById(R.id.movieRating);
        }
    }
}