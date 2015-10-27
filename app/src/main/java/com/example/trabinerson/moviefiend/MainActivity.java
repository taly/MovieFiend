package com.example.trabinerson.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.trabinerson.moviefiend.fragments.InTheatresFragment;
import com.example.trabinerson.moviefiend.fragments.MovieDetailsFragment;
import com.example.trabinerson.moviefiend.fragments.SimilarMoviesPagerFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements InTheatresFragment.Callbacks, MovieDetailsFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        Fragment fragment;
        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) { // Started from deep link - show movie details fragment
            String movieIdStr = data.getLastPathSegment();
            int movieId = Integer.parseInt(movieIdStr);
            fragment = MovieDetailsFragment.createFragment(movieId, null, true, true);
        } else { // Started normally - show list
            fragment = new InTheatresFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }


    @Override
    public void onMovieClicked(Movie movie) {
        MovieDetailsFragment fragment = MovieDetailsFragment.createFragment(
                movie.getId(), movie, true, true);
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

    private void startFragment(Fragment fragment, String fragmentFlag) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragmentFlag)
                .commit();
    }
}
