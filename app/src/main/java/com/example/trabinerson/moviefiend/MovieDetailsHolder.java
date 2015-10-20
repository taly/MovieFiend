package com.example.trabinerson.moviefiend;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
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
    private final TextView mRatingView;
    private final TextView mDescriptionView;
    private final TextView mSimilarMoviesText;
    private final ProgressBar mProgressBar;
    private final RatingBubbleView mRatingBubble;

    public MovieDetailsHolder(View rootView) {
        this.mPosterView = (NetworkImageView) rootView.findViewById(R.id.imageview_details_poster);
        this.mNameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
        this.mRatingView = (TextView) rootView.findViewById(R.id.textview_movie_rating);
        this.mRatingBubble = (RatingBubbleView) rootView.findViewById(R.id.rating_bubble);
        this.mDescriptionView = (TextView) rootView.findViewById(R.id.textview_movie_description);
        this.mSimilarMoviesText = (TextView) rootView.findViewById(R.id.textview_similar_movies);
        this.mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_similar_movies);
    }

    public void setMovie(Context context, Movie movie) {
        ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
        this.mNameView.setText(movie.getName());
        this.mRatingView.setText(context.getString(R.string.rating_standalone));
        this.mRatingBubble.setFinalRating(movie.getRating());
        this.mPosterView.setImageUrl(movie.getPosterUrl(), imageLoader);
        this.mDescriptionView.setText(movie.getDescription());
    }

    public void finishLoading() {
        this.mProgressBar.setVisibility(View.GONE);
        this.mSimilarMoviesText.setVisibility(View.VISIBLE);
    }

    public void disableSimilarMovies() {
        this.mSimilarMoviesText.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.GONE);
    }

    public void animateRatingBubble() {
        // TODO API v problem with ofArgb. But but but...
        int color1 = Color.parseColor("#ff0000");
        int color2 = Color.parseColor("#00ff00");
        ObjectAnimator animator = ObjectAnimator.ofArgb(mRatingBubble, "bubbleColor", color1, color2);
        animator.setDuration(1000);
        animator.start();
    }
}
