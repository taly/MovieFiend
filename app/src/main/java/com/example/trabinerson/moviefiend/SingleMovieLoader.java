package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

/**
 * A loader that gets data for a single movie.
 */
public class SingleMovieLoader extends MoviesLoader {

    private int mMovieId;

    public SingleMovieLoader(Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Override
    protected Uri.Builder getBaseUriBuilder() {
        String movieId = Integer.toString(mMovieId);
        Uri.Builder builder = Uri.parse(MOVIE_API_BASE_URL).buildUpon()
                .appendPath(movieId);
        return builder;
    }

    @Override
    protected Movie[] getMoviesFromResponse(JSONObject response) {
        Movie movie = parseResponseMovie(response); // Response contains only 1 movie
        Movie[] ret = {movie};
        return ret;
    }
}
