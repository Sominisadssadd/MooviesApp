package com.example.mooviesapp;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie?token=YG6VWAJ-M18M254-P5KF2A4-5VQGA9N&field=rating.kp&search=7-10&limit=20")
    Single<MovieResponse> getMovie(@Query("page") int page);
    //в качестве парамертра у нас query params и будет получена другая страница, если другое число
    //page будет вставленно в самый конец

    //movie - это endpoint ) просто знай
    @GET("movie/{idFilms}?token=YG6VWAJ-M18M254-P5KF2A4-5VQGA9N")
    Single<Videos> getTrailers(@Path("idFilms") int idFilms); //добавит вместо {idFilms} id фильма


    @GET("https://api.kinopoisk.dev/v1/review?token=YG6VWAJ-M18M254-P5KF2A4-5VQGA9N&field=movieId")
    Single<ReviewResponse> getReviews(@Query("search") int idFilm, @Query("page") int page);
}
