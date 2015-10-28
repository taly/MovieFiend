package com.example.trabinerson.moviefiend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment that contains a movie.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String ARG_KEY_MOVIE = "CurrentMovie";

    private MovieDetailsHolder mMovieDetailsHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.movie_details, container, false);

        // Get movie
        Bundle args = getArguments();
        Movie movie = args.getParcelable(ARG_KEY_MOVIE);

        // Set movie
        mMovieDetailsHolder = new MovieDetailsHolder(rootView);
        mMovieDetailsHolder.disableSimilarMovies();
        mMovieDetailsHolder.setMovie(movie, false);

        return rootView;
    }
}
