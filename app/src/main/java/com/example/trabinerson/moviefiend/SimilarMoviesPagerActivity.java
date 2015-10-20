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
import android.widget.ImageView;

/**
 * An activity that shows a slider between similar movies.
 */
public class SimilarMoviesPagerActivity extends FragmentActivity {

    public static final String INTENT_KEY_SIMILAR_MOVIES = "SimilarMovies";
    public static final int NUM_MOVIES = 5;

    private static final String LOG_TAG = SimilarMoviesPagerActivity.class.getSimpleName();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Movie[] mSimilarMovies;
    private ImageView[] mDotViews;

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

        // Keep dots in memory
        initProgressDots();

        // Instantiate a ViewPager and a PagerAdapter
        initPagerAndAdapter();
    }

    private void initProgressDots() {
        int[] dotIds = {
                R.id.imageview_dot1,
                R.id.imageview_dot2,
                R.id.imageview_dot3,
                R.id.imageview_dot4,
                R.id.imageview_dot5
        };
        mDotViews = new ImageView[mSimilarMovies.length];
        for (int i = 0; i < mSimilarMovies.length; i++) {
            mDotViews[i] = (ImageView) findViewById(dotIds[i]);
        }
    }

    private void initPagerAndAdapter() {

        // Pager
        mPager = (ViewPager) findViewById(R.id.pager_similar_movies);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // Highlight correct dot
                float activeScale = 1.5f;
                int inactiveScale = 1;
                for (int i = 0; i < mSimilarMovies.length; i++) {
                    if (i == position) {
                        mDotViews[i].setScaleX(activeScale);
                        mDotViews[i].setScaleY(activeScale);
                    }
                    else {
                        mDotViews[i].setScaleX(inactiveScale);
                        mDotViews[i].setScaleY(inactiveScale);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        // Adapter
        mPagerAdapter = new SimilarMoviesPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }


    /**
     * A simple pager adapter that represents 5 MovieDetailsFragment objects, in sequence.
     */
    private class SimilarMoviesPagerAdapter extends FragmentStatePagerAdapter {

        private MovieDetailsFragment[] mFragments;

        public SimilarMoviesPagerAdapter(FragmentManager fm) {
            super(fm);

            // Create fragments in advance
            mFragments = new MovieDetailsFragment[mSimilarMovies.length];
            for (int i = 0; i < mSimilarMovies.length; i++) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailsFragment.ARG_KEY_MOVIE, mSimilarMovies[i]);
                MovieDetailsFragment fragment = new MovieDetailsFragment();
                fragment.setArguments(bundle);
                mFragments[i] = fragment;
            }
        }

        @Override
        public Fragment getItem(int position) {
            MovieDetailsFragment fragment = mFragments[position];
            return fragment;
        }

        @Override
        public int getCount() {
            return mSimilarMovies.length;
        }
    }
}
