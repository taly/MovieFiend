package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.net.Uri;

/**
 * A loader for the list of similar movies of a given movie.
 */
public class SimilarMoviesLoader extends MoviesLoader {

    private static final String SIMILAR_MOVIES_ENDPOINT = "similar";

    private int mMovieId;

    public SimilarMoviesLoader(Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Override
    protected Uri.Builder getBaseUriBuilder() {
        String movieId = Integer.toString(mMovieId);
        Uri.Builder builder = Uri.parse(MOVIE_API_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(SIMILAR_MOVIES_ENDPOINT);
        return builder;
    }
}
