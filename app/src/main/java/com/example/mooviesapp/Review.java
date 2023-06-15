package com.example.mooviesapp;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    private int id;

    @SerializedName("author")
    private String author;

    @SerializedName("type")
    private String type;

    @SerializedName("review")
    private String review;


    public Review(int id, String author, String type, String review) {
        this.id = id;
        this.author = author;
        this.type = type;
        this.review = review;
    }


    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getReview() {
        return review;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", review='" + review + '\'' +
                '}';
    }
}
