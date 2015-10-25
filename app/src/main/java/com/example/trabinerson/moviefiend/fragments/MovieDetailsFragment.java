package com.example.trabinerson.moviefiend.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.MovieDetailsHolder;
import com.example.trabinerson.moviefiend.R;

/**
 * A fragment that contains a movie.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String ARG_KEY_MOVIE = "CurrentMovie";
    public static final String ARG_KEY_ANIMATE_RATING = "AnimateRating";

    private MovieDetailsHolder mMovieDetailsHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.movie_details, container, false);

        // Get movie
        Bundle args = getArguments();
        Movie movie = args.getParcelable(ARG_KEY_MOVIE);
        boolean animate = args.getBoolean(ARG_KEY_ANIMATE_RATING);

        // Set movie
        mMovieDetailsHolder = new MovieDetailsHolder(rootView);
        mMovieDetailsHolder.disableSimilarMovies();
        mMovieDetailsHolder.setMovie(movie, animate);

        return rootView;
    }
}
