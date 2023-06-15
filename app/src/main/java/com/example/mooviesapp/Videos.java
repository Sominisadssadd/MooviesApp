package com.example.mooviesapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Videos implements Serializable {

    @SerializedName("videos")
    private TrailersList trailersList;

    public Videos(TrailersList trailersList){
        this.trailersList = trailersList;
    }

    public TrailersList getTrailersList() {
        return trailersList;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "trailersList=" + trailersList +
                '}';
    }
}
