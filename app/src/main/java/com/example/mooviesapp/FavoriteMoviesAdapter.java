package com.example.mooviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteViewHolder> {


    private List<Movie> movies = new ArrayList<>();

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    private OnFavoriteItemClickListener onFavoriteItemClickListener;
    public void setOnFavoriteItemClickListener(OnFavoriteItemClickListener clickListener){
        this.onFavoriteItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        Movie currentMovie = movies.get(position);
        Glide.with(holder.itemView)
                .load(currentMovie.getPoster().getUrl()) //getPoster возвращает обьект, а не значение поля
                .into(holder.imageViewPosterOfFavorite);


        holder.itemView.setOnClickListener(listener -> {
            onFavoriteItemClickListener.setOnFavoriteClickListener(currentMovie);
        });
    }

    interface OnFavoriteItemClickListener {
        void setOnFavoriteClickListener(Movie movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewPosterOfFavorite;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPosterOfFavorite = itemView.findViewById(R.id.imageViewPosterOfFilm);
        }
    }
}
