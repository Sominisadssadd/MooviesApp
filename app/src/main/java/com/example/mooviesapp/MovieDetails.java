package com.example.mooviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetails extends AppCompatActivity {


    private static final String args = "film_args";
    private TextView textViewName;
    private TextView textViewYear;
    private TextView textViewDescription;
    private ImageView imageViewPoster;
    private MovieDetailsViewModel viewModel;
    private RecyclerView recyclerViewTrailers;
    private ReviewAdapter reviewAdapter;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewReviews;
    private ImageView inFavoriteImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();
        initRecyclerView();

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(args);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
        textViewName.setText(movie.getName());
        textViewDescription.setText(movie.getDescription());
        textViewYear.setText(String.valueOf(movie.getYear()));

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });

        viewModel.loadReviews(movie.getId());
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

        reviewAdapter.setOnHalfReachListener(new ReviewAdapter.onHalfReachListener() {
            @Override
            public void onHalfReachListener() {
                viewModel.loadReviews(movie.getId());
            }
        });


        Drawable starOn = ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on);
        Drawable starOff = ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off);

        viewModel.getIsFavorite(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb == null) {
                    inFavoriteImageView.setImageDrawable(starOff); //на кнопку будет каждый раз устанавливаться разный слушатель нажатий, в зависимости от условий
                    inFavoriteImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.addNewFavoriteFilm(movie);

                        }
                    });
                } else {
                    inFavoriteImageView.setImageDrawable(starOn);
                    inFavoriteImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeFromFavorite(movie.getId());

                        }
                    });
                }
            }
        });

    }

    private void initRecyclerView() {
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailersAdapter);
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.onTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        reviewAdapter = new ReviewAdapter();
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setAdapter(reviewAdapter);


    }

    private void initViews() {
        inFavoriteImageView = findViewById(R.id.imageViewInFavorteButton);
        textViewName = findViewById(R.id.textViewNameOfFilm);
        textViewYear = findViewById(R.id.textViewYearOfFilm);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewPoster = findViewById(R.id.imageViewPosterOfFilmDetails);

    }

    public static Intent getNewIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetails.class);
        intent.putExtra(args, movie);
        return intent;
    }
}