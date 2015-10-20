package com.example.trabinerson.moviefiend;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
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

    private static final int RATING_ANIMATION_DURATION = 1000;

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

    public void setMovie(Movie movie, boolean animate) {
        ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();
        mNameView.setText(movie.getName());
        mRatingBubble.setFinalRating(movie.getRating());
        mPosterView.setImageUrl(movie.getPosterUrl(), imageLoader);
        mDescriptionView.setText(movie.getDescription());

        // Rating bubble
        initRatingBubble(movie, animate);
    }

    public void finishLoading() {
        mProgressBar.setVisibility(View.GONE);
        mSimilarMoviesText.setVisibility(View.VISIBLE);
    }

    public void disableSimilarMovies() {
        mSimilarMoviesText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void initRatingBubble(Movie movie, boolean animate) {

        // Normalize rating
        float rating = (float)movie.getRating();
        float normalizedRating;

        // Calculate target color
        Integer targetColor;
        Integer color1 = new Integer(mRatingBubble.getColor1());
        Integer color2 = new Integer(mRatingBubble.getColor2());
        Integer color3 = new Integer(mRatingBubble.getColor3());
        ArgbEvaluator evaluator = new ArgbEvaluator();
        if (rating <= 5) {
            normalizedRating = rating / 5f;
            targetColor = (Integer) evaluator.evaluate(normalizedRating, color1, color2);
        }
        else {
            normalizedRating = (rating - 5f) / 5f;
            targetColor = (Integer) evaluator.evaluate(normalizedRating, color2, color3);
        }

        // Animate or just set values
        if (animate && rating > 0) {
            animateRatingBubble(movie, targetColor.intValue());
        }
        else {
            mRatingBubble.setBubbleColor(targetColor.intValue());
            mRatingBubble.setRating(rating);
        }
    }

    private void animateRatingBubble(Movie movie, int targetColor) {

        // Preliminaries
        double rating = movie.getRating();
        int totalDuration = RATING_ANIMATION_DURATION;
        int color1 = mRatingBubble.getColor1();
        int color2 = mRatingBubble.getColor2();
        boolean doubleColorAnimation = (rating > 5);

        // Background animator - from red to yellow to green (stop at target rating)
        AnimatorSet backgroundAnimator = new AnimatorSet();

        // Create first animation and add to set
        int animator1TargetColor = doubleColorAnimation ? color2 : targetColor;
        ObjectAnimator backgroundAnimator1 = ObjectAnimator.ofArgb(
                mRatingBubble, "bubbleColor", color1, animator1TargetColor);
        AnimatorSet.Builder builder = backgroundAnimator.play(backgroundAnimator1);

        if (doubleColorAnimation) {

            // Create second animation and add to set
            ObjectAnimator backgroundAnimator2 = ObjectAnimator.ofArgb(
                    mRatingBubble, "bubbleColor", color2, targetColor);
            builder.before(backgroundAnimator2);

            // Normalize durations so that transition is smooth
            int animation1Duration = (int)(totalDuration * (5f / rating));
            backgroundAnimator1.setDuration(animation1Duration);
            backgroundAnimator2.setDuration(totalDuration - animation1Duration);
        }
        else {
            backgroundAnimator1.setDuration(totalDuration);
        }

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
