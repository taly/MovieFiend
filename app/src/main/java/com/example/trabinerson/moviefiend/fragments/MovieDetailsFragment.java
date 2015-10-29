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
import com.example.trabinerson.moviefiend.views.MovieDetailsView;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.loaders.SimilarMoviesLoader;

/**
 * A fragment that contains a movie.
 */
public class MovieDetailsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Movie[]> {

    public interface Callbacks {
        void onSimilarMoviesClicked(Movie[] similarMovies);
    }

    public static final String FRAGMENT_FLAG = "MovieDetails";

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();
    private static final String INSTANCE_KEY_ANIMATE_RATING = "AnimateRating";
    private static final String ARG_KEY_MOVIE = "CurrentMovie";
    private static final String ARG_KEY_SHOW_SIMILAR = "ShowSimilarMovies";
    private static final String ARG_KEY_ANIMATE_RATING = "AnimateRating";
    private static final int LOADER_ID = 1;

    private int mMovieId;
    private Movie[] mSimilarMovies;
    private MovieDetailsView mMovieDetailsView;
    private Callbacks mCallback;
    private boolean mAnimateRating;
    private boolean mShowSimilar;

    public static MovieDetailsFragment createFragment(
            Movie movie, boolean animateRating, boolean showSimilarMovies) {

        // Create bundle
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_KEY_MOVIE, movie);
        bundle.putBoolean(ARG_KEY_ANIMATE_RATING, animateRating);
        bundle.putBoolean(ARG_KEY_SHOW_SIMILAR, showSimilarMovies);

        // Create fragment
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mShowSimilar) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callbacks) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.movie_details_view, container, false);

        // Get general args
        Bundle args = getArguments();
        mAnimateRating = args.getBoolean(ARG_KEY_ANIMATE_RATING, true);
        if (mAnimateRating && savedInstanceState != null) {
            mAnimateRating = savedInstanceState.getBoolean(INSTANCE_KEY_ANIMATE_RATING, true);
        }
        mShowSimilar = args.getBoolean(ARG_KEY_SHOW_SIMILAR, true);

        // Initializations
        mMovieDetailsView = (MovieDetailsView) rootView.findViewById(R.id.movie_details_view);
        initSimilarMovies(rootView);

        // Get movie
        Movie movie = args.getParcelable(ARG_KEY_MOVIE);
        mMovieId = movie.getId();
        setMovie(movie);
        Log.i(LOG_TAG, "Unbundled " + movie.getName());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Don't animate rating on screen rotate
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
        if (mShowSimilar) {
            mMovieDetailsView.finishLoading();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) { }

    private void setMovie(Movie movie) {
        if (!mShowSimilar) {
            mMovieDetailsView.disableSimilarMovies();
        }
        mMovieDetailsView.setMovie(movie, mAnimateRating);
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
