package com.example.trabinerson.moviefiend;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * The activity that shows movie details.
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie[]> {

    public static final String INTENT_KEY_MOVIE = "Movie";
    private static final int LOADER_ID = 2;

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Get movie
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(INTENT_KEY_MOVIE);
        mMovieId = movie.getId();
        Log.i(LOG_TAG, "Unbundled " + movie.getName());

        // Get views
        View rootView = findViewById(R.id.details_root);
        NetworkImageView posterView = (NetworkImageView) rootView.findViewById(R.id.imageview_details_poster);
        TextView nameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
        TextView ratingView = (TextView) rootView.findViewById(R.id.textview_movie_rating);
        TextView descriptionView = (TextView) rootView.findViewById(R.id.textview_movie_description);

        // Set data
        ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
        nameView.setText(movie.getName());
        String rating = getString(R.string.rating, movie.getRating());
        ratingView.setText(rating);
        posterView.setImageUrl(movie.getPosterUrl(), imageLoader);
        descriptionView.setText(movie.getDescription());

        // Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new SimilarMoviesLoader(this, mMovieId);
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        Log.i(LOG_TAG, "Got " + data.length + " similar movies");
        // TODO
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        // TODO (?)
    }
}
