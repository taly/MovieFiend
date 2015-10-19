package com.example.trabinerson.moviefiend;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
    private SimilarMoviesHolder mSimilarMoviesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Get holder

        // Get movie
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(INTENT_KEY_MOVIE);
        mMovieId = movie.getId();
        Log.i(LOG_TAG, "Unbundled " + movie.getName());

        // Set movie on screen
        View rootView = findViewById(R.id.details_root);
        mSimilarMoviesHolder = new SimilarMoviesHolder(rootView);
        mSimilarMoviesHolder.setMovie(this, movie);

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
        mSimilarMoviesHolder.finishLoading();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        // TODO (?)
    }

    public static class SimilarMoviesHolder {

        private final NetworkImageView mPosterView;
        private final TextView mNameView;
        private final TextView mRatingView;
        private final TextView mDescriptionView;
        private final TextView mSimilarMoviesText;
        private final ProgressBar mProgressBar;

        public SimilarMoviesHolder(View rootView) {
            this.mPosterView = (NetworkImageView) rootView.findViewById(R.id.imageview_details_poster);
            this.mNameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
            this.mRatingView = (TextView) rootView.findViewById(R.id.textview_movie_rating);
            this.mDescriptionView = (TextView) rootView.findViewById(R.id.textview_movie_description);
            this.mSimilarMoviesText = (TextView) rootView.findViewById(R.id.textview_similar_movies);
            this.mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_similar_movies);
        }

        public void setMovie(Context context, Movie movie) {
            ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
            this.mNameView.setText(movie.getName());
            String rating = context.getString(R.string.rating, movie.getRating());
            this.mRatingView.setText(rating);
            this.mPosterView.setImageUrl(movie.getPosterUrl(), imageLoader);
            this.mDescriptionView.setText(movie.getDescription());
        }

        public void finishLoading() {
            this.mProgressBar.setVisibility(View.GONE);
            this.mSimilarMoviesText.setVisibility(View.VISIBLE);
        }
    }
}
