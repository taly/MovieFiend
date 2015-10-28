package com.example.trabinerson.moviefiend.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.loaders.SingleMovieLoader;

/**
 * An interim activity that loads details for a movie and starts the details activity.
 */
public class DeeplinkDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie[]> {

        private final int LOADER_ID = 1;

        private int mMovieId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.movie_loading);
            
            Intent intent = getIntent();
            Uri data = intent.getData();
            
            String movieId = data.getLastPathSegment();
            mMovieId = Integer.parseInt(movieId);

            // Get movie
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
        
        @Override
        public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
            return new SingleMovieLoader(this, mMovieId);
        }
        
        @Override
        public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if (data == null || data.length == 0) {
                Toast.makeText(this, "No movie found with ID " + mMovieId, Toast.LENGTH_LONG).show();
            }
            else {
                Movie movie = data[0]; // SingleMovieLoader returns 1 movie

                // Create DetailsActivity
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.INTENT_ARG_MOVIE, movie);
                intent.putExtras(bundle);
            }
            startActivity(intent);
            
            // Our work here is done
            finish();
        }
    
        @Override
        public void onLoaderReset(Loader<Movie[]> loader) { }
    }
