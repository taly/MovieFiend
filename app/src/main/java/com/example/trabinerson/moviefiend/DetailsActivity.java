package com.example.trabinerson.moviefiend;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * The activity that shows movie details.
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie[]> {

    public static final String INTENT_KEY_MOVIE = "Movie";
    private static final int LOADER_ID = 2;

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    private static final String INSTANCE_KEY_ANIMATE_RATING = "AnimateRating";

    private int mMovieId;
    private Movie[] mSimilarMovies;
    private MovieDetailsHolder mMovieDetailsHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Get movie
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(INTENT_KEY_MOVIE);
        mMovieId = movie.getId();
        Log.i(LOG_TAG, "Unbundled " + movie.getName());

        // Set movie on screen
        boolean animate = true;
        if (savedInstanceState != null) {
            animate = savedInstanceState.getBoolean(INSTANCE_KEY_ANIMATE_RATING, true);
        }
        View rootView = findViewById(R.id.details_root);
        mMovieDetailsHolder = new MovieDetailsHolder(rootView);
        mMovieDetailsHolder.setMovie(movie, animate);

        // Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INSTANCE_KEY_ANIMATE_RATING, false);
    }

    public void onSimilarMoviesClicked(View view) {
        Bundle bundle = new Bundle();
        int numMovies = Math.min(mSimilarMovies.length, SimilarMoviesPagerActivity.NUM_MOVIES);
        Movie[] movieSubset = Arrays.copyOfRange(mSimilarMovies, 0, numMovies);
        bundle.putParcelableArray(SimilarMoviesPagerActivity.INTENT_KEY_SIMILAR_MOVIES, movieSubset);
        Intent intent = new Intent(this, SimilarMoviesPagerActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new SimilarMoviesLoader(this, mMovieId);
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        Log.i(LOG_TAG, "Got " + data.length + " similar movies");
        mSimilarMovies = data;
        mMovieDetailsHolder.finishLoading();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        // TODO (?)
    }
}
