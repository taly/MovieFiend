/*
 * Copyright (c) 2015 PayPal, Inc.
 *
 * All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * A custom view that shows movie details.
 */
public class MovieDetailsView extends ScrollView {

    private NetworkImageView mPosterView;
    private TextView mNameView;
    private TextView mDescriptionView;
    private TextView mSimilarMoviesText;
    private ProgressBar mProgressBar;
    private RatingBubbleView mRatingBubble;

    public MovieDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.movie_details_view_children, this, true);
        setupChildren();
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

    private void setupChildren() {
        mPosterView = (NetworkImageView) findViewById(R.id.imageview_details_poster);
        mNameView = (TextView) findViewById(R.id.textview_movie_name);
        mRatingBubble = (RatingBubbleView) findViewById(R.id.rating_bubble);
        mDescriptionView = (TextView) findViewById(R.id.textview_movie_description);
        mSimilarMoviesText = (TextView) findViewById(R.id.textview_similar_movies);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_similar_movies);
    }

}
