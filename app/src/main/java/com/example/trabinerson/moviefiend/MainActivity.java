package com.example.trabinerson.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.trabinerson.moviefiend.fragments.InTheatresFragment;
import com.example.trabinerson.moviefiend.fragments.MovieDetailsFragment;
import com.example.trabinerson.moviefiend.fragments.SimilarMoviesPagerFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements InTheatresFragment.InTheatresCallbacks, MovieDetailsFragment.MovieDetailsCallbacks {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set fragment (if necessary)
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Fragment fragment;
            Intent intent = getIntent();
            String data = intent.getDataString();

            if (data != null) { // Started from deep link

                // Get movie ID
                String movieIdStr = Uri.parse(data).getLastPathSegment();
                int movieId = Integer.parseInt(movieIdStr);

                // Create bundle
                Bundle bundle = new Bundle();
                bundle.putInt(MovieDetailsFragment.ARG_KEY_MOVIE_ID, movieId);
                bundle.putBoolean(MovieDetailsFragment.ARG_KEY_SHOW_SIMILAR, true);
                bundle.putBoolean(MovieDetailsFragment.ARG_KEY_ANIMATE_RATING, true);

                // Create fragment
                fragment = new MovieDetailsFragment();
                fragment.setArguments(bundle);

            } else {
                fragment = new InTheatresFragment();
            }

            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

    }

    @Override
    public void onMovieClicked(Movie movie) {

        // Create bundle
        Bundle bundle = new Bundle();
        bundle.putInt(MovieDetailsFragment.ARG_KEY_MOVIE_ID, movie.getId());
        bundle.putParcelable(MovieDetailsFragment.ARG_KEY_MOVIE, movie);
        bundle.putBoolean(MovieDetailsFragment.ARG_KEY_ANIMATE_RATING, true);
        bundle.putBoolean(MovieDetailsFragment.ARG_KEY_SHOW_SIMILAR, true);

        // Create fragment
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(bundle);

        // Set fragment
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
        mFragmentManager.beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragmentFlag)
                .commit();
    }
}
