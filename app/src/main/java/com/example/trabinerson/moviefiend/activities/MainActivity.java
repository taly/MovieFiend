package com.example.trabinerson.moviefiend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.fragments.InTheatresFragment;
import com.example.trabinerson.moviefiend.fragments.MovieDetailsFragment;
import com.example.trabinerson.moviefiend.fragments.SimilarMoviesPagerFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements InTheatresFragment.Callbacks, MovieDetailsFragment.Callbacks {

    public static final String INTENT_ARG_MOVIE = "Movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        // Prepare movies list on the stack in any case (even if started from deep link)
        showInTheatresList();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) { // Started from deep link - show movie details fragment
            Movie movie = extras.getParcelable(INTENT_ARG_MOVIE);
            showMovie(movie);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        MovieDetailsFragment fragment = MovieDetailsFragment.createFragment(movie, true, true);
        startFragment(fragment, MovieDetailsFragment.FRAGMENT_FLAG);
    }

    @Override
    public void onSimilarMoviesClicked(Movie[] similarMovies) {

        // Create bundle
        Bundle bundle = new Bundle();
        int numMovies = Math.min(similarMovies.length, SimilarMoviesPagerFragment.NUM_MOVIES);
        Movie[] movieSubset = Arrays.copyOfRange(similarMovies, 0, numMovies);
        bundle.putParcelableArray(SimilarMoviesPagerFragment.ARG_KEY_SIMILAR_MOVIES, movieSubset);

        // Create fragment
        SimilarMoviesPagerFragment fragment = new SimilarMoviesPagerFragment();
        fragment.setArguments(bundle);

        // Set fragment
        startFragment(fragment, SimilarMoviesPagerFragment.FRAGMENT_FLAG);
    }

    private void showInTheatresList() {
        InTheatresFragment fragment = new InTheatresFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showMovie(Movie movie) {
        onMovieClicked(movie); // Behave as if movie was clicked
    }

    private void startFragment(Fragment fragment, String fragmentFlag) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_from_left,
                        R.anim.slide_to_right,
                        R.anim.slide_from_right,
                        R.anim.slide_to_left)
                .replace(R.id.fragment_container, fragment, fragmentFlag)
                .addToBackStack("")
                .commit();
    }
}
