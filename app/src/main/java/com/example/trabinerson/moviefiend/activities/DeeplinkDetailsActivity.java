package com.example.trabinerson.moviefiend.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.loaders.SingleMovieLoader;

/**
 * An interim activity that loads details for a movie and starts the details activity.
 */
public class DeeplinkDetailsActivity
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie[]> {

    private final int LOADER_ID = 1;

    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_loading);

        Intent intent = getIntent();
        String data = intent.getDataString();

        // TODO add validations / defensive code
        String movieId = data.substring(data.lastIndexOf("/") + 1);
        mMovieId = Integer.parseInt(movieId);

        // Get movie
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new SingleMovieLoader(this, mMovieId);
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {

        Movie movie = data[0]; // SingleMovieLoader returns 1 movie

        // Start DetailsActivity
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsActivity.INTENT_KEY_MOVIE, movie);
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        // Our work here is done
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }
}
