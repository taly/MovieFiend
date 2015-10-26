package com.example.trabinerson.moviefiend.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.MovieDetailsHolder;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.loaders.SimilarMoviesLoader;

/**
 * A fragment that contains a movie.
 */
public class MovieDetailsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Movie[]> {

    public interface MovieDetailsCallbacks {
        void onSimilarMoviesClicked(Movie[] similarMovies);
    }

    public static final String ARG_KEY_MOVIE = "CurrentMovie";
    public static final String ARG_KEY_SHOW_SIMILAR = "ShowSimilarMovies";
    public static final String ARG_KEY_ANIMATE_RATING = "AnimateRating";
    public static final String FRAGMENT_FLAG = "MovieDetails";

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();
    private static final String INSTANCE_KEY_ANIMATE_RATING = "AnimateRating";
    private static final int LOADER_ID = 1;

    private int mMovieId;
    private Movie[] mSimilarMovies;
    private MovieDetailsHolder mMovieDetailsHolder;
    private MovieDetailsCallbacks mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MovieDetailsCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MovieDetailsCallbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.movie_details, container, false);

        // Get movie
        Bundle args = getArguments();
        Movie movie = args.getParcelable(ARG_KEY_MOVIE);
        mMovieId = movie.getId();
        Log.i(LOG_TAG, "Unbundled " + movie.getName());

        // Get other args
        boolean animate = args.getBoolean(ARG_KEY_ANIMATE_RATING, true);
        if (animate && savedInstanceState != null) {
            animate = savedInstanceState.getBoolean(INSTANCE_KEY_ANIMATE_RATING, true);
        }
        boolean showSimilar = args.getBoolean(ARG_KEY_SHOW_SIMILAR, true);

        // Init similar movies button
        initSimilarMovies(rootView);

        // Set movie
        mMovieDetailsHolder = new MovieDetailsHolder(rootView);
        if (!showSimilar) {
            mMovieDetailsHolder.disableSimilarMovies();
        }
        mMovieDetailsHolder.setMovie(movie, animate);

        // Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INSTANCE_KEY_ANIMATE_RATING, false);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new SimilarMoviesLoader(getActivity(), mMovieId);
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        Log.i(LOG_TAG, "Got " + data.length + " similar movies");
        mSimilarMovies = data;
        mMovieDetailsHolder.finishLoading();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) { }

    private void initSimilarMovies(View rootView) {
        TextView similarMoviesView = (TextView) rootView.findViewById(R.id.textview_similar_movies);
        similarMoviesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSimilarMoviesClicked(mSimilarMovies);
            }
        });
    }

}
