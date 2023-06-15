package com.example.mooviesapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private onReachEndListener reachEndListener;
    private onClickListener onClickListener;

    public void setReachEndListener(onReachEndListener reachEndListener) {
        this.reachEndListener = reachEndListener;

    }

    public void setOnClickListener(onClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);
        Glide.with(holder.itemView)
                .load(currentMovie.getPoster().getUrl())
                .into(holder.imageViewPoster);

        Double rating = currentMovie.getRating().getKp();
        int background_id;
        if (rating > 7) {
            background_id = R.drawable.rating_background_green;
        } else if (rating > 5) {
            background_id = R.drawable.rating_background_yellow;
        } else {
            background_id = R.drawable.rating_background_red;
        }
        //по id получаем наш фон в виде drawable
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), background_id);

        holder.textViewRating.setBackground(background);
        holder.textViewRating.setText(String.format("%.1f", rating));
        if (position == movies.size() - 10) {
            reachEndListener.reachEnd();
        }

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onClickListener.clickListener(currentMovie);
          }
      });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    interface onReachEndListener {
        void reachEnd();
    }

    interface onClickListener {
        void clickListener(Movie movie);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewPoster;
        public TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPosterOfFilm);
            textViewRating = itemView.findViewById(R.id.textViewRationOfFilm);

        }
    }
}
