package com.example.mooviesapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

@Dao
public interface FavoriteMoviesDao {

    @Query("select * from favorite_movies")
    LiveData<List<Movie>> getAllFavoriteFilms();

    @Query("select * from favorite_movies where id = :idMovie")
    LiveData<Movie> getFavoriteFilm(int idMovie);

    @Insert
    Completable insertNewFavoriteFilm(Movie favoriteMovie);

    @Query("delete from favorite_movies where id = :idMovie")
    Completable deleteFavoriteMovieFromFavorite(int idMovie);

}
