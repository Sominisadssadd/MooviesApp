package com.example.mooviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorite;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;
    private MovieDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        initRecyclerView();

        viewModel.listOfFavorite().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favoriteMoviesAdapter.setMovies(movies);
            }
        });

    }


    private void initRecyclerView() {
        recyclerViewFavorite = findViewById(R.id.recyclerViewFavoriteMovies);
        favoriteMoviesAdapter = new FavoriteMoviesAdapter();
        recyclerViewFavorite.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewFavorite.setAdapter(favoriteMoviesAdapter);
        favoriteMoviesAdapter.setOnFavoriteItemClickListener(new FavoriteMoviesAdapter.OnFavoriteItemClickListener() {
            @Override
            public void setOnFavoriteClickListener(Movie movie) {
                MovieDetails.getNewIntent(FavoriteActivity.this,movie);
            }
        });




    }

    public static Intent newIntent(Context context){
        return new Intent(context,FavoriteActivity.class);
    }
}