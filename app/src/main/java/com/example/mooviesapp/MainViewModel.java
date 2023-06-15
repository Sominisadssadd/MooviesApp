package com.example.mooviesapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {


    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private CompositeDisposable setOfDisposables = new CompositeDisposable();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    //Viewmodel переживает переворот, поэтому page будет равняться тому, чему равнялась
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadFilms();
    }







    public void loadFilms() {
        Boolean loading = isLoading.getValue();
        if (Boolean.TRUE.equals(loading)) {
            return;
        }

        Disposable filmsDisposable = ApiFactory.getApi.getMovie(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    isLoading.setValue(true);
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieResponse>() {
                               @Override
                               public void accept(MovieResponse movieResponse) throws Throwable {
                                   List<Movie> movieList = movies.getValue();
                                   if (movieList != null) {
                                       movieList.addAll(movieResponse.getMovies());
                                       movies.setValue(movieList);
                                   } else {
                                       movies.setValue(movieResponse.getMovies());
                                   }
                                   page++;
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        });

        setOfDisposables.add(filmsDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        setOfDisposables.dispose();
    }
}
