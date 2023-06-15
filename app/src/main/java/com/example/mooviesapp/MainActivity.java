package com.example.mooviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private static final String TAG = "MAINACTIVITY";
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerViewMovie;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        progressBar = findViewById(R.id.progressBarMovie);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite_screen) {
            Intent intentFavoriteScreen = FavoriteActivity.newIntent(this);
            startActivity(intentFavoriteScreen);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerViewMovie = findViewById(R.id.recyclerViewMovies);
        movieAdapter = new MovieAdapter();
        recyclerViewMovie.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMovie.setAdapter(movieAdapter);
        movieAdapter.setReachEndListener(new MovieAdapter.onReachEndListener() {
            @Override
            public void reachEnd() {
                viewModel.loadFilms();
            }
        });
        movieAdapter.setOnClickListener(new MovieAdapter.onClickListener() {
            @Override
            public void clickListener(Movie movie) {
                Intent intent = MovieDetails.getNewIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });
    }
}