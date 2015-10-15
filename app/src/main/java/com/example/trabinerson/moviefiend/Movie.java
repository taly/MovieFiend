package com.example.trabinerson.moviefiend;

import java.io.Serializable;

/**
 * Represents a movie in theatres.
 */
public class Movie implements Serializable {

    public String mName;
    public double mRating;
    public String mPosterUrl;
    public String mDescription;

    public Movie(String name, double rating, String posterUrl, String description) {
        this.mName = name;
        this.mRating = rating;
        this.mPosterUrl = posterUrl;
        this.mDescription = description;
    }
}
