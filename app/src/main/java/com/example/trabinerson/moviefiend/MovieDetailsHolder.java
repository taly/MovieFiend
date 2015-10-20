package com.example.trabinerson.moviefiend;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Holder for movie-related views.
 */
public class MovieDetailsHolder {

    private Movie mMovie;

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
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
        mNameView.setText(movie.getName());
        mRatingBubble.setFinalRating(movie.getRating());
        mPosterView.setImageUrl(movie.getPosterUrl(), imageLoader);
        mDescriptionView.setText(movie.getDescription());
    }

    public void finishLoading() {
        mProgressBar.setVisibility(View.GONE);
        mSimilarMoviesText.setVisibility(View.VISIBLE);
    }

    public void disableSimilarMovies() {
        mSimilarMoviesText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void animateRatingBubble(int bubbleColor1, int bubbleColor2, int bubbleColor3) {
        if (mMovie == null) {
            return;
        }
        double rating = mMovie.getRating();
        int totalDuration = 2000;

        // Background color animators
        ObjectAnimator backgroundAnimator1 = ObjectAnimator.ofArgb(
                mRatingBubble, "bubbleColor", bubbleColor1, bubbleColor2);
        backgroundAnimator1.setDuration(totalDuration/2);

        ObjectAnimator backgroundAnimator2 = ObjectAnimator.ofArgb(
                mRatingBubble, "bubbleColor", bubbleColor2, bubbleColor3);
        backgroundAnimator1.setDuration(totalDuration/2);

        AnimatorSet backgroundAnimator = new AnimatorSet();
        backgroundAnimator.playSequentially(backgroundAnimator1, backgroundAnimator2);

        // Number animator
        ObjectAnimator numberAnimator = ObjectAnimator.ofFloat(
                mRatingBubble, "rating", 0.0f, (float)rating);
        numberAnimator.setDuration(totalDuration);

        // All together now
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(backgroundAnimator, numberAnimator);

        animatorSet.start();
    }
}
