package com.example.trabinerson.moviefiend;

/**
 * Represents a movie in theatres.
 */
public class Movie {

    public String mName;
    public double mRating;
    public String mPosterUrl;

    public Movie(String name, double rating, String posterUrl) {
        this.mName = name;
        this.mRating = rating;
        this.mPosterUrl = posterUrl;
    }
}
