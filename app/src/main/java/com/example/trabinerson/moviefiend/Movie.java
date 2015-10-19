package com.example.trabinerson.moviefiend;

/**
 * Represents a movie in theatres.
 */
public class Movie {

    private String mName;
    private double mRating;
    private String mPosterUrl;

    public Movie(String name, double rating, String posterUrl) {
        this.mName = name;
        this.mRating = rating;
        this.mPosterUrl = posterUrl;

    }
    public String getName() {
        return this.mName;
    }

    public double getRating() {
        return this.mRating;
    }

    public String getPosterUrl() {
        return this.mPosterUrl;
    }
}
