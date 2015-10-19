package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.net.Uri;

/**
 * A loader for the initial list of movies.
 */
public class InTheatresMoviesLoader extends MoviesLoader {

    private static final String MOVIE_API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String NOW_PLAYING_ENDPOINT = "now_playing";

    public InTheatresMoviesLoader(Context context) {
        super(context);
    }

    @Override
    protected Uri.Builder getBaseUriBuilder() {
        Uri.Builder builder = Uri.parse(MOVIE_API_BASE_URL).buildUpon()
                .appendPath(NOW_PLAYING_ENDPOINT);
        return builder;
    }
}
