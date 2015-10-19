package com.example.trabinerson.moviefiend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * An activity that shows a slider between similar movies.
 */
public class SimilarMoviesPagerActivity extends FragmentActivity {

    public static final String INTENT_KEY_SIMILAR_MOVIES = "SimilarMovies";

    private static final String LOG_TAG = SimilarMoviesPagerActivity.class.getSimpleName();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Movie[] mSimilarMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_movie_slide);

        // Get movies
        Intent intent = getIntent();
        Parcelable[] parcel = intent.getExtras().getParcelableArray(INTENT_KEY_SIMILAR_MOVIES);
        mSimilarMovies = new Movie[parcel.length];
        for (int i = 0; i < parcel.length; i++) {
            mSimilarMovies[i] = (Movie)parcel[i];
        }

        Log.i(LOG_TAG, "Unbundled " + mSimilarMovies.length + " movies");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager_similar_movies);
        mPagerAdapter = new SimilarMoviesPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class SimilarMoviesPagerAdapter extends FragmentStatePagerAdapter {
        public SimilarMoviesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MovieDetailsFragment.ARG_KEY_MOVIE, mSimilarMovies[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mSimilarMovies.length;
        }
    }
}
