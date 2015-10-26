package com.example.trabinerson.moviefiend;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Holder for movie-related views.
 */
public class MovieDetailsHolder {

    private final NetworkImageView mPosterView;
    private final TextView mNameView;
    private final TextView mDescriptionView;
    private final TextView mSimilarMoviesText;
    private final ProgressBar mProgressBar;
    private final RatingBubbleView mRatingBubble;

    public MovieDetailsHolder(View rootView) {
        mPosterView = (NetworkImageView) rootView.findViewById(R.id.imageview_details_poster);
        mNameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
        mRatingBubble = (RatingBubbleView) rootView.findViewById(R.id.rating_bubble);
        mDescriptionView = (TextView) rootView.findViewById(R.id.textview_movie_description);
        mSimilarMoviesText = (TextView) rootView.findViewById(R.id.textview_similar_movies);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_similar_movies);

        // Init rating bubble
        mRatingBubble.setRating(0, false);
    }

    public void setMovie(Movie movie, boolean animate) {
        ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
        mNameView.setText(movie.getName());
        mPosterView.setImageUrl(movie.getPosterUrl(), imageLoader);
        mDescriptionView.setText(movie.getDescription());

        // Rating bubble
        float rating = (float) movie.getRating();
        mRatingBubble.setRating(rating, animate);
    }

    public void finishLoading() {
        mProgressBar.setVisibility(View.GONE);
        mSimilarMoviesText.setVisibility(View.VISIBLE);
    }

    public void disableSimilarMovies() {
        mSimilarMoviesText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

}
