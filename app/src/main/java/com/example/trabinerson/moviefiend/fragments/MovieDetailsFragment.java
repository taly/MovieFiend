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
import com.example.trabinerson.moviefiend.loaders.SingleMovieLoader;

/**
 * A fragment that contains a movie.
 */
public class MovieDetailsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Movie[]> {

    public interface MovieDetailsCallbacks {
        void onSimilarMoviesClicked(Movie[] similarMovies);
    }

    public static final String ARG_KEY_MOVIE_ID = "MovieId";
    public static final String ARG_KEY_MOVIE = "CurrentMovie";
    public static final String ARG_KEY_SHOW_SIMILAR = "ShowSimilarMovies";
    public static final String ARG_KEY_ANIMATE_RATING = "AnimateRating";
    public static final String FRAGMENT_FLAG = "MovieDetails";

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();
    private static final String INSTANCE_KEY_ANIMATE_RATING = "AnimateRating";
    private static final int SINGLE_MOVIE_LOADER_ID = 1;
    private static final int SIMILAR_MOVIES_LOADER_ID = 2;

    private int mMovieId;
    private Movie[] mSimilarMovies;
    private MovieDetailsHolder mMovieDetailsHolder;
    private MovieDetailsCallbacks mCallback;
    private boolean mAnimateRating;
    private boolean mShowSimilar;

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

        // Get general args
        Bundle args = getArguments();
        mAnimateRating = args.getBoolean(ARG_KEY_ANIMATE_RATING, true);
        if (mAnimateRating && savedInstanceState != null) {
            mAnimateRating = savedInstanceState.getBoolean(INSTANCE_KEY_ANIMATE_RATING, true);
        }
        mShowSimilar = args.getBoolean(ARG_KEY_SHOW_SIMILAR, true);

        // Init similar movies button
        initSimilarMovies(rootView);
        mMovieDetailsHolder = new MovieDetailsHolder(rootView);

        // Get (or load) movie
        mMovieId = args.getInt(ARG_KEY_MOVIE_ID);
        Movie movie = args.getParcelable(ARG_KEY_MOVIE);
        if (movie == null) {
            getLoaderManager().initLoader(SINGLE_MOVIE_LOADER_ID, null, this);
        } else {
            setMovie(movie);
            Log.i(LOG_TAG, "Unbundled " + movie.getName());
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INSTANCE_KEY_ANIMATE_RATING, false);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        if (id == SINGLE_MOVIE_LOADER_ID) {
            return new SingleMovieLoader(getActivity(), mMovieId);
        } else {
            return new SimilarMoviesLoader(getActivity(), mMovieId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        int id = loader.getId();
        if (id == SINGLE_MOVIE_LOADER_ID) {
            Movie movie = data[0]; // Returns only 1 movie
            setMovie(movie);
        } else {
            Log.i(LOG_TAG, "Got " + data.length + " similar movies");
            mSimilarMovies = data;
            if (mShowSimilar) {
                mMovieDetailsHolder.finishLoading();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) { }

    private void setMovie(Movie movie) {
        if (mShowSimilar) {
            getLoaderManager().initLoader(SIMILAR_MOVIES_LOADER_ID, null, this);
        } else {
            mMovieDetailsHolder.disableSimilarMovies();
        }
        mMovieDetailsHolder.setMovie(movie, mAnimateRating);
    }

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
