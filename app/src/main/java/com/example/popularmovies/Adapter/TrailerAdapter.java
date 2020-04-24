package com.example.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<Trailer> data;
    private Context context;

    public TrailerAdapter(Context context,List<Trailer> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item_card,parent,false);
        return new TrailerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
        final Trailer trailer = data.get(position);
        String thumbnailUrl = "https://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg";
        final String trailerId = trailer.getKey();
        Picasso.get().load(thumbnailUrl).into(holder.trailerPoster);
        holder.trailerPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Opening Youtube",Toast.LENGTH_SHORT).show();
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerId));
                youtubeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(youtubeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  data != null? data.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView trailerPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerPoster = itemView.findViewById(R.id.trailerPoster);
        }
    }
}
