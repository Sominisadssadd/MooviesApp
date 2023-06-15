package com.example.mooviesapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends AndroidViewModel {

    private int page = 1;

    private MutableLiveData<List<Trailer>> trailer = new MutableLiveData<>();

    public LiveData<List<Trailer>> getTrailers() {
        return trailer;
    }


    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    private MutableLiveData<List<Review>> review = new MutableLiveData<>();

    public LiveData<List<Review>> getReviews() {
        return review;
    }


    private CompositeDisposable setOfDisposable = new CompositeDisposable();

    private final FavoriteMoviesDao movieDao;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieDao = DataBaseFavoriteMovies.getInstance(application).favoriteDao();
    }


    public LiveData<List<Movie>> listOfFavorite() {
        return movieDao.getAllFavoriteFilms();
    }

    public LiveData<Movie> getIsFavorite(int idMovie) {
        return movieDao.getFavoriteFilm(idMovie);
    }


    public void addNewFavoriteFilm(Movie movie) {
        Disposable disposable = movieDao.insertNewFavoriteFilm(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        setOfDisposable.add(disposable);
    }

    public void removeFromFavorite(int idMovie) {
        Disposable disposable = movieDao.deleteFavoriteMovieFromFavorite(idMovie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        setOfDisposable.add(disposable);
    }

    public void loadTrailers(int idFilms) {
        Disposable disposable = ApiFactory.getApi.getTrailers(idFilms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Videos, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(Videos videos) throws Throwable {
                        return videos.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Throwable {
                        trailer.setValue(trailers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("MAINACTIVITY", throwable.toString());
                    }
                });

        setOfDisposable.add(disposable);

    }


    public void loadReviews(int idFilms) {
        Boolean Loading = isLoading.getValue();
        if (Boolean.TRUE.equals(Loading)) {
            {
                return;
            }

        }
        Disposable disposable = ApiFactory.getApi.getReviews(idFilms, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .doOnSubscribe(disposable1 -> {
                    isLoading.setValue(true);
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                               @Override
                               public void accept(List<Review> reviews) throws Throwable {
                                   List<Review> reviewList = review.getValue();
                                   if (reviewList != null) {
                                       reviewList.addAll(reviews);
                                       review.setValue(reviewList);

                                   } else {
                                       review.setValue(reviews);
                                   }
                                   page++;

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d("MAINACTIVITY", throwable.toString());
                            }
                        });

        setOfDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        setOfDisposable.dispose();
    }
}
