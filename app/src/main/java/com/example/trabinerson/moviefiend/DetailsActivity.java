package com.example.trabinerson.moviefiend;

import android.content.Intent;
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
public class DetailsActivity extends AppCompatActivity {

    public static final String INTENT_KEY_MOVIE = "Movie";

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Get movie
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(INTENT_KEY_MOVIE);
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
    }
}
