package com.example.trabinerson.moviefiend;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a movie in theatres.
 */
public class Movie implements Parcelable {

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

    private Movie(Parcel parcel) {
        mName = parcel.readString();
        mRating = parcel.readDouble();
        mPosterUrl = parcel.readString();
        mDescription = parcel.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeDouble(this.mRating);
        dest.writeString(this.mPosterUrl);
        dest.writeString(this.mDescription);
    }
}
