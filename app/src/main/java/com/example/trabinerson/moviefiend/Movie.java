package com.example.trabinerson.moviefiend;

import java.io.Serializable;

/**
 * Represents a movie in theatres.
 */
public class Movie implements Serializable {

    private String mName;
    private double mRating;
    private String mPosterUrl;
    private String mDescription;

    public Movie(String name, double rating, String posterUrl, String description) {
        this.mName = name;
        this.mRating = rating;
        this.mPosterUrl = posterUrl;
        this.mDescription = description;
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

    public String getDescription() {
        return this.mDescription;
    }
}
