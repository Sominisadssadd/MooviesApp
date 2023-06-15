package com.example.mooviesapp;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class DataBaseFavoriteMovies extends RoomDatabase {

    private static DataBaseFavoriteMovies instance = null;
    private static final String DATABASE_NAME = "movieDataBase";

    public static DataBaseFavoriteMovies getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    DataBaseFavoriteMovies.class,
                    DATABASE_NAME
            ).build();
        }

        return instance;
    }

    public abstract FavoriteMoviesDao favoriteDao();


}
