package com.example.trabinerson.moviefiend.activities;

import android.app.LoaderManager;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

        String movieId = Uri.parse(data).getLastPathSegment();
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

        if (data == null || data.length == 0) {
            Toast.makeText(this, "No movie found with ID " + mMovieId, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Movie movie = data[0]; // SingleMovieLoader returns 1 movie

            // Create DetailsActivity
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailsActivity.INTENT_KEY_MOVIE, movie);
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtras(bundle);

            // Add parent to backstack and start
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(intent)
            .startActivities();
        }

        // Our work here is done
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }
}
